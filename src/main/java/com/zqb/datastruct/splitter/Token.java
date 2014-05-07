package com.zqb.datastruct.splitter;
/**
 * 
 * @author zhengqb
 *        created by 2014年5月4日
 */
public class Token {

	private String text;
	private char[] textArray;
	
	public Token(String text) {
		this.text = text;
		this.textArray = text.toCharArray();
	}
	
	public int length() {
		if(text==null)
			return 0;
		return text.length();
	}

	public char getChar(int pos) {
		return textArray[pos];
	}
}
