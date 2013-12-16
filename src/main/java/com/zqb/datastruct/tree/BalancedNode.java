package com.zqb.datastruct.tree;
/**
 * 平衡二叉查找树（Balanced Binary Search Tree），红黑树 (Red-Black Tree )，B-tree 
 * @author zhengquanbin
 *         created 2013年12月16日
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class BalancedNode<T> extends Node<T> {
	/**数据的个数*/
	private int dataNum;
	/**数据的内容数组*/
	private Object[] datas;
	/**指向子节点的指针*/
	private BalancedNode<T>[] children;
	/**当前数组保存数据的位置*/
	private int dataCurrent;
	
	public BalancedNode(int dataNum) {
		this.dataNum = dataNum;
		this.datas = new Object[dataNum];
		this.children = new BalancedNode[dataNum+1];
		this.dataCurrent = 0;
	}
	public int getDataNum() {
		return dataNum;
	}
	public void setDataNum(int dataNum) {
		this.dataNum = dataNum;
	}
	public T[] getDatas() {
		return (T[]) datas;
	}
	public void setDatas(T[] datas) {
		this.datas = datas;
	}
	public BalancedNode<T>[] getChildren() {
		return children;
	}
	public void setChildren(BalancedNode<T>[] children) {
		this.children = children;
	}
	public int getDataCurrent() {
		return dataCurrent;
	}
	public void setDataCurrent(int dataCurrent) {
		this.dataCurrent = dataCurrent;
	}
}
