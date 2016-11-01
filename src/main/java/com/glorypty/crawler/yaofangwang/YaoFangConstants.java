package com.glorypty.crawler.yaofangwang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 药房网基本类
 * @author Administrator
 *
 */
public class YaoFangConstants {

	/**药企-省份 */
	public static Map<String, String> provinceMap = new HashMap<String, String>();
	/** 药企详细-省份 */
	public static Map<String, String> url_provinceMap = new HashMap<String, String>();

	public static String YC_HOME = "http://yaochang.yaofangwang.com/";
	
	/*************** 分割线 ******************/
	/**医院*/
	public static Map<String, String> yy_provinceMap = new HashMap<String, String>();
	public static Map<String, String> yy_page_provinceMap = new LinkedHashMap<String, String>();
	public static Map<String, String> yy_detail_provinceMap = new LinkedHashMap<String, String>();
	public static Map<String, Integer> yy_province_PageMap = new HashMap<String, Integer>();
	public static List<String> yy_detailUrlList = new ArrayList<String>();
	public static int perPageNum = 20;
	
	public static String YY_HOME = "http://yiyuan.yaofangwang.com/";
	public static String YY_URL = "http://yiyuan.yaofangwang.com/hospital_0_p100000.html";
	
	/*************** 分割线 ******************/
	/**药房*/
	public static List<String> yf_detailUrlList = new ArrayList<String>();
	public static int totalPage = 0;
	public static String YF_HOME = "http://yaodian.yaofangwang.com/";
	public static String YF_URL = "http://yaodian.yaofangwang.com/shops-4947-0-1000000.html";
	public static String YF_PAGE_URL = "http://yaodian.yaofangwang.com/shops-4947-0-{0}.html";
	
}
