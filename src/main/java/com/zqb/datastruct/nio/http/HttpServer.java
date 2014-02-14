package com.zqb.datastruct.nio.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.HashSet;
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
	
	private static final int POOL_MULTIPLE = 4;
	
	protected volatile boolean running = false;
	
	public HttpServer() throws IOException {
		executorService = Executors.newFixedThreadPool(
				Runtime.getRuntime().availableProcessors()*POOL_MULTIPLE);
		
		server = ServerSocketChannel.open();
		server.configureBlocking(false);
		server.socket().bind(new InetSocketAddress(port));
		
		selector = Selector.open();
		
		running = true;
		System.out.println("server is running!");
	}
	
	public void service() throws IOException {
		server.register(selector, SelectionKey.OP_ACCEPT, new AcceptHandler());
		
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
					
					Handler handler = (Handler) key.attachment();
					handler.handle(key);
				} catch(Exception e) {
					if(key!=null) {
						key.cancel();
						key.channel().close();
					}
				}
				
			}
		}
	}
	
	
	class AcceptHandler implements Handler {
		
		@Override
		public void handle(SelectionKey selectionKey) throws IOException {
			
		}
	}
	
	public static void main(String[] args) {
		
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(2);
		set.add(3);
		set.add(4);
		set.add(5);
		set.add(6);
		set.add(7);
		set.add(8);
		set.add(9);
		
		Iterator<Integer> iter = set.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
			
			iter.remove();
		}
	}
}
