package com.glorypty.crawler.qgyyzs;

import java.util.ArrayList;
import java.util.List;

public class QgyyzsConstants {

	public static final int MAX_PAGE_SIZE = Integer.MAX_VALUE;
	
	/** 展会论坛 */
	public static final String ZHLT_URL_HOME = "http://www.qgyyzs.net/zhanhui/zh0_0.htm";
	/** 分页列表 */
	public static final String ZHLT_LIST_PRE = "http://www.qgyyzs.net/zhanhui/zh0_";
	/** 详情页列表 */
	public static final String ZHLT_DETAIL_PRE = "http://www.qgyyzs.net/zhanhui/html/";
	
	/** 招标信息 */
	public static final String ZBXX_URL_HOME = "http://zb.qgyyzs.net/zhaobiao_list.asp?language_sj=1";
	/** 分页列表 */
	public static final String ZBXX_LIST_PRE = "http://zb.qgyyzs.net/zhaobiao_list.asp?topage=";
	/** 分页 */
	public static final String ZBXX_LIST_PRE2 = "http://zb.qgyyzs.net/zhaobiao_list.asp";
	/** 详情页列表 */
	public static final String ZBXX_DETAIL_PRE = "http://zb.qgyyzs.net/show_zhaobiao.asp?zbid=";
	
	/** 求购信息 */
	public static final String QGXX_URL_HOME = "http://www.qgyyzs.net/qglist_0.htm";
	/** 分页列表 */
	public static final String QGXX_LIST_PRE = "http://www.qgyyzs.net/qglist_";
	/** 详情页列表 */
	public static final String QGXX_DETAIL_PRE = "http://www.qgyyzs.net/business/qg";
	
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
			urlList.add(ZHLT_URL_HOME);
			urlList.add(ZBXX_URL_HOME);
			urlList.add(QGXX_URL_HOME);
		}
		return urlList;
	}
	
}
