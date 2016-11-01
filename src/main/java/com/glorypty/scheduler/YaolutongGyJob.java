/*
 *Project: glorypty-crawler
 *File: com.glorypty.scheduler.HC360Job.java <2015年12月23日>
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
 * @author yiwen<2016年2月26日>
 */
public class YaolutongGyJob extends LoggerBase implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.logger.info("Quartz[药路通网]...");
		CrawlerFactory.door(CrawlerSourceEnum.YAOLUTONG);
	}

}
