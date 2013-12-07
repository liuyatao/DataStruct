package com.zqb.datastruct.list.test;
/**
 * 输入一个整形数组，数组里有正数也有负数。数组中连续的一个或多个整数组成一个子数组，每个子数组都有一个和。求所有子数组的和的最大值。要求时间复杂度为O(n)。
 * @author zhengquanbin
 *		   created 2013-12-7
 */
public class SumOfSubArray {

	public static void main(String[] args) {
		int sz[] = {1,-2,3,10,-4,7,2,-5, 40};
		System.out.println(maxSum(sz, sz.length));
	}
	
	static int maxSum(int[] sz, int len) {
		int maxSumHere = 0;
		int maxSumCur = 0;
		for(int i=0; i<len; i++) {
			if(maxSumHere+sz[i]>0) {
				maxSumHere = maxSumHere + sz[i];
			} else {
				maxSumHere = 0;
			}
			if(maxSumHere>maxSumCur) {
				maxSumCur = maxSumHere;
			}
		}
		return maxSumCur;
	}
}
