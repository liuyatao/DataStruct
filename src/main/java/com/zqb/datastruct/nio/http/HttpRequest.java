package com.zqb.datastruct.nio.http;

import java.net.URI;

/**
 * 封装了一个http请求
 * @author zhengquanbin
 *         created 2014年2月16日
 */
public class HttpRequest {

	public static enum Method {
		
		GET("GET"), PUT("PUT"), POST("POST"), HEAD("HEAD");
		
		private String name;
		
		private Method(String name) {
			this.name = name;
		}
	}
	
	private Method method;
	private String version;
	private URI uri;
}
