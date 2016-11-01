package com.glorypty.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glorypty.crawler.CrawlerFactory;
import com.glorypty.crawler.common.ConstantsCrawler.CrawlerSourceEnum;

import cn.xkshow.commons.logging.LoggerBase;

public class YaofangwangJob extends LoggerBase implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		this.logger.info("Quartz[药房网]...");
		CrawlerFactory.door(CrawlerSourceEnum.YAOFANGWANG);
	}

}
