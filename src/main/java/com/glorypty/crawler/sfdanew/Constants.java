/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.sfda.Constants.java	<2015年4月2日 >
 ****************************************************************
 * 版权所有@2015 国裕秀网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.sfdanew;

import java.util.ArrayList;
import java.util.List;

import com.glorypty.crawler.common.ConstantsCrawler;

/**
 *
 * @Author yiwen
 * @Date 2015年4月2日 下午3:15:45
 */
public class Constants {
	/**
	 * 执行步骤数量(值2|3)：三步可缩减(因每个分类的文章序列均是从1开始)为两步，即中间步骤可省略
	 * 1.http://app1.sfda.gov.cn/datasearch/face3/dir.html
	 * 2.http://app1.sfda.gov.cn/datasearch/face3/base.jsp
	 * 	---->转http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=108&curstart=2
	 * 3.http://app1.sfda.gov.cn/datasearch/face3/content.jsp?tableId=108&tableName=TABLE108&Id=24
	 */
	public static Integer NUMBER_STEP = 3;	
	
	/** 分类执行参数：X>0表示获取指定分类，[仅支持单类型的扩容.容错处理]否则执行所有分类 */
	public static Integer PARAM_URL_GROUP = 0;
	/** (单类型)索引.开始：X>0按指定ID[页码]开始，否则从1开始 */
	public static Integer PARAM_DATAID_START = 0;	
	/** (单类型)索引.结束：X>0按指定ID[页码]结束，否则完整执行 */
	public static Integer PARAM_DATAID_END = 0;
	
	/** 编码格式 */
	public static String HTML_CHARSET = "UTF-8";
	/** 访问主页 */
	public static final String URL_HOME = "http://app1.sfda.gov.cn/datasearch/face3/dir.html";	
	/** 二级访问前缀 */
	public static final String URL_PREFIX_LEV2 = "http://app1.sfda.gov.cn/datasearch/face3/base.jsp?" + (PARAM_URL_GROUP>0 ? "tableId="+PARAM_URL_GROUP : "");
	/** 二级访问前缀：分页 */
	public static final String URL_PREFIX_LEV22 = "http://app1.sfda.gov.cn/datasearch/face3/search.jsp?";	
	/** 三级访问前缀 */
	public static final String URL_PREFIX_LEV3 = "http://app1.sfda.gov.cn/datasearch/face3/content.jsp?";
	public static final String URL_PREFIX_LEV3_PRE = "http://app1.sfda.gov.cn/datasearch/face3/";
	
	/** 爬取时数据存放目录 */
	public static String CRAWL_STORAGE = ConstantsCrawler.CRAWL_STORAGE + "sfdanew/";
	/** 异常爬取记录:错误URL */
	public static final String CRAWL_ERROR_PARSE = CRAWL_STORAGE + "error_parse.txt";
	
	/** 生成页面模板 */
	public static final String CRAWLER_DOWNLOAD_CONTENT = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset={0}\"/></head><body>{1}</body></html>";
	
	/**详细链接*/
	public static List<String> detailList = new ArrayList<String>();
}
