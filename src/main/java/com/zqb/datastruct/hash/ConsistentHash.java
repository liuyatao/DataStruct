package com.zqb.datastruct.hash;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性hash
 * @author zhengqb
 *         created 2014年1月7日
 */
public class ConsistentHash<T> {
	/**hash计算对象，用于自定义hash算法*/
	private HashFunc hashFunc;
	/**复杂的节点个数*/
	private final int numberOfReplicas;
	/**一致hash环*/
	private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();
	
	/**
	 * 构造，使用java默认认的hash算法 
	 * @param numberOfReplicas 复制的节点个数，增加每个节点的复制点有利于负载均衡
	 * @param nodes 节点对象 
	 */
	public ConsistentHash(int numberOfReplicas, Collection<T> nodes) {
		this.numberOfReplicas = numberOfReplicas;
		this.hashFunc = new HashFunc() {
			
			@Override
			public Integer hash(Object key) {
				String data = key.toString();
				//使用默认的FNV1hash算法
				final int p = 16777619;
				int hash = (int) 2166136261L;
				for(int i=0; i<data.length(); i++) {
					hash = (hash^data.charAt(i)*p);
				}
				hash += hash<<13;
				hash ^= hash>>7;
				hash += hash<<3;
				hash ^= hash>>17;
				hash += hash<<5;
				return hash;
			}
		};
		//初始化节点
		for(T node: nodes) {
			add(node);
		}
	}

	/**
	 * 增加节点<br/>
	 * 每增加一个节点，就会在闭环上增加给定复制节点数<br/>
	 * 例如复制节点是2，则每次调用此方法一次，增加丙从此虚拟节点，这两个节点指向同一node，由于hash算法会调用node的toString方法，故按照toString去重
	 * @param node
	 */
	public void add(T node) {
		for(int i=0; i<numberOfReplicas; i++) {
			circle.put(hashFunc.hash(node.toString()+i), node);
		}
	}
	
	/**
	 * 移除节点的同时移除相应的虚拟节点
	 * @param node
	 */
	public void remove(T node) {
		for(int i=0; i<numberOfReplicas; i++) {
			circle.remove(hashFunc.hash(node.toString()+i));
		}
	}
	
	/**
	 * 获得最近顺时针节点
	 * @param key
	 * @return
	 */
	public T get(Object key) {
		if(circle.isEmpty()) {
			return null;
		}
		int hash = hashFunc.hash(key);
		if(!circle.containsKey(hash)) {
			SortedMap<Integer, T> tailMap = circle.tailMap(hash);//返回此映射的部分视图，其键大于等于hash
			hash = tailMap.isEmpty()?circle.firstKey():tailMap.firstKey();
		}
		//正好命中
		return circle.get(hash);
	}
	
	/**
	 * hash算法对象，用于自定义hash算法
	 * @author zhengqb
	 *         created 2014年1月7日
	 */
	public interface HashFunc {
		
		Integer hash(Object key);
	}
	
	public static void main(String[] args) {
		
		HashFunc func = new HashFunc() {
			
			@Override
			public Integer hash(Object key) {
				String data = key.toString();
				//使用默认的FNV1hash算法
				final int p = 16777619;
				int hash = (int) 2166136261L;
				for(int i=0; i<data.length(); i++) {
					hash = (hash^data.charAt(i)*p);
				}
				hash += hash<<13;
				hash ^= hash>>7;
				hash += hash<<3;
				hash ^= hash>>17;
				hash += hash<<5;
				return hash;
			}
		};
		System.out.println(func.hash(100));
	}
}
