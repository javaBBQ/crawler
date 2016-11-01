/*
 *Project: glorypty-crawler
 *File: com.glorypty.scheduler.CpiJob.java <2015年6月15日>
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
 * 中国医药信息网Job
 * @Author hardy 
 * @Date 2015年6月15日 下午2:18:43
 * @version 1.0
 */
public class CpiJob extends LoggerBase implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.logger.info("Quartz[中国医药信息网]...");
		CrawlerFactory.door(CrawlerSourceEnum.CPI_HYZX);
	}

}
