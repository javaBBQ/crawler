package com.glorypty.crawler.yaolutong;

import java.util.ArrayList;
import java.util.List;

/**
 * 药路通基本信息类
 * @author Yiwen
 *
 */
public class YaolutongConstants {
	public static final String COMMON_URL_HOME = "http://www.yaolutong.com/";
	public static final int MAX_PAGE_SIZE = 3;
	
	/** 资讯有效时间：月份 */
	public static final int EFFECTIVE_MONTH = -1;
	
	
	public static int TotalNum = 1;
	
	
	/**招商信息*/
	public static final String ZHAOSHANG_URL_HOME = "http://www.yaolutong.com/zhaoshang/";
	public static final String ZHAOSHANG_PAGE_URL_HOME_PRE = "http://www.yaolutong.com/zhaoshang/index.aspx?page=";
	public static final String ZHAOSHANG_URL_CONTENT_PRE = "http://www.yaolutong.com/zhaoshang/single.aspx?id=";
	
	/**代理信息*/
	public static final String DAILI_URL_HOME = "http://www.yaolutong.com/daili/";
	public static final String DAILI_PAGE_URL_HOME_PRE = "http://www.yaolutong.com/daili/index.aspx?page=";
	public static final String DAILI_URL_CONTENT_PRE = "http://www.baiduyy.com/DAILI/";
	
	public static List<String> urlDetailList = new ArrayList<String>();
	public static List<String> urlTotalList = new ArrayList<String>();
	/**
	 * 访问第一页url集合
	 */
	public static List<String> urlList;
	
	public static List<String> getUrlList(){
		if(urlList==null||urlList.isEmpty()){
			urlList = new ArrayList<String>();
			urlList.add(ZHAOSHANG_URL_HOME);
			/*urlList.add(DAILI_URL_HOME);*/
		}
		return urlList;
	}
}
