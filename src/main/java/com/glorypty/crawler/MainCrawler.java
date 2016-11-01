/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.Main.java <2015年4月15日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler;

import org.apache.commons.lang.StringUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.glorypty.SystemUtil;
import com.glorypty.crawler.common.ConstantsCrawler.CrawlerSourceEnum;

/**
 * 数据采集入口程序
 * @Author hardy 
 * @Date 2015年4月15日 上午9:30:24
 * @version 1.0
 */
public class MainCrawler {
	
	public static void main(String[] args) {
		long time_execute = System.currentTimeMillis();		
		if(!SystemUtil.inits("crawler")){			
			System.out.println("初始化数据出错，请检查配置是否输入有误！");
			return;
		}
		// 无参，则执行任务调度
		if(null==args || args.length<1 || StringUtils.isEmpty(args[0])){
			try {
				Scheduler sched = new StdSchedulerFactory().getScheduler();
				sched.start();
			} catch (SchedulerException e) {
				System.out.println("Quartz初始化异常");
				e.printStackTrace();
			}
		}else{
			System.out.println("当前爬虫爬取源是：" + args[0]);
			switch (args[0]) {
				case "SFDAMALL":	
					CrawlerFactory.door(CrawlerSourceEnum.SFDAMALL);
					break;
				case "SFDACASE":	
					CrawlerFactory.door(CrawlerSourceEnum.SFDACASE);
					break;
				case "CPI_HYZX":
					CrawlerFactory.door(CrawlerSourceEnum.CPI_HYZX);
					break;
				case "MENET":
					CrawlerFactory.door(CrawlerSourceEnum.MENET);
					break;	
				case "YIYAO100":
					CrawlerFactory.door(CrawlerSourceEnum.YIYAO100);
					break;
				case "BAIDUYY":
					CrawlerFactory.door(CrawlerSourceEnum.BAIDUYY);
					break;
				case "YAOFANGWANG":
					CrawlerFactory.door(CrawlerSourceEnum.YAOFANGWANG);
					break;
				case "DRUG39":
					CrawlerFactory.door(CrawlerSourceEnum.DRUG39);
					break;
				case "YIYAOLM":
					CrawlerFactory.door(CrawlerSourceEnum.YIYAOLM);
					break;
				case "YIYAOJIE":
					CrawlerFactory.door(CrawlerSourceEnum.YIYAOJIE);
					break;
				case "YAO1":
					CrawlerFactory.door(CrawlerSourceEnum.YAO1);
					break;
				case "QGYYZS":
					CrawlerFactory.door(CrawlerSourceEnum.QGYYZS);
					break;	
				case "EHAOYAO":
					CrawlerFactory.door(CrawlerSourceEnum.EHAOYAO);
					break;
				case "HC360":
					CrawlerFactory.door(CrawlerSourceEnum.HC360);
					break;
				case "HC360QG":
					CrawlerFactory.door(CrawlerSourceEnum.HC360QG);
					break;
				case "ALIBABA":
					CrawlerFactory.door(CrawlerSourceEnum.ALIBABA);
					break;	
				case "YAOLUTONG":
					CrawlerFactory.door(CrawlerSourceEnum.YAOLUTONG);
					break;	
				default:
					break;
			}
			time_execute -= System.currentTimeMillis();
			System.out.println("执行完毕：耗时(ss)：" + Math.abs(time_execute)/1000);
		}
		
	}
	
}
