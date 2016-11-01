/*
 *Project: glorypty-crawler
 *File: com.glorypty.scheduler.MenetJob.java <2015年6月15日>
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
 * 米内网
 * @Author hardy 
 * @Date 2015年6月15日 下午2:20:59
 * @version 1.0
 */
public class MenetJob extends LoggerBase implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.logger.info("Quartz[米内网]...");
		CrawlerFactory.door(CrawlerSourceEnum.MENET);
	}

}
