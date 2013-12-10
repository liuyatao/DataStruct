package com.zqb.datastruct.list;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 给出俩个单向链表的头指针，比如h1，h2，判断这俩个链表是否相交。为了简化问题，我们假设俩个链表均不带环。
 * 1、对于没有环，相交的连表，那他们总会聚到一条链上来
 * @author zhengquanbin
 *		   created 2013-12-8
 */
public class JoinedSimple {

	/**
	 * 这是两条链表没有环的情况下，
	 * 
	 * 如要这两条链存在环（也可能不存在的情况下，至少一个存在环），那就会出现两种情况：
	 * 1、有且只有一个环，那两条链表就一定不会相交
	 * 2、两个都是环的情况下，如果其中一个环在迭代了一圈之后又和自己的的header相等，并且不等于别一个环的值的话，就那说明这两个环是不相交的，反之就是相交
	 * 
	 * @param l1
	 * @param l2
	 * @return
	 */
	static boolean isJoinedSimple(LinkedList<String> l1, LinkedList<String> l2) {
		Iterator<String> itr1 = l1.iterator();
		String s1 = null;
		while(itr1.hasNext()) {
			s1 = itr1.next();
		}
		
		Iterator<String> itr2 = l2.iterator();
		String s2 = null;
		while(itr2.hasNext()) {
			s2 = itr2.next();
		}
		
		return s1==s2;
	}
	
	/**
	 * 检察是否存在环
	 * @param link
	 * @return
	 */
	static LinkedList<String> testCylic(LinkedList<String> link) {
		Iterator<String> itr = link.iterator();
		String start = itr.next();
		String current = null;
		return null;
		/* 在此处,c是这样写的，每次p2比p1就是多一个，这样累加，如果存在环，那么在这里肯定会出现p1==p2的情况
		 * 当在这样场景，java可能就会比较难表现出来，这也是java是弱化指针的概念的一下弊端
		
		Node* testCylic(Node * h1) {
	  		Node * p1 = h1, *p2 = h1;
	  		while (p2!=NULL && p2->next!=NULL) {
	  			p1 = p1->next;
	    		p2 = p2->next->next;
	    		if (p1 == p2) {
	      			return p1;
	    		}
	  		}
	  		return NULL;
		}
		*/
	}
}
