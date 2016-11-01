/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.Controller.java <2015年4月2日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.base;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * 爬虫控制基类
 * @Author hardy
 * @Date 2015年4月2日 下午3:03:12
 */
public abstract class BaseController {
	protected final Logger logger = Logger.getLogger(this.getClass());
	
	/** 爬虫信息默认存储目录 */
	protected static final String CRAWL_STORAGE = "target/crawl/";
	
	/**
	 * 获取爬虫控制类
	 * @Author hardy<2015年4月13日>
	 * @return
	 * @throws Exception
	 */
	protected CrawlController getController() throws Exception{
		logger.info("crawler.io.tmpdir：" + CRAWL_STORAGE);
        return this.getController(CRAWL_STORAGE);
	}
	
	/**
	 * 获取爬虫控制类
	 * @param storageFolder存储文件夹
	 * @return
	 * @throws Exception
	 * @Author hardy
	 * 2015年4月4日 下午1:29:36
	 */
	protected CrawlController getController(String storageFolder) throws Exception{  
        return this.getController(storageFolder, false, -1);
	}
	
	/**
	 * 获取爬虫控制类
	 * @Author hardy<2015年4月15日>
	 * @param maxDepthOfCrawling爬行深度
	 * @return
	 * @throws Exception
	 */
	protected CrawlController getController(int maxDepthOfCrawling) throws Exception{
		logger.info("crawler.io.tmpdir：" + CRAWL_STORAGE);
        return this.getController(CRAWL_STORAGE, false, maxDepthOfCrawling);
	}
		
	/**
	 * 获取爬虫控制类
	 * @Author hardy<2015年4月13日>
	 * @param storageFolder存储文件夹
	 * @param resumableCrawling不支持重复爬取
	 * @return
	 * @throws Exception
	 */
	protected CrawlController getController(String storageFolder, boolean resumableCrawling) throws Exception{  
        return this.getController(storageFolder, resumableCrawling, -1);
	}  
		
	/**
	 * 获取爬虫控制类
	 * @Author hardy<2015年4月15日>
	 * @param storageFolder存储文件夹
	 * @param resumableCrawling不支持重复爬取
	 * @param maxDepthOfCrawling爬行深度
	 * @return
	 * @throws Exception
	 */
	protected CrawlController getController(String storageFolder, boolean resumableCrawling, int maxDepthOfCrawling) throws Exception{  
        CrawlConfig config = new CrawlConfig();  
        if(StringUtils.isNotEmpty(storageFolder))        	
        	config.setCrawlStorageFolder(storageFolder);
        config.setMaxDepthOfCrawling(maxDepthOfCrawling);
        config.setResumableCrawling(resumableCrawling);

        return this.getController(config); 
	}  
	
	/**
	 * 获取爬虫控制类
	 * @Author hardy<2015年4月13日>
	 * @param config
	 * @return
	 * @throws Exception
	 */
	protected CrawlController getController(CrawlConfig config) throws Exception{         
        /* 
         * Instantiate the controller for this crawl. 
         */  
        PageFetcher pageFetcher = new PageFetcher(config);  
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();  
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);  
        return new CrawlController(config, pageFetcher, robotstxtServer); 
	}  
	
	/**
	 * 爬虫入口
	 * @param lstUrls
	 * @throws Exception
	 * @Author hardy
	 * 2015年4月4日 下午2:00:35
	 */
	public abstract void door(List<String> lstUrls) throws Exception;
	
	/**
	 * 爬取异常入口
	 * @Author hardy 2015年4月6日 上午11:59:38
	 */
	protected abstract void doorError();
	
}
