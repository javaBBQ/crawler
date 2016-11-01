/*
 *Project: glorypty-crawler
 *File: com.glorypty.scheduler.SdfaJob.java <2015年6月15日>
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
 * 药监局Job
 * @Author hardy 
 * @Date 2015年6月15日 上午11:57:21
 * @version 1.0
 */
public class SfdaMallJob extends LoggerBase implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.logger.info("Quartz[药监局]...");
		CrawlerFactory.door(CrawlerSourceEnum.SFDAMALL);
	}

}
