/*
 *Project: glorypty-crawler
 *File: com.glorypty.scheduler.EHaoYao.java
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
 * @author Andy
 * @date 2015年7月9日
 * @version 1.0
 */
public class EHaoYaoJob extends LoggerBase implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.logger.info("Quartz[好药师网]...");
		CrawlerFactory.door(CrawlerSourceEnum.EHAOYAO);
	}

}

