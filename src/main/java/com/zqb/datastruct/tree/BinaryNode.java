package com.zqb.datastruct.tree;
/**
 * 二叉结点
 * @author zhengquanbin
 *		   created 2013-12-7
 */
public class BinaryNode<T> extends Node<T> {

	private T data;
	private BinaryNode<T> left, right;
	
	public BinaryNode(T data) {
		this.data = data;
	}
	public BinaryNode(T data, BinaryNode<T> left, BinaryNode<T> right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public BinaryNode<T> getLeft() {
		return left;
	}
	public void setLeft(BinaryNode<T> left) {
		this.left = left;
	}
	public BinaryNode<T> getRight() {
		return right;
	}
	public void setRight(BinaryNode<T> right) {
		this.right = right;
	}
	@Override
	public String toString() {
		return "BinaryNode [data=" + data + "]";
	}
}
