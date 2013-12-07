package com.zqb.datastruct.list.test;

import java.util.LinkedList;

/**
 * 定义栈的数据结构，要求添加一个min函数，能够得到栈的最小元素。要求函数min、push以及pop的时间复杂度都是O(1)。
 * @author zhengquanbin
 *		   created 2013-12-7
 */
public class StackWithMin<E extends Comparable<E>> {
	/**
	 * 存在元素的栈
	 */
	private LinkedList<E> elements;
	/**
	 * 辅助存在最元素的栈位置的栈
	 */
	private LinkedList<E> minposit;
	
	public StackWithMin() {
		elements = new LinkedList<E>();
		minposit = new LinkedList<E>();
	}
	/**
	 * 返回最小的无素
	 * @return
	 */
	public E min() {
		return minposit.peek();
	}
	
	public E push(E item) {
		elements.push(item);
		E _item = minposit.peek();
		if(_item==null || item.compareTo(_item)<=0) {
			minposit.push(item);
		}
		return item;
	}
	public synchronized E pop() {
		E item = elements.pop();
		if(item.equals(minposit.peek())) {
			minposit.pop();
		}
		return item;
	}
}
