package com.zqb.datastruct.splitter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用tree来实现分词字典
 * @author zhengqb
 *        created by 2014年5月4日
 */
public class DictNode {
	/**根节点*/
	public static final DictNode ROOT;
	static {
		ROOT = new DictNode();
		
		String dictPath = "/home/zhengqb/tmp/dict";
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(dictPath);
			br = new BufferedReader(fr);
			
			String line = null;
			DictNode node, parentNode;
			while((line=br.readLine())!=null) {
				int level = 1;
				parentNode = ROOT;
				char[] words = line.toCharArray();
				for(int i=0; i<words.length; i++) {
					node = new DictNode(words[i],level++);
					parentNode.put(words[i], node);
					parentNode = node;
				}
				parentNode.end = true;
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fr!=null)
					fr.close();
				if(br!=null)
					br.close();
			} catch (IOException e) {
			}
		}
	}
	
	/**表示树的层次*/
	private int level;
	/**里面放着的具体字*/
	private char word;
	/**是否是该词的最后一个字*/
	private boolean end;
	/**下一层的节点*/
	private Map<Character, DictNode> subNodes;
	
	
	public DictNode() {
		subNodes = new HashMap<Character, DictNode>();
	}
	private DictNode(char word, int level) {
		this();
		this.word = word;
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	public DictNode buildOrGetNode(String word) {
		return subNodes.get(word);
	}
	public boolean isEnd() {
		return end;
	}
	public void put(char word, DictNode node) {
		if(!this.subNodes.containsKey(word)) {
			this.subNodes.put(word, node);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + word;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DictNode other = (DictNode) obj;
		if (word != other.word)
			return false;
		return true;
	}
	
	
	public static void main(String[] args) {
		DictNode root = DictNode.ROOT;
		System.out.println(root);
	}
}
