package com.zqb.datastruct.tree;

public class BinaryTree<T> implements Tree<T> {

	private BinaryNode<T> root;

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int count() {
		return 0;
	}

	@Override
	public int heigth() {
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

}
