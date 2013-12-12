package com.zqb.datastruct.list;
/**
 * 单链表
 * @author zhengquanbin
 *		   created 2013-12-12
 */
public class SinglyLinkedList<T> implements List<T>, Cloneable {

	private Node<T> head;
	private Node<T> tail;
	
	public SinglyLinkedList() {
		head = new Node<T>();
		tail = head;
	}
	
	public void add(T data) {
		Node<T> node = new Node<T>(data);
		tail.setNext(node);
		tail = node;
	}
	
	@Override
	public List<T> reverse() {
		try {
			@SuppressWarnings("unchecked")
			SinglyLinkedList<T> list = (SinglyLinkedList<T>) this.clone();
			Node<T> p = list.head.getNext(), succ, front = null;
			while(p!=null) {
				succ = p.getNext();
				p.setNext(front);
				front = p;
				p = succ;
			}
			/*
			 还可以用一种递归的方法来实现，但是有点奇怪，一时还没有想明白。可能在C中实现会好一点，java好像有点不太合适
			 Node * reverse(Node * head) {
				 if (head == NULL) return head;
				 if (head->next == NULL) return head;
				 Node * ph = reverse(head->next);
				 head->next->next = head;
				 head->next = NULL;
				 return ph;
			 }
			 */
			list.head.setNext(front);
			return list;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		Node<T> p = head.getNext();
		while(p!=null) {
			sb.append(p.getData()).append(",");
			p = p.getNext();
		}
		if(sb.length()>1) {
			sb.setCharAt(sb.length()-1, ']');
		} else {
			sb.append("]");
		}
		return sb.toString();
	}
}
