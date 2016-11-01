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

import cn.xkshow.commons.logging.LoggerBase;

import com.glorypty.crawler.CrawlerFactory;
import com.glorypty.crawler.common.ConstantsCrawler.CrawlerSourceEnum;

/**
 * 环球医药网
 * @author 刘磊
 * @date 2015年6月26日
 * @version 1.0
 */
public class QgyyzsJob extends LoggerBase implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.logger.info("Quartz[环球医药网]...");
		CrawlerFactory.door(CrawlerSourceEnum.QGYYZS);
	}

}

