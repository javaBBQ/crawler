package com.glorypty.crawler.baiduyy;

import java.util.ArrayList;
import java.util.List;

/**
 * 百度虫基本信息类
 * @author ZhangLujun
 *
 */
public class BaiduyyConstants {

	public static final String COMMON_URL_HOME = "http://www.baiduyy.com/news09/newslist.aspx?page";
//	public static final int MAX_PAGE_SIZE = Integer.MAX_VALUE;
	public static final int MAX_PAGE_SIZE = 3;
	
	/** 资讯有效时间：月份 */
	public static final int EFFECTIVE_MONTH = -5;
	
	
	/**行业资讯-新品抢鲜*/
	public static final String XPQX_URL_HOME = "http://www.baiduyy.com/news09/newslist.aspx?uid=7";
	public static final String XPQX_URL_CONTENT = "http://www.baiduyy.com/news09/newsdetail.aspx?id";	
	public static final String XPQX_PAGE_URL_HOME_PRE = "http://www.baiduyy.com/news09/newslist.aspx";	
	/**行业资讯-警示平台 */
	public static final String JSPT_URL_HOME = "http://www.baiduyy.com/news09/newslist.aspx?uid=5";
	/**行业资讯-企业之窗 */
	public static final String QYZC_URL_HOME = "http://www.baiduyy.com/news09/newslist.aspx?uid=4";
	/**行业资讯-药价动态 */
	public static final String YJDT_URL_HOME = "http://www.baiduyy.com/news09/newslist.aspx?uid=3";
	/**政策法规-政策法规 */
	public static final String ZCFG_URL_HOME = "http://www.baiduyy.com/news09/newslist.aspx?uid=2";
	
	/**招标信息-招标新闻*/
	public static final String ZBXW_URL_HOME = "http://www.baiduyy.com/news09/newslist.aspx?uid=1";
	/**招标信息-招标信息*/
	public static final String ZBXX_URL_HOME = "http://www.baiduyy.com/zbxx/zbxx_index.asp";
	public static final String ZBXX_PAGE_URL_HOME_PRE = "http://www.baiduyy.com/zbxx/zbxx_index.asp?nowpage=";
	public static final String ZBXX_URL_CONTENT_PRE = "http://www.baiduyy.com/zbxx/zbxx_xx_";
	
	/**代理信息-代理信息*/
	public static final String DLXX_URL_HOME = "http://www.baiduyy.com/dailishang/index.aspx";
	public static final String DLXX_PAGE_URL_HOME_PRE = "http://www.baiduyy.com/dailishang/index.aspx?";
	public static final String DLXX_URL_CONTENT_PRE = "http://www.baiduyy.com/dailishang/dlsdetail.aspx?id=";
	public static final String DLXX_URL_CONTENT_PRE_TRUE = "http://www.baiduyy.com/dailishang/list.aspx";
	
	/**采购信息-求购信息*/
	public static final String QGXX_URL_HOME = "http://www.baiduyy.com/qgxx/qglist.asp?nowpage=0";
	public static final String QGXX_PAGE_URL_HOME_PRE = "http://www.baiduyy.com/qgxx/qglist.asp?nowpage=";
	public static final String QGXX_URL_CONTENT_PRE = "http://www.baiduyy.com/qgxx/";
	public static final String QGXX_URL_FOR_IMAGE = "http://www.baiduyy.com/";
	
	/**采购信息-药价文件*/
	public static final String YJWJ_URL_HOME = "http://www.baiduyy.com/yaojia/yaojiafile.asp";
	public static final String YJWJ_PAGE_URL_HOME_PRE = "http://www.baiduyy.com/yaojia/yaojiafile.asp?nowpage=";
	public static final String YJWJ_URL_CONTENT_PRE = "http://www.baiduyy.com/yaojia/xiangxi/jia_detail_";
	public static final String YJWJ_WEB_SETTING = "http://www.baiduyy.com/";
	
	/**招商信息-招商信息*/
	public static final String ZSXX_URL_HOME = "http://www.baiduyy.com/zsxx/zslist.aspx";
	public static final String ZSXX_PAGE_URL_HOME_PRE = "http://www.baiduyy.com/zsxx/zslist.aspx?page=";
	public static final String ZSXX_URL_CONTENT_PRE = "http://www.baiduyy.com/zsxx/zsxx_bsinfo02_";
	public static final String ZSXX_WEB_SETTING = "http://www.baiduyy.com/zsxx/";
	
	/**展会论坛-医药展会*/
	public static final String YYZH_URL_HOME = "http://yyzh.baiduyy.com/zhanhuilist.aspx";
	public static final String YYZH_URL_CONTENT = "http://yyzh.baiduyy.com/zhInfo.aspx?id";
	
	
	/**
	 * 访问第一页url集合
	 */
	public static List<String> urlList;
	
	public static List<String> getUrlList(){
		if(urlList==null||urlList.isEmpty()){
			urlList = new ArrayList<String>();
			urlList.add(XPQX_URL_HOME);
			urlList.add(ZBXW_URL_HOME);
			urlList.add(JSPT_URL_HOME);
			urlList.add(YJDT_URL_HOME);
			urlList.add(ZCFG_URL_HOME);
			//一般情况下此网址常访问不了
			urlList.add(QYZC_URL_HOME);
			urlList.add(ZBXX_URL_HOME);
			urlList.add(DLXX_URL_HOME);
			
			urlList.add(QGXX_URL_HOME);
			urlList.add(YJWJ_URL_HOME);
			urlList.add(ZSXX_URL_HOME);
			urlList.add(YYZH_URL_HOME);
			
			
		}
		return urlList;
	}
}
