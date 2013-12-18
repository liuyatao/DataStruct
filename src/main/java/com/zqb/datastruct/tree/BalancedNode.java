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
	/**父亲节点*/
	private BalancedNode<T> parent;
	/**指向子节点的指针*/
	private BalancedNode<T>[] children;
	/**当前数组保存数据的位置*/
	private int dataCurrent;
	
	public BalancedNode(int dataNum) {
		this(dataNum, null);
	}
	public BalancedNode(int dataNum, BalancedNode<T> parent) {
		this.dataCurrent = 0;
		this.parent = parent;
		this.dataNum = dataNum;
		this.datas = new Object[dataNum];
		this.children = new BalancedNode[dataNum+1];
	}
	
	/**
	 * 在结点中设置一个参数
	 * @param data
	 * @return 若数据不放到这个节点上，就会返回下一个节点
	 */
	public BalancedNode<T> setOrNext(T data) {
		if(dataCurrent==0 || (dataCurrent<dataNum && data.compareTo((T) datas[dataCurrent-1])>0)) {
			//如果dataCurrent==0，那就说明这个结点是没有任何的数据，那就直接把数据放到datas第一个
			//如果dataCurrent<dataNum，并且data比dataCurrent的值要大，那也可以直接放到最后
			datas[dataCurrent++] = data;
			return null;
		}
		//进行二分查找
		Offset offset = binarySearch(data);
		if(offset.exist) {
			return null;
		}
		if(children[offset.index]==null) {
			children[offset.index] = new BalancedNode<T>(dataNum, this);
		}
		return children[offset.index];
	}
	/**
	 * 在结点中设置一个参数删除一个参数
	 * @param data
	 * @return
	 */
	public BalancedNode<T> removeOrNext(T data) {
		if(dataCurrent==0) {
			return null;
		}
		//进行二分查找
		Offset offset = binarySearch(data);
		if(offset.exist) {
			int index = this.parent.binarySearch((T) this.datas[0]).index;
			//进行节点的删除
			if(dataCurrent==1) { 
				this.parent.children[index] = children[0];
			} else {
				
			}
			return null;
		}
		//如果children[position]为null就表示为没有找到相应的节点
		return children[offset.index];
	}
	/**
	 * （设置过新算法的）折半查找，算法描述：
	 * @param data
	 * @return 
	 */
	private Offset binarySearch(T data) {
		int begin = 0, end = dataCurrent-1, mid=0;
		while(end>begin) {
			mid = (begin+end)/2;
			switch(data.compareTo((T) datas[mid])) {
			case 0: return new Offset(true, mid);
			case 1:
				//data比mid位置的数大
				if(data.compareTo((T) datas[mid+1])<0) {
					return new Offset(false, mid+1);
				}
				begin = mid+1;
				break;
			case -1:
				//data比mid位置的数小
				if(data.compareTo((T) datas[mid-1])>0) {
					return new Offset(false, mid);
				}
				end = mid-1;
				break;
			}
		}
		return new Offset(false, mid);
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
	
	/**
	 * binary查找之后返回的结果，如果Offset.exist等于true，表示元素是存在的，那么index就为元素的下标
	 * 如果Offset.exist等于false，那就是为下一个节点的下标
	 * @author zhengquanbin
	 *         created 2013年12月18日
	 */
	class Offset {
		/**是否存在*/
		boolean exist;
		/**结果的下标*/
		int index;
		
		public Offset(boolean exist, int index) {
			this.exist = exist;
			this.index = index;
		}
	}
}
