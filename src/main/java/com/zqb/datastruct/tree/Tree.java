package com.zqb.datastruct.tree;
/**
 * 树
 * @author zhengquanbin
 *		   created 2013-12-7
 */
public interface Tree<T> {
	
	boolean isEmpty();
	/**
	 * 返回节点的个数
	 * @return
	 */
	int count();
	/**
	 * 返回节点的高度
	 * @return
	 */
	int height();
	/**
	 * 先根遍历
	 */
	void preOrder();
	/**
	 * 中根遍历
	 */
	void inOrder();
	/**
	 * 后根遍历
	 */
	void postOrder();
	/**
	 * 层次遍历
	 */
	void levelOrder();
	/**
	 * 查找并返回首次出现的关键字为key元素结
	 * @param key
	 * @return
	 */
	Node<T> search(T key);
	/**
	 * 返回node的父母结点
	 * @param node
	 * @return
	 */
	Node<T> getParent(Node<T> node);
	/**
	 * 插入一下根节点
	 * @param x
	 */
	void insertRoot(T x);
	/**
	 * 插入一个子节点
	 * @param p
	 * @param x
	 * @param leftChild
	 * @return
	 */
	Node<T> insertChild(Node<T> p, T x, boolean leftChild);
	/**
	 * 删除p结点的左或右子树
	 * @param p
	 * @param leftChild
	 * @return
	 */
	Node<T> removeChild(Node<T> p, boolean leftChild);
	
	void removeAll();
}
