/*
 *Project: glorypty-crawler
 *File: com.glorypty.scheduler.DrugJob.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glorypty.crawler.CrawlerFactory;
import com.glorypty.crawler.common.ConstantsCrawler.CrawlerSourceEnum;

import cn.xkshow.commons.logging.LoggerBase;

/**
 * 中国医药联盟网
 * @author yiwen
 * @date 2015年6月25日
 * @version 1.0
 */
public class YiyaolmJob extends LoggerBase implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.logger.info("Quartz[中国医药联盟]...");
		CrawlerFactory.door(CrawlerSourceEnum.YIYAOLM);
	}

}

