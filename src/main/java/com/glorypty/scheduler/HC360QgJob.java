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
 *
 * @author ZhangLujun 
 * @Date 2015年12月23日 上午9:40:49
 * @version 1.0
 */
public class HC360QgJob extends LoggerBase implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.logger.info("Quartz[慧聪网]...");
		CrawlerFactory.door(CrawlerSourceEnum.HC360QG);
	}

}
