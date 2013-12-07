package com.zqb.datastruct.tree;

import org.junit.Before;
import org.junit.Test;

public class BinaryTreeTest {

	private BinaryTree<String> btree;
	
	@Before
	public void before() {
		String[] prelist = {"a", "b", "d", null, "g", null, null, null, "c", "e", null, null, "f", "h"};
		btree = new BinaryTree<String>(prelist);
	}
	
	@Test
	public void testBinaryTree() {
		/*btree.preOrder();
		btree.levelOrder();
		System.out.println(btree.count());
		System.out.println(btree.height());*/
		//btree.levelOrderStruct();
		System.out.println(btree.toPreOrderList());
	}
}
