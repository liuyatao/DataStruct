package com.zqb.datastruct.nio.exception;

public class ParsetRequestException extends Exception {
	private static final long serialVersionUID = 3233356836826392480L;
	
	private String serverName;

	
	public ParsetRequestException(String meg, String serverName) {
		super(meg);
		this.serverName = serverName;
	}
	
	public String getServerName() {
		return this.serverName;
	}
	
	public static void main(String[] args) {
		
		System.out.println(1&0);
		
		System.out.println(1|4);
		
		System.out.println(1^4);
	}
}
