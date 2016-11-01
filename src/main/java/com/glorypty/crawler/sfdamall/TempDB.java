/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.common.TempDB.java 
 ****************************************************************
 * 版权所有@2015 国裕秀网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.sfdamall;

import java.util.HashMap;
import java.util.Map;

/**
 * 临时数据库
 * 
 * @Author Andy
 * @Date 2015年7月3日 
 */
public class TempDB {

	/** 数据分类(抓取部分)：药监局分类名=药监局分类ID */
	@SuppressWarnings("serial")
	public static final Map<String, String> DATA_GROUP_NAME = new HashMap<String, String>() {
		{
			/* 保健食品-2 */
			put("国产保健食品", "30");
			put("进口保健食品", "31");
			
			/* 药品-21 */
			put("国产药品", "25");
			put("进口药品", "36");

			/* 化妆品-5 */
			put("国产化妆品", "68");
			put("进口化妆品", "69");
			
			/* 医疗器械-9 */
			put("国产器械", "26");
			put("进口器械", "27");
		}
	};
	
	/** 数据分类(抓取部分)：药监局分类ID=药监局分类名*/
	public static final Map<String, String> DATA_TYPE_NAME = new HashMap<String, String>() {
		{
			/* 保健食品-2 */
			put("30","国产保健食品");
			put("31","进口保健食品");
			/* 药品-21 */
			put("25","国产药品");
			put("36","进口药品");

			/* 化妆品-5 */
			put("68","国产化妆品");
			put("69","进口化妆品");
			/* 医疗器械-9 */
			put("26","国产器械");
			put("27","进口器械");
		}
	};

}
