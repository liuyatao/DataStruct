package com.zqb.datastruct.tree;
/**
 * 平衡二叉查找树（Balanced Binary Search Tree），红黑树 (Red-Black Tree )，B-tree 
 * @author zhengquanbin
 *         created 2013年12月16日
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class BalancedNode<T extends Comparable<T>> extends Node<T> {
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
	
	/**
	 * 在结点中设置一个参数
	 * @param data
	 * @return 若数据不放到这个节点上，就会返回下一个节点
	 */
	public BalancedNode<T> setOrNext(T data) {
		if(dataCurrent==0) {
			//如果dataCurrent==0，那就说明这个结点是没有任何的数据，那就直接把数据放到datas第一个
			datas[dataCurrent++] = data;
			return null;
		}
		if(dataCurrent==dataNum) {
			
		}
		return null;
	}
	
	//----getter and setter
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
	
	public static void main(String[] args) {
		System.out.println((new Integer(3)).compareTo(3));
	}
}
