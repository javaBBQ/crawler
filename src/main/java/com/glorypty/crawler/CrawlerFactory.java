/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.CrawlerFactory.java  <2015年4月5日 >
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/ 
package com.glorypty.crawler;

import com.glorypty.crawler.common.ConstantsCrawler.CrawlerSourceEnum;

/**
 * 数据采集工厂
 * @Author hardy(admin@xkshow.cn)
 * @Date 2015年4月5日 上午11:46:10
 */
public class CrawlerFactory {

	public static void door(CrawlerSourceEnum sourceEnum) {
		try {
			switch (sourceEnum) {
				case SFDAMALL:
					new com.glorypty.crawler.sfdamall.step.Step1Controller().door(null);
					break;
				case SFDACASE:
					/*new com.glorypty.crawler.sdfacase.step.Step1Controller().door(null);*/
					break;
				case CPI_HYZX:
					new com.glorypty.crawler.cpi.step.CpiStep0Controller().door(null);
					break;
				case MENET:
					new com.glorypty.crawler.menet.step.MenetStep0Controller().door(null);
					break;
				case YIYAO100:
					new com.glorypty.crawler.yiyao.step.Step0Controller().door(null);
					break;
				case BAIDUYY:
					new com.glorypty.crawler.baiduyy.step.BaiduyyStep0Controller().door(null);
					break;	
				case YAOFANGWANG:
					new com.glorypty.crawler.yaofangwang.step.YaoFangStep1Controller().door(null);
					break;
				case YAO1:
					new com.glorypty.crawler.yao1.step.Yao1Step0Controller().door(null);
					break;
				case QGYYZS:
					new com.glorypty.crawler.qgyyzs.step.QgyyzsStep0Controller().door(null);
					break;
				case YIYAOLM:
					new com.glorypty.crawler.yiyaolm.step.Step0Controller().door(null);
					break;
				case YIYAOJIE:
					new com.glorypty.crawler.yiyaojie.step.Step0Controller().door(null);
					break;	
				case DRUG39:
					new com.glorypty.crawler.drug39.step.DrugStep0Controller().door(null);
					break;	
				case EHAOYAO:
					new com.glorypty.crawler.ehaoyao.step.Step0Controller().door(null);
					break;
				case HC360:
					new com.glorypty.crawler.hc360.step.Step0Controller().door(null);
					break;
				case HC360QG:
					new com.glorypty.crawler.hc360qg.step.Step0Controller().door(null);
					break;
				case ALIBABA:
					new com.glorypty.crawler.alibaba.step.Step2Controller().door(null);
					break;	
				case YAOLUTONG:
					new com.glorypty.crawler.yaolutong.step.YaolutongStep0Controller().door(null);
					break;	
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
