package com.zqb.datastruct.tree;
/**
 * B-tree又叫平衡多路查找树
 * @author zhengquanbin
 *         created 2013年12月16日
 * @param <T>
 */
public class BalancedTree<T extends Comparable<T>> implements Tree<T> {

	private BalancedNode<T> root;
	
	public BalancedTree(int dataNum) {
		this.root = new BalancedNode<T>(dataNum);
	}
	
	/**
	 * 插入一个结点
	 * @param data
	 */
	public void insert(T data) {
		BalancedNode<T> node = root.setOrNext(data);
		while(node!=null) {
			node = node.setOrNext(data);
		}
	}
	
	/**
	 * 删除某个节点，这里可以有两个方案，1：可以把相应的节点标记成删除，这样就不需要移动大量的节点，但就会产生大量的判断
	 * 2、直接把节点删除，但要移动大量的节点，这里采用第二次方案来实现
	 * @param data
	 */
	public void remove(T data) {
		BalancedNode<T> node = root.removeOrNext(data);
		while(node!=null) {
			node = node.removeOrNext(data);
		}
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	@Override
	public int count() {
		return 0;
	}

	@Override
	public int height() {
		return 0;
	}

	@Override
	public void preOrder() {
		
	}

	@Override
	public void inOrder() {
	}

	@Override
	public void postOrder() {
	}

	@Override
	public void levelOrder() {
	}

	@Override
	public Node<T> search(T key) {
		return null;
	}

	@Override
	public Node<T> getParent(Node<T> node) {
		return null;
	}

	@Override
	public void insertRoot(T x) {
		
	}

	@Override
	public Node<T> insertChild(Node<T> p, T x, boolean leftChild) {
		return null;
	}

	@Override
	public Node<T> removeChild(Node<T> p, boolean leftChild) {
		return null;
	}

	@Override
	public void removeAll() {
	}
	
	public static void main(String[] args) {
		BalancedTree<Integer> tree = new BalancedTree<Integer>(5);
		Integer[] array = {4, 2, 6, 20, 5, 8, 14, 19, 15, 30, 29, 17};
		for(Integer a: array) {
			tree.insert(a);
		}
		System.out.println("done");
	}
}
