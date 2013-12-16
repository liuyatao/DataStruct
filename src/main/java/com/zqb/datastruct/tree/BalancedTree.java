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
	 * 添加一个结点
	 * @param data
	 */
	public void add(T data) {
		BalancedNode<T> node = root.setOrNext(data);
		while(node!=null) {
			node = node.setOrNext(data);
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
			tree.add(a);
		}
		System.out.println("done");
	}
}
