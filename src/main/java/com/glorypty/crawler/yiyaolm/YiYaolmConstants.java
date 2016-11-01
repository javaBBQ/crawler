package com.glorypty.crawler.yiyaolm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**中国医药联盟
 * 生意经--医药营销 131
 * http://www.chinamsr.com/xiaoshoujiqiao/
 * @author Administrator
 *
 */
public class YiYaolmConstants {
	
	/** 行业资讯 -- 生意经  */
//	public static final String YYYX_URL_HOME = "http://www.chinamsr.com/xiaoshoujiqiao/1.shtml";
	public static final String YYYX_URL_HOME = "http://www.chinamsr.com/xiaoshoujiqiao/";
	public static final String YYYX_PAGE_URL_HOME = "http://www.chinamsr.com/xiaoshoujiqiao/{0}.shtml";
	public static final String YYYX_PAGE_URL_HOME_PRE = "http://www.chinamsr.com/xiaoshoujiqiao/";
	

	public static final String HOMR_URL = "http://www.chinamsr.com/xiaoshoujiqiao/";
	public static int fixedNum = 106;
	public static int totalPage = 0;
	
	/**详细页面*/
	public static final String COMMON_URL_CONTENT = "http://www.chinamsr.com/";
	public static final String URL_CONTENT_PRE = "http://www.chinamsr.com/";
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
			urlList.add(YYYX_URL_HOME);
		}
		return urlList;
	}
	
	
	public static Map<String,String> getUrlMap(){
		if(urlMap==null||urlMap.isEmpty()){
			urlMap.put(YYYX_URL_HOME, YYYX_PAGE_URL_HOME);
		}
		return urlMap;
	}
}
