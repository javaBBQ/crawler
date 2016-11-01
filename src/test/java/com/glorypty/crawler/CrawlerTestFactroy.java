package com.glorypty.crawler;

import com.glorypty.crawler.common.ConstantsCrawler.CrawlerSourceEnum;

public class CrawlerTestFactroy {

	public static void door(CrawlerSourceEnum sourceEnum) {
		try {
			switch (sourceEnum) {
				case SFDANEW:
					new com.glorypty.crawler.sfdamall.step.Step2Controller().door(null);
					break;
				case CPI_HYZX:
					new com.glorypty.crawler.cpi.step.CpiStep2Controller().door(null);
					break;
				case MENET:
					new com.glorypty.crawler.menet.step.MenetStep1Controller().door(null);
					break;
				case YIYAO100:
					new com.glorypty.crawler.yiyao.step.Step2Controller().door(null);
					break;
				case BAIDUYY:
					new com.glorypty.crawler.baiduyy.step.BaiduyyStep1Controller().door(null);
					break;
				case YAOFANGWANG:
					new com.glorypty.crawler.yaofangwang.step.YaoFangStep3Controller().door(null);
					break;	
				case DRUG39:
					new com.glorypty.crawler.drug39.step.DrugStep2Controller().door(null);
					break;	
				case YIYAOLM:
					new com.glorypty.crawler.yiyaolm.step.Step0Controller().door(null);
					break;	
				case YIYAOJIE:
					new com.glorypty.crawler.yiyaojie.step.Step0Controller().door(null);
					break;
				case YAO1:
					new com.glorypty.crawler.yao1.step.Yao1Step0Controller().door(null);
					break;
				case QGYYZS:
					new com.glorypty.crawler.qgyyzs.step.QgyyzsStep0Controller().door(null);
					break;		
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
