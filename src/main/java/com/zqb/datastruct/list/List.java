package com.zqb.datastruct.list;
/**
 * 表示一个list
 * @author zhengquanbin
 *		   created 2013-12-12
 */
public interface List<T> {
	/**
	 * 添加一个元素
	 * @param data
	 */
	void add(T data);
	/**
	 * 颠倒一个线性表的顺序（这里会有两种方案，一种是传统的while方式，另一种的递归）
	 * @return
	 */
	List<T> reverse();
}
