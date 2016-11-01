/*
 *Project: glorypty-crawler
 *File: com.glorypty.excel.Main.java <2015年4月15日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.excel;

import com.glorypty.SystemUtil;
import com.glorypty.excel.common.ConstantsExcel.HtmlSourceEnum;

/**
 *
 * @Author hardy 
 * @Date 2015年4月15日 上午11:37:13
 * @version 1.0
 */
public class MainExcel {

	public static void main(String[] args) {
		long time_execute = System.currentTimeMillis();
		System.out.println("当前爬虫爬取源是：" + args[0]);
		if(!SystemUtil.inits("excel")){
			System.out.println("初始化数据出错，请检查配置是否输入有误！");
			return;
		}
		switch (args[0]) {
			case "SFDA":	
				ExcelFactory.door(HtmlSourceEnum.SFDA);
				break;	
			case "DB_SFDA":	
				ExcelFactory.door(HtmlSourceEnum.DB_SFDA);
				break;	
			default:
				break;
		}
		time_execute -= System.currentTimeMillis();
		System.out.println("执行完毕：耗时(ss)：" + Math.abs(time_execute)/1000);
	}

}
