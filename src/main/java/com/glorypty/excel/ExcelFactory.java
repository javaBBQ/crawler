/*
 *Project: glorypty-crawler
 *File: com.glorypty.excel.ExcelFactory.java	<2015年4月8日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.excel;

import com.glorypty.excel.common.ConstantsExcel.HtmlSourceEnum;

/**
 *
 * @Author hardy
 * @Date 2015年4月8日 上午10:59:50
 */
public class ExcelFactory {
	
	public static void door(HtmlSourceEnum sourceEnum) {
		try {
			switch (sourceEnum) {
				case SFDA:
					new com.glorypty.excel.sfda.Controller().door();
					break;
				case DB_SFDA:
					new com.glorypty.excel.sfda.db.Controller().door();
					break;
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
