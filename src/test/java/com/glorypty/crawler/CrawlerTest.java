/**
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.CrawlerTest.java <2015-01-06>
 ****************************************************************
 * 版权所有@${year} 国裕网络科技  保留所有权利.
 ***************************************************************/ 
package com.glorypty.crawler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.glorypty.crawler.common.ConstantsCrawler.CrawlerSourceEnum;

/**
 * 
 * @Author hardy(admin@xkshow.cn) 2015年4月6日 上午11:34:43
 */
public class CrawlerTest {
	private static long TIME_EXECUTE = 0l;
	
	@Before
	public void setUp() throws Exception {
		TIME_EXECUTE = System.currentTimeMillis();
	}

	@After
	public void tearDown() throws Exception {
		TIME_EXECUTE -= System.currentTimeMillis();
		System.out.println("执行完毕：耗时(ss)：" + Math.abs(TIME_EXECUTE)/1000);
	}
	
	@Test
	public void test() {
//		CrawlerFactory.door(CrawlerSourceEnum.EHAOYAO);
//		CrawlerFactory.door(CrawlerSourceEnum.SFDANEW);
//		CrawlerFactory.door(CrawlerSourceEnum.SFDAMALL);
//		CrawlerFactory.door(CrawlerSourceEnum.SFDACASE);
//		CrawlerFactory.door(CrawlerSourceEnum.MENET);
//		CrawlerFactory.door(CrawlerSourceEnum.BAIDUYY);
//		CrawlerFactory.door(CrawlerSourceEnum.CPI_HYZX);
//		CrawlerFactory.door(CrawlerSourceEnum.YIYAO100);
//		CrawlerFactory.door(CrawlerSourceEnum.DRUG39);
//		CrawlerFactory.door(CrawlerSourceEnum.QGYYZS);
//		CrawlerFactory.door(CrawlerSourceEnum.YAOFANGWANG);
//		CrawlerTestFactroy.door(CrawlerSourceEnum.SFDANEW);
//		CrawlerTestFactroy.door(CrawlerSourceEnum.MENET);
//		CrawlerTestFactroy.door(CrawlerSourceEnum.BAIDUYY);
//		CrawlerTestFactroy.door(CrawlerSourceEnum.YIYAO100);
//		CrawlerTestFactroy.door(CrawlerSourceEnum.CPI_HYZX);
//		CrawlerFactory.door(CrawlerSourceEnum.YAOFANGWANG);
//		CrawlerTestFactroy.door(CrawlerSourceEnum.YAOFANGWANG);
//		CrawlerTestFactroy.door(CrawlerSourceEnum.DRUG39);   
//		CrawlerFactory.door(CrawlerSourceEnum.YAO1);
//		CrawlerFactory.door(CrawlerSourceEnum.HC360);
		CrawlerFactory.door(CrawlerSourceEnum.HC360QG);
//		runQuartz();
	}
	
	private void runQuartz(){
		try {
			Scheduler sched = new StdSchedulerFactory().getScheduler();
			sched.start();
		} catch (SchedulerException e) {
			System.out.println("Quartz初始化异常");
			e.printStackTrace();
		}
	}

}
