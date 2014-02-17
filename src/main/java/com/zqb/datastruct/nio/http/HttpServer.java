package com.zqb.datastruct.nio.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * nio实现的http服务器
 * @author zhengqb
 *         created 2014年2月13日
 */
public class HttpServer {

	protected ExecutorService executorService;
	
	protected Selector selector;
	protected ServerSocketChannel server;
	protected int port = 80;
	protected boolean tcpNoDelay = false;
	private Charset charset = Charset.forName("utf-8");
	
	protected HttpHandler[] handlers;
	protected int curHandler = 0;
	
	private static final int POOL_MULTIPLE = 4;
	
	protected volatile boolean running = false;
	
	public HttpServer() throws IOException {
		server = ServerSocketChannel.open();
		server.configureBlocking(false);
		server.socket().bind(new InetSocketAddress(port));
		
		selector = Selector.open();
		
		running = true;
		
		executorService = Executors.newFixedThreadPool(POOL_MULTIPLE);
		handlers = new HttpHandler[POOL_MULTIPLE];
		for(int i=0; i<POOL_MULTIPLE; i++) {
			handlers[i] = new HttpHandler(Selector.open());
			executorService.execute(handlers[i]);
		}
		System.out.println("server is running!");
	}
	
	public void service() throws IOException {
		server.register(selector, SelectionKey.OP_ACCEPT);
		
		while(running) {
			int n = selector.select();
			if(n==0) {
				continue;
			}
			
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iter = keys.iterator();
			
			SelectionKey key = null;
			while(iter.hasNext()) {
				try {
					key = iter.next();
					iter.remove();
					
					try {
		              if (key.isValid()) {
		                if (key.isAcceptable())
		                  doAccept(key);
		              }
		            } catch (IOException e) {
		            }
		            key = null;
				} catch(Exception e) {
				}
			}
		}
	}
	
	private void doAccept(SelectionKey key) throws IOException {
		ServerSocketChannel socketChannel = (ServerSocketChannel) key.channel();
		SocketChannel channel;
		
		while((channel=socketChannel.accept())!=null) {
			channel.configureBlocking(false);
			channel.socket().setTcpNoDelay(tcpNoDelay);
			
			HttpHandler handler = this.getHandler();
			handler.regiest(channel);
			/*ByteBuffer buffer = ByteBuffer.allocate(1024);
			selectionKey.attach(buffer);
			*/
			handler.handle();
		}
	}
	
	private HttpHandler getHandler() {
		curHandler = (curHandler+1)%POOL_MULTIPLE;
		return handlers[curHandler];
	}

	class HttpHandler implements Runnable {
		
		protected Selector selector;
		
		public HttpHandler(Selector selector) {
			this.selector = selector;
		}

		public void handle() {
			this.selector.wakeup();
		}

		public SelectionKey regiest(SocketChannel channel) throws IOException {
			//this.selector.wakeup();
			return channel.register(this.selector, SelectionKey.OP_READ, new RequestChannel(channel));
		}

		@Override
		public void run() {
			
			while(running) {
				SelectionKey key = null;
				try {
					int n = this.selector.select();
					if(n==0) {
						continue;
					}
					
					Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
					while(keys.hasNext()) {
						key = keys.next();
						keys.remove();
						if(key.isValid()) {
							if(key.isReadable()) {
								//接收http请求
								receive(key);
							}
						}
					}
				} catch (IOException e) {
					try {
						if(key!=null) {
							key.cancel();
							key.channel().close();
						}
					} catch (IOException e1) {}
				}
			}
		}

		/**
		 * 接收用户的http请求，这里有个问题就是什么时候才算了请求发送完成
		 * @param key
		 * @throws IOException 
		 */
		private void receive(SelectionKey key) throws IOException {
			RequestChannel rc = (RequestChannel) key.attachment();
			//接收http请求
			if(!rc.receive())
				return;
			
			//进行http请求的解析
			//HttpRequest request = parse(rc.getByteBuffer());
			String line = null;
			while((line=rc.readLine())!=null) {
				System.out.println(line);
			}
		}
		
		/**
		 * 进行http请求的解析
		 * @param byteBuffer
		 * @return
		 */
		private HttpRequest parse(ByteBuffer byteBuffer) {
			return null;
		}

		/**
		 * 对SocketChannel进行包装，增加了自动增长缓冲区容量的功能<br/>
		 * 	1、如果我需要进行对SocketChannel的包装，就不应该有任何关于SocketChannel，以及相关的类
		 * @author zhengquanbin
		 *         created 2014年2月16日
		 */
		class RequestChannel {
			protected SocketChannel socketChannel;
			protected ByteBuffer byteBuffer;
			protected boolean received = false;
			
			protected List<String> httpHead;
			protected int position = 0;
			
			private static final int REQUEST_BUFFER_SIZE = 4098;
			
			public RequestChannel(SocketChannel socketChannel) throws IOException {
				this.socketChannel = socketChannel;
				byteBuffer = ByteBuffer.allocate(REQUEST_BUFFER_SIZE);
				
				this.httpHead = new ArrayList<String>();
			}

			/**
			 * 接收http请求，如果没有发送完，返回false
			 * @return
			 * @throws IOException 
			 */
			public boolean receive() throws IOException {
				if(!received) {
					//received = (socketChannel.read(byteBuffer)==-1);
					int n = socketChannel.read(byteBuffer);
					if(n!=-1) {
						String data = charset.decode(byteBuffer).toString();
						if(data.indexOf("\r\n")!=-1) {
							String line = data.substring(0, data.indexOf("\n")+1);
							httpHead.add(line);
							ByteBuffer temp = charset.encode(line);
							byteBuffer.position(temp.limit());
							byteBuffer.compact();
						}
						return false;
					}
					received = true;
				}
				return received;
			}

			/**
			 * 如果发现ByteBuffer的size不足i，就会增加1倍
			 * @return
			 */
			private ByteBuffer resizeBuffer() {
				ByteBuffer tmp = ByteBuffer.allocate((int) (byteBuffer.capacity()*1.5));
				tmp.put(byteBuffer);
				return tmp;
			}
			
			/**
			 * 读取一行
			 * @return 返回null时表示没有数据
			 */
			public String readLine() {
				if(position==httpHead.size()) {
					return null;
				}
				return httpHead.get(position++);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		new HttpServer().service();
		
	}
}
