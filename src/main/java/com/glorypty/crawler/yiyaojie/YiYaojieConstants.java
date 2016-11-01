package com.glorypty.crawler.yiyaojie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**医药界
 * 热点话题--医改评论 106
 * http://www.yiyaojie.com/ft/ygzc/
 * @author yiwen
 *
 */
public class YiYaojieConstants {
	
	/** 行业资讯 -- 生意经  */
	public static final String YYJie_URL_HOME = "http://www.yiyaojie.com/ft/ygzc/";
	public static final String YYJie_PAGE_URL_HOME = "http://www.yiyaojie.com/ft/ygzc/list_60_{0}.html";
	public static final String YYJie_PAGE_URL_HOME_PRE = "http://www.yiyaojie.com/ft/ygzc/";
	
	/** 107  精英访谈
	 * http://www.yiyaojie.com/ft/sjjy/ 商界精英
	 * http://www.yiyaojie.com/ft/zjry/ 专家论语
	 */
	public static final String YYJie_SJJY_URL_HOME = "http://www.yiyaojie.com/ft/sjjy/";
	public static final String YYJie_SJJY_PAGE_URL_HOME = "http://www.yiyaojie.com/ft/sjjy/list_39_{0}.html";
	public static final String YYJie_SJJY_PAGE_URL_HOME_PRE = "http://www.yiyaojie.com/ft/sjjy/";

	public static final String YYJie_ZJRY_URL_HOME = "http://www.yiyaojie.com/ft/zjry/";
	public static final String YYJie_ZJRY_PAGE_URL_HOME = "http://www.yiyaojie.com/ft/zjry/list_40_{0}.html";
	public static final String YYJie_ZJRY_PAGE_URL_HOME_PRE = "http://www.yiyaojie.com/ft/zjry/";

	
	public static final String HOMR_URL = "http://www.yiyaojie.com/ft/ygzc/";
	public static int fixedNum = 106;
	public static int totalPage = 0;
	public static int totalNum = 0;
	
	/**详细页面*/
	public static final String COMMON_URL_CONTENT = "http://www.yiyaojie.com";
	public static final String URL_CONTENT_PRE = "http://www.yiyaojie.com";
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
			urlList.add(YYJie_URL_HOME);
			urlList.add(YYJie_SJJY_URL_HOME);
			urlList.add(YYJie_ZJRY_URL_HOME);
		}
		return urlList;
	}
	
	
	public static Map<String,String> getUrlMap(){
		if(urlMap==null||urlMap.isEmpty()){
			urlMap.put(YYJie_URL_HOME, YYJie_PAGE_URL_HOME);
			urlMap.put(YYJie_SJJY_URL_HOME, YYJie_SJJY_PAGE_URL_HOME);
			urlMap.put(YYJie_ZJRY_URL_HOME, YYJie_SJJY_PAGE_URL_HOME);
		}
		return urlMap;
	}
}
