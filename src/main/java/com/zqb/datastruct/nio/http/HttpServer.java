package com.zqb.datastruct.nio.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
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
	
	protected Handler[] handlers;
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
		handlers = new Handler[POOL_MULTIPLE];
		for(int i=0; i<POOL_MULTIPLE; i++) {
			handlers[i] = new Handler(Selector.open());
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
			
			Handler handler = this.getHandler();
			SelectionKey selectionKey = handler.regiest(channel);
			selectionKey.attach(handler);
			
			//handler.handle();
		}
	}
	
	private Handler getHandler() {
		curHandler = (curHandler+1)%POOL_MULTIPLE;
		return handlers[curHandler];
	}

	class Handler implements Runnable {
		
		protected Selector selector;
		
		public Handler(Selector selector) {
			this.selector = selector;
		}

		public void handle() {
			
		}

		public SelectionKey regiest(SocketChannel channel) throws IOException {
			return channel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
		}

		@Override
		public void run() {
			
			while(running) {
				try {
					int n = selector.select();
					if(n==0) {
						continue;
					}
					
					Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
					SelectionKey key = null;
					while(keys.hasNext()) {
						key = keys.next();
						if(key.isValid()) {
							if(key.isReadable()) {
								//接收http请求
								receive();
							}
							if(key.isWritable()) {
								//响应请求
								send();
							}
						}
					}
				} catch (IOException e) {
					
					
				}
			}
		}
		private void receive() {
			
		}
		private void send() {
			
		}
	}
}
