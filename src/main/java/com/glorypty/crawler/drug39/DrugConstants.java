/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.drug39.DrugConstants.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.drug39;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andy
 * @date 2015年6月24日
 * @version 1.0
 */
public class DrugConstants {
	
	/**热点话题-行业观察*/
	public static final String RDFT_HYGC_URL_HOME= "http://news.39.net/hygc/";
	/**热点话题-医药新闻*/
	public static final String RDFT_YYXW_URL_HOME = "http://drug.39.net/yjxw/yydt/";
	/**热点话题-药企动态*/
	public static final String RDFT_YQDT_URL_HOME = "http://drug.39.net/yjxw/yqyf/";
	/**热点话题-新药动态*/
	public static final String RDFT_XYDT_URL_HOME = "http://drug.39.net/yjxw/xydt/";
	
	/**最新科研-新药研发*/
	public static final String ZXKY_XYYF_URL_HOME = "http://drug.39.net/yjxw/lcyf/";
	
	/**商情分析-药店资讯*/
	public static final String SQFX_YDZX_URL_HOME = "http://drug.39.net/yjxw/yfdt/";
	
	public static  int totalPage = 1;
	public static Map<String,Integer> typeMap = new HashMap<String, Integer>();
	public static List<String> detailList = new ArrayList<String>();
	
	public static Map<String,Integer> getTypeMap(){
		if(typeMap == null || typeMap.isEmpty()){
			//热点话题
			typeMap.put(RDFT_HYGC_URL_HOME, new Integer(106));
			typeMap.put(RDFT_YYXW_URL_HOME, new Integer(106));
			typeMap.put(RDFT_YQDT_URL_HOME, new Integer(106));
			typeMap.put(RDFT_XYDT_URL_HOME, new Integer(106));
			//最新科研
			typeMap.put(ZXKY_XYYF_URL_HOME, new Integer(103));
			//商情分析
			typeMap.put(SQFX_YDZX_URL_HOME, new Integer(104));
		}
		return typeMap;
	}
	
}

