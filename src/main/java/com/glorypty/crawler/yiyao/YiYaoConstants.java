package com.glorypty.crawler.yiyao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 100医药网基本信息类
 * @author Administrator
 *
 */
public class YiYaoConstants {
	
	/** 行业资讯 -- 政策法规  */
	public static final String ZCFG_URL_HOME = "http://news.100yiyao.com/116/1.html";
	public static final String ZCFG_PAGE_URL_HOME = "http://news.100yiyao.com/news/116/{0}.html";
	public static final String ZCFG_PAGE_URL_HOME_PRE = "http://news.100yiyao.com/news/116/";
	
	/** 行业资讯 -- 社会关注  */
	public static final String SHGZ_URL_HOME = "http://news.100yiyao.com/90/1.html";
	public static final String SHGZ_PAGE_URL_HOME = "http://news.100yiyao.com/news/90/{0}.html";
	public static final String SHGZ_PAGE_URL_HOME_PRE = "http://news.100yiyao.com/news/90/";
	
	/** 行业资讯 -- 医药企业新闻  */
	public static final String YYQYXW_URL_HOME = "http://news.100yiyao.com/452/1.html";
	public static final String YYQYXW_PAGE_URL_HOME = "http://news.100yiyao.com/news/452/{0}.html";
	public static final String YYQYXW_PAGE_URL_HOME_PRE = "http://news.100yiyao.com/news/452/";
	
	/** 行业资讯 -- 医药新闻 */
	public static final String YYXW_URL_HOME = "http://news.100yiyao.com/350/1.html";
	public static final String YYXW_PAGE_URL_HOME = "http://news.100yiyao.com/news/350/{0}.html";
	public static final String YYXW_PAGE_URL_HOME_PRE = "http://news.100yiyao.com/news/350/";
	
	/** 展会论坛-- 展会 */
	public static final String ZH_URL_HOME = "http://zhanhui.100yiyao.com/55/1.html";
	public static final String ZH_PAGE_URL_HOME = "http://zhanhui.100yiyao.com/news/55/{0}.html";
	public static final String ZH_PAGE_URL_HOME_PRE = "http://zhanhui.100yiyao.com/news/55/";
	
	/** 供货商 -- 医药营销 */
	public static final String YYYX_URL_HOME = "http://news.100yiyao.com/115/1.html";
	public static final String YYYX_PAGE_URL_HOME = "http://news.100yiyao.com/news/115/{0}.html";
	public static final String YYYX_PAGE_URL_HOME_PRE = "http://news.100yiyao.com/news/115/";
	
	/** 招标信息-- 医药招投标 */
	public static final String YYZTB_URL_HOME = "http://news.100yiyao.com/390/1.html";
	public static final String YYZTB_PAGE_URL_HOME = "http://news.100yiyao.com/news/390/{0}.html";
	public static final String YYZTB_PAGE_URL_HOME_PRE = "http://news.100yiyao.com/news/390/";
	
	/**详细页面*/
	public static final String COMMON_URL_CONTENT = "http://news.100yiyao.com/detail/";
	public static final String URL_CONTENT_PRE = "http://news.100yiyao.com";
	
	
	/** 最新入驻-- 药企列表  */
//	public static final String YQLB_URL_HOME = "http://www.100yiyao.com/company/3,0,0,1.html";
	
	/** 招标信息-- 医药招标中标 */
	public static final String YYZBZB_URL_HOME = "http://www.100yiyao.com/zb/";
	public static final String YYZBZB_PAGE_URL_HOME = "http://www.100yiyao.com/zb/list/0,{0}.html";
	public static final String YYZBZB_PAGE_URL_HOME_PRE = "http://www.100yiyao.com/zb/list/";
	public static final String YYZBZB_PAGE_URL_CONTENT = "http://www.100yiyao.com/zb/0,";
	
	/** 招商信息-- 医药招商 */
	public static final String YYZS_URL_HOME = "http://www.100yiyao.com/product/";
	public static final String YYZS_PAGE_URL_HOME = "http://www.100yiyao.com/product/1,{0}.html";
	public static final String YYZS_PAGE_URL_HOME_PRE = "http://www.100yiyao.com/product/1,";
	public static final String YYZS_PAGE_URL_CONTENT = "http://www.100yiyao.com/product/";
	
	
	/** 代理信息 -- 代理信息 */
	public static final String DLXX_URL_HOME = "http://www.100yiyao.com/agent/";
	public static final String DLXX_PAGE_URL_HOME = "http://www.100yiyao.com/agentlist/{0}.html";
	public static final String DLXX_PAGE_URL_HOME_PRE = "http://www.100yiyao.com/agentlist/";
	public static final String DLXX_PAGE_URL_CONTENT = "http://www.100yiyao.com/agent/";
	public static final String HOMR_URL = "http://www.100yiyao.com";
	public static int fixedNum = 1000;
	public static String fixedDate = "2015-01-01";
	
	/**
	 * 访问第一页url集合
	 */
	public static Map<String,String> urlMap = new HashMap<String, String>();
	
	/**
	 * 访问第一页url集合
	 */
	public static List<String> urlList = new ArrayList<String>();
	
	/**详细页集合*/
	public static List<String> detail_list = new ArrayList<String>();
	
	/**
	 * 信息抓取有效时间
	 */
	public static int PAST_MONTH = -5;
	
	public static List<String> getUrlList(){
		if(urlList==null||urlList.isEmpty()){
			urlList.add(ZCFG_URL_HOME);
			urlList.add(SHGZ_URL_HOME);
			urlList.add(YYQYXW_URL_HOME);
			urlList.add(YYXW_URL_HOME);
			urlList.add(ZH_URL_HOME);
			urlList.add(YYYX_URL_HOME);
			urlList.add(YYZTB_URL_HOME);
			urlList.add(YYZBZB_URL_HOME);
			urlList.add(YYZS_URL_HOME);
			urlList.add(DLXX_URL_HOME);
		}
		return urlList;
	}
	
	
	public static Map<String,String> getUrlMap(){
		if(urlMap==null||urlMap.isEmpty()){
			urlMap.put(ZCFG_URL_HOME, ZCFG_PAGE_URL_HOME);
			urlMap.put(SHGZ_URL_HOME, SHGZ_PAGE_URL_HOME);
			urlMap.put(YYQYXW_URL_HOME, YYQYXW_PAGE_URL_HOME);
			urlMap.put(YYXW_URL_HOME, YYXW_PAGE_URL_HOME);
			urlMap.put(ZH_URL_HOME, ZH_PAGE_URL_HOME);
			urlMap.put(YYYX_URL_HOME, YYYX_PAGE_URL_HOME);
			urlMap.put(YYZTB_URL_HOME,YYZTB_PAGE_URL_HOME);
			urlMap.put(YYZBZB_URL_HOME, YYZBZB_PAGE_URL_HOME);
		}
		return urlMap;
	}
}
