package com.zqb.datastruct.list;

import org.junit.Test;

public class SinglyLinkedListTest {

	@Test
	public void testReverse() {
		List<String> list = new SinglyLinkedList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		
		System.out.println(list);
		System.out.println(list.reverse());
	}
}
