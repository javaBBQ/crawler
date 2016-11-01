/*
 *Project: glorypty-crawler
 *File: com.glorypty.SystemUtil.java <2015年4月15日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty;

import java.util.Properties;

import cn.xkshow.io.file.PropertyFile;

/**
 *
 * @Author hardy 
 * @Date 2015年4月15日 上午10:53:16
 * @version 1.0
 */
public class SystemUtil {

	/**
	 * 初始化数据
	 * @Author hardy<2015年4月15日>
	 * @param source 数据源(crawler|excel)
	 * @return
	 */
	public static boolean inits(String source){
		Properties pros = PropertyFile.getInstance().loadingProperties("system.properties");
	    if (null == pros) {
	    	return false;
	    }
	    try {
	    	switch (source) {
				case "crawler":// 爬虫
					com.glorypty.crawler.common.ConstantsCrawler.CRAWL_STORAGE = pros.getProperty("crawler.CRAWL_STORAGE").trim();    		    		
					com.glorypty.crawler.ehaoyao.EHaoYaoConstants.OUT_FILE = pros.getProperty("crawler.EHAOYAO_OUT_FILE").trim();    		    		
					break;
				case "excel":// Excel
					com.glorypty.excel.common.ConstantsExcel.PATH_BASE = pros.getProperty("excel.PATH_BASE").trim();    	
					break;
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	    return true;
	}
	
}
