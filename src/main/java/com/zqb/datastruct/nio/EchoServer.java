package com.zqb.datastruct.nio;

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
 * 运用java.nio实现的一个简单的server
 * @author zhengqb
 *         created 2014年2月12日
 */
public class EchoServer {
	private int port = 8000;
	private volatile boolean running = false;
	private ServerSocketChannel socketChannel = null;
	private ExecutorService executorService;
	private Selector selector;
	private Charset charset = Charset.forName("utf-8");
	
	private static final int POOL_MULTIPLE = 4;
	
	public EchoServer() throws IOException {
		executorService = Executors.newFixedThreadPool(
				Runtime.getRuntime().availableProcessors()*POOL_MULTIPLE);
		
		selector = Selector.open();
		
		socketChannel = ServerSocketChannel.open();
		//允许后面的程序可以顺利的绑定到相同的端口上
		socketChannel.socket().setReuseAddress(true);
		//设置nio
		socketChannel.configureBlocking(false);
		socketChannel.socket().bind(new InetSocketAddress(port));
		
		running = true;
		System.out.println("Echo Server start, and bind in "+port);
	}
	
	public void service() throws IOException {
		//注册
		socketChannel.register(selector, SelectionKey.OP_ACCEPT);
		while(selector.select()>0 && running) {
			
			 Set<SelectionKey> set = selector.selectedKeys();
			 Iterator<SelectionKey> iterator = set.iterator();
			 
			 SelectionKey key = null;
			 while(iterator.hasNext()) {
				 try {
					 key = iterator.next();
					 iterator.remove();
					 if(key.isAcceptable()) {
						 //处理连接事件
						 Accept(key);
					 }
					 if(key.isReadable()) {
						 //处理读事件
						 receive(key);
					 }
					 if(key.isWritable()) {
						 //处理写事件
						 send(key);
					 }
				 } catch(Exception e) {
					 try {
						 if(key!=null) {
							 key.cancel();
							 key.channel().close();
						 }
					 } catch(Exception ex) {
					 }
				 }
				 
			 }
			
		}
	}
	
	private void Accept(SelectionKey key) throws IOException {
		ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
		SocketChannel socketChannel = ssc.accept();
		System.out.println("接收来自于客户端的连接，ip="
				+socketChannel.socket().getInetAddress()+", port="+socketChannel.socket().getPort());
		socketChannel.configureBlocking(false);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		socketChannel.register(selector, 
				SelectionKey.OP_READ|SelectionKey.OP_WRITE, buffer);
	}
	private void receive(SelectionKey key) throws IOException {
		ByteBuffer buffer = (ByteBuffer) key.attachment();
		SocketChannel socketChannel = (SocketChannel) key.channel();
		
		ByteBuffer readbuffer = ByteBuffer.allocate(32);
		socketChannel.read(readbuffer);
		//把limit设置为position，把position设置为0
		readbuffer.flip();
		
		buffer.limit(buffer.capacity());
		buffer.put(readbuffer);
	}
	private void send(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		ByteBuffer buffer = (ByteBuffer) key.attachment();
		buffer.flip();
		
		String data = decode(buffer);
		if(data.indexOf("\r\n")==-1) {
			return ;
		}
		String line = data.substring(0, data.indexOf("\n")+1);
		System.out.println(line);
		
		ByteBuffer output = encode("echo:"+line);
		while(output.hasRemaining()) {
			socketChannel.write(output);
		}
		
		//截取一行数据
		ByteBuffer temp = encode(line);
		buffer.position(temp.limit());
		buffer.compact();
		
		//如果已经输出了字符bye
		if(line.equals("bye\r\n")) {
			key.cancel();
			socketChannel.close();
			System.out.println("关闭客户端和服务器的连接！");
		}
	}

	private ByteBuffer encode(String str) {
		return charset.encode(str);
	}
	private String decode(ByteBuffer buffer) {
		return charset.decode(buffer).toString();
	}
	
	public static void main(String[] args) throws IOException {
		
		//new EchoServer().service();
		
		List<Integer> list = new ArrayList<Integer>(10);
		System.out.println(list.get(1));
		
	}
	
}
