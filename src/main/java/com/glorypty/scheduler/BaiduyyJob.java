/*
 *Project: glorypty-crawler
 *File: com.glorypty.scheduler.BaiduyyJob.java <2015年6月15日>
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
 * 百度虫Job
 * @Author hardy 
 * @Date 2015年6月15日 下午2:23:19
 * @version 1.0
 */
public class BaiduyyJob extends LoggerBase implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.logger.info("Quartz[百度虫]...");
		CrawlerFactory.door(CrawlerSourceEnum.BAIDUYY);
	}

}
