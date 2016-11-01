/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.hc360.Hc360Constants.java <2015年12月17日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.hc360;

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
public class Hc360Constants {
	
	/**hc360爬虫记录存放目录*/
	public static final String RECORD_DIR = "hc360/";
	
	/**保健品-列表首页*/
	public static String BJP_HOME = "http://www.hc360.com/prod-z2/pbse5rwbru.html";
	/**保健品-列表页前缀*/
	public static String BJP_PRD_LIST_PR_URL = "http://www.hc360.com/prod-z2/pbse5rwbru";
	/**保健品-列表分页*/
	public static String BJP_PRD_LIST_URL = "http://www.hc360.com/prod-z2/pbse5rwbru-{0}.html";
	/**保健品-商品详情页集合*/
	public static List<String> bjp_deatil_url_list = new LinkedList<String>();
	public static int bjp_total = 0;
	
	/**医疗器械-列表首页*/
	public static String YLQX_HOME = "http://www.hc360.com/prod-l2/b5xz4wbwy5.html";
	/**医疗器械-列表页前缀*/
	public static String YLQX_PRD_LIST_PR_URL = "http://www.hc360.com/prod-l2/b5xz4wbwy5";
	/**医疗器械-列表分页*/
	public static String YLQX_PRD_LIST_URL = "http://www.hc360.com/prod-l2/b5xz4wbwy5-{0}.html";
	/**医疗器械-商品详情页集合*/
	public static List<String> ylqx_deatil_url_list = new LinkedList<String>();
	public static int ylqx_total = 0;
	
	/**药品-列表首页*/
	public static String YP_HOME = "http://s.hc360.com/?mc=seller&w=%D2%A9";
	/**药品-列表页前缀*/
	public static String YP_PRD_LIST_PR_URL = "http://s.hc360.com/?ee=";
	public static String YP_PRD_LIST_PR_URL2 = "http://s.hc360.com/?af=";
	public static String endChar = "&mc=seller&w=%D2%A9";
	/**药品-列表分页*/
	public static String YP_PRD_LIST_URL = "http://s.hc360.com/?mc=seller&w=%D2%A9&ee={0}";
	/**药品-商品详情页集合*/
	public static List<String> yp_deatil_url_list = new LinkedList<String>();
	public static int yp_total = 0;
	
	/**商品详情页前缀*/
	public static String PRD_DETAIL_PR_URL = "http://b2b.hc360.com/supplyself/";
	
	/**商品详情页集合*/
	public static List<String> prd_deatil_url_list = new LinkedList<String>();
	
	/**聪慧网需抓取分类首页*/
	public static List<String> HOME_LIST_URL = new ArrayList<String>(){
		private static final long serialVersionUID = 6910035418931343695L;
		{
			add(BJP_HOME);
			add(YLQX_HOME);
			add(YP_HOME);
		}
	};
	
	/**获取设置爬取页数*/
	public static int getPageNum(){
		Properties pros = PropertyFile.getInstance().loadingProperties("system.properties");
		String pageNum = pros.getProperty("cmsgoods_page_num");
		return (pageNum==null||"".equals(pageNum))?0:Integer.parseInt(pageNum);
	}
}
