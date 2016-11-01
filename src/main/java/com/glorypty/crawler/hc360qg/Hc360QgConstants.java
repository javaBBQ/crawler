/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.hc360.Hc360Constants.java <2015年12月17日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.hc360qg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import cn.xkshow.io.file.PropertyFile;

/**
 * hc360聪慧网基本类	
 * @author ZhangLujun 
 * @Date 2015年12月17日 上午10:25:40
 * @version 1.0
 */
public class Hc360QgConstants {
	
	/**hc360爬虫记录存放目录*/
	public static final String RECORD_DIR = "hc360qg/";
	
	/**慧聪网求购首页*/
	public static String QG_HOME = "http://b2b.hc360.com/supermarket/ylb/buy.html";
	
	/**慧聪网求购详情页*/
	public static List<String> DETAIL_LIST_URL = new ArrayList<String>();
	
	/**聪慧网需抓取分类首页*/
	public static List<String> HOME_LIST_URL = new ArrayList<String>(){
		private static final long serialVersionUID = 6910035418931343695L;
		{
			add(QG_HOME);
		}
	};
	
	/**获取设置爬取多少天之前数据,默认爬取前一天至现在数据*/
	public static int getPastDay(){
		Properties pros = PropertyFile.getInstance().loadingProperties("system.properties");
		String pastDay = pros.getProperty("cmsgoods_past_day");
		return (pastDay==null||"".equals(pastDay))?-1:Integer.parseInt(pastDay);
	}
}
