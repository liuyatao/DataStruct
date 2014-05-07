package com.zqb.datastruct.splitter;
/**
 * 中文分词器
 * @author zhengqb
 *        created by 2014年5月4日
 */
public class ChineseSplitter {

	private Token token;
	
	public void enqueueToken(int pos, DictNode node) {
		if(pos>=token.length()) {
			return ;
		}
		//用动态规划判断从当前位置的字符开始分词是否已经进行过啦
		if(isTokened(pos) && node.getLevel()==0) {
			return ;
		}
		if(node.getLevel()==0) {
			setTokened(pos);
		}
		char current = token.getChar(pos);
		DictNode subNode = node.buildOrGetNode(current);
		if(subNode==null) {
			enqueueToken(pos+1, DictNode.ROOT);
			return;
		}
		if(node.isEnd()) {
			
		}
	}

	private void setTokened(int pos) {
		
	}

	private boolean isTokened(int pos) {
		return false;
	}
	
}
