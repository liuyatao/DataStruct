package com.zqb.datastruct.list;

import org.junit.Test;

import com.zqb.datastruct.list.test.StackWithMin;

public class StackWithMinTest {

	@Test
	public void test() {
		StackWithMin<Integer> min = new StackWithMin<Integer>();
		Integer[] array = {32, 23, 43, 23, 21, 243, 34, 4, 65, 454, 43, 32, 6, 56, 76, 29, 2};
		for(int i=0; i<array.length; i++) {
			min.push(array[i]);
		}
		for(int i=0; i<5; i++) {
			min.pop();
		}
		System.out.println(min.min());
	}
}
