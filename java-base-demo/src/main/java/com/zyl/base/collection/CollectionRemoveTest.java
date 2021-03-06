package com.zyl.base.collection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class CollectionRemoveTest {

	public static void main(String[] args) {
		ArrayList list = new ArrayList<String>(); //ArrayList 底层实现为 数组
		list.add(String.valueOf("1"));
		list.add(String.valueOf("2"));
		
		list.remove(0); //result :OK
		Iterator listIterator = list.iterator();
		while(listIterator.hasNext()){
			listIterator.next();
			listIterator.remove(); //result: 不加上一行的next代码报错java.lang.IllegalStateException
		}
		System.out.println("ArrayList end.............");
		
		LinkedList<String> linkList= new LinkedList<String>(); //LinkedList 底层实现为 单链表
		linkList.add(String.valueOf("1"));
		linkList.add(String.valueOf("2"));
		linkList.remove();
		Iterator linkListIterator = list.iterator();
		while(linkListIterator.hasNext()){
			linkListIterator.next();
			linkListIterator.remove(); //result:不加上一行的next代码报错java.lang.IllegalStateException
		}
		System.out.println("linkList end.............");
		
		Set<String> set= new HashSet<String>(); //HashSet 底层实现为hashmap
		set.add(String.valueOf("1"));
		set.add(String.valueOf("2"));
		set.remove(new String("1"));//set.remove("1");  两种写法都可 remove 成功
		Iterator setIterator = set.iterator();
		while(setIterator.hasNext()){
			setIterator.next();
			setIterator.remove(); //result:不加上一行的next代码报错java.lang.IllegalStateException
			break;
		}
		System.out.println("set end............." + set.size());
	}

}
