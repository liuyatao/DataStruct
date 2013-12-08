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
	 * 这是两条链表没有环的情况下
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
	static boolean testCylic(LinkedList<String> link) {
		Iterator<String> itr = link.iterator();
		String start = itr.next();
		String current = null;
		return false;
	}
}
