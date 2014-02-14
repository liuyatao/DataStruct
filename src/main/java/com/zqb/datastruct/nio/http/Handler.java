package com.zqb.datastruct.nio.http;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * 
 * @author zhengqb
 *         created 2014年2月13日
 */
public interface Handler {

	void handle(SelectionKey selectionKey) throws IOException;
}
