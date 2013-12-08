package com.zqb.datastruct.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTree<T> implements Tree<T> {

	private BinaryNode<T> root;
	
	public BinaryTree() {
	}
	public BinaryTree(T[] prelist) {
		i = 0;	//初始化i的值
		this.root = create(prelist);
	}
	
	/**
	 * 主要是用于在构造一个二叉树的时，标志创建的位置
	 */
	private int i;
	/**
	 * 构造一个二叉树
	 * @param prelist 标明空子树的先根序列
	 */
	private BinaryNode<T> create(T[] prelist) {
		if(i<prelist.length) {
			T elem = prelist[i];
			i++;
			if(elem!=null) {
				BinaryNode<T> node = new BinaryNode<T>(elem);
				node.setLeft(create(prelist));
				node.setRight(create(prelist));
				return node;
			} else if(i==1) {
				throw new IllegalArgumentException("root node must not null");
			}
		}
		return null;
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int count() {
		return count(root);
	}
	private int count(BinaryNode<T> node) {
		if(node==null) {
			return 0;
		}
		return 1+count(node.getLeft())+count(node.getRight());
	}

	@Override
	public int height() {
		return height(root);
	}
	private int height(BinaryNode<T> node) {
		if(node==null) {
			return 0;
		}
		int lh = height(node.getLeft());
		int rh = height(node.getRight());
		return lh>rh?lh+1:rh+1;
	}

	@Override
	public void preOrder() {
		System.out.print("先根次育遍历二叉树：");
		preOrder(root);
		System.out.println();
	}
	
	public void preOrder(BinaryNode<T> node) {
		if(node!=null) {
			System.out.print(node.getData() + ", ");
			preOrder(node.getLeft());
			preOrder(node.getRight());
		}
	}
	
	
	public void printPath() {
		T[] path = (T[]) new Object[height()];
		printPath(root, path, 0);
	}
	/**
	 * 求出路径的递归函数
	 * @param node
	 * @param path
	 * @param top
	 */
	private void printPath(BinaryNode<T> node, T[] path, int top) {
		path[top++] = node.getData();
		if(node.getLeft()==null && node.getRight()==null) {
			for(int i=0; i<top; i++) {
				System.out.print(path[i]+", ");
			}
			System.out.println();
		} else {
			if(node.getLeft()!=null) {
				printPath(node.getLeft(), path, top);
			}
			if(node.getRight()!=null) {
				printPath(node.getRight(), path, top);
			}
		}
	}
	
	public List<BinaryNode<T>> toPreOrderList() {
		List<BinaryNode<T>> list = new LinkedList<BinaryNode<T>>();
		toPreOrderList(root, list);
		return list;
	}
	private void toPreOrderList(BinaryNode<T> node, List<BinaryNode<T>> list) {
		if(node!=null) {
			list.add(node);
			toPreOrderList(node.getLeft(), list);
			toPreOrderList(node.getRight(), list);
		}
	}

	@Override
	public void inOrder() {
		
	}

	@Override
	public void postOrder() {
		
	}

	@Override
	public void levelOrder() {
		Queue<BinaryNode<T>> queue = new LinkedList<BinaryNode<T>>();
		System.out.print("层次遍历：");
		BinaryNode<T> node = this.root;
		while(node!=null) {
			System.out.print(node.getData()+", ");
			if(node.getLeft()!=null) {
				queue.offer(node.getLeft());
			}
			if(node.getRight()!=null) {
				queue.offer(node.getRight());
			}
			node = queue.poll();
		}
		System.out.println();
	}
	public void levelOrderStruct() {
		Queue<BinaryNode<T>> queue = new LinkedList<BinaryNode<T>>();
		System.out.println("层次遍历：");
		queue.offer(this.root);
		while(!queue.isEmpty()) {
			Queue<BinaryNode<T>> _queue = new LinkedList<BinaryNode<T>>();
			while(!queue.isEmpty()) {
				BinaryNode<T> n = queue.poll();
				System.out.print(n.getData()+" ");
				if(n.getLeft()!=null) {
					_queue.offer(n.getLeft());
				}
				if(n.getRight()!=null) {
					_queue.offer(n.getRight());
				}
			}
			System.out.println();
			queue.addAll(_queue);
		}
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
	
	public BinaryNode<T> getRoot() {
		return root;
	}
	public void setRoot(BinaryNode<T> root) {
		this.root = root;
	}
}
