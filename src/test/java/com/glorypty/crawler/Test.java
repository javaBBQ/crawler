/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawlerTest.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Andy
 * @date 2015年6月18日
 * @version 1.0
 */
public class Test {
	public static final int COUNT = 100000;
	public static void main(String[] args) {
		try {
			Scheduler sched = new StdSchedulerFactory().getScheduler();
			sched.start();
		} catch (SchedulerException e) {
			System.out.println("Quartz初始化异常");
			e.printStackTrace();
		}
//		String array = "ArrayList";
//		String linked = "LinkedList";
//		List<String> arrayList = add(array);
//		List<String> linkedList = add(linked);
//		read(array, arrayList);
//		read(linked, linkedList);
	}
	
	private static List<String> add(String type){
		List<String> list = null;
		long start = System.currentTimeMillis();
		String str = "i am no.";
		if(type.equals("ArrayList")){
			list = new ArrayList<String>();
			for (int i = 0; i < COUNT; i++) {
				list.add(str+i);
			}
		}else if(type.equals("LinkedList")){
			list = new  LinkedList<String>();
			for (int i = 0; i < COUNT; i++) {
				list.add(str+i);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(type+"新增"+COUNT+"元素花费 :"+(end - start)+"ss");
		return list;
	}
	
	private static void read(String type,List<String> list){
		long start = System.currentTimeMillis();
		if(type.equals("ArrayList")){
			for (int i = 0; i < list.size(); i++) {
				list.get(i);
			}
		}else if(type.equals("LinkedList")){
			for (int i = 0; i < list.size(); i++) {
				list.get(i);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(type+"遍历"+list.size()+"元素花费 :"+(end - start)+"ss");
	}
	
	
}

