package com.glorypty.crawler.yao1;

import java.util.ArrayList;
import java.util.List;

public class Yao1Constants {
	
	public static final int MAX_PAGE_SIZE = Integer.MAX_VALUE;
	
	public static final String URL_HOME = "http://www.yao1.cn";
	
	/** 热点话题 */
	public static final String RDHT_URL_HOME = "http://www.yao1.cn/news/medicine-Ebusiness/index/more.html";
	/** 热点话题列表页 */
	public static final String RDHT_LIST_PRE = "http://www.yao1.cn/news/medicine-Ebusiness/index/more.html?p=";
	/** 详情页前缀 */
	public static final String RDHT_DETAIL_PRE = "http://www.yao1.cn/news/medicine-Ebusiness/detail/";
	
	/** 精英访谈 */
	public static final String JYFT_URL_HOME = "http://www.yao1.cn/top-talk/experts/more.html";
	/** 精英访谈列表页 */
	public static final String JYFT_LIST_PRE = "http://www.yao1.cn/top-talk/experts/more.html?p=";
	/** 详情页前缀 */
	public static final String JYFT_DETAIL_PRE = "http://www.yao1.cn/top-talk/detail/";
	
	
	/** 所有详情页链接 */
	public static List<String> detailUrls = new ArrayList<String>();
	
	/** 所有列表页链接个数 */
	public static int listUrlSize = 0;
	
	/**
	 * 访问第一页url集合
	 */
	public static List<String> urlList;
	
	public static List<String> getUrlList(){
		if(urlList==null||urlList.isEmpty()){
			urlList = new ArrayList<String>();
			urlList.add(RDHT_URL_HOME);
			urlList.add(JYFT_URL_HOME);
		}
		return urlList;
	}
	
}
