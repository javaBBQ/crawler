package com.glorypty.crawler.menet.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.common.ConstantsCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 信息抓取控制器
 * @author Zhanglujun
 *
 */
public class MenetStep1Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		
		CrawlController crawlController = this.getController(ConstantsCrawler.CRAWL_STORAGE + "menet/", true, 0);
		//本地
//		CrawlController crawlController = this.getController(0);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
		
//		crawlController.addSeed("http://www.menet.com.cn/Articles/Marketing/201403/201403261549344934_111608.shtml");
//		crawlController.addSeed("http://www.menet.com.cn/Articles/information/201211/201211150842254225_83099.html");
//		crawlController.addSeed("http://www.menet.com.cn/Articles/information/201211/201211150841414141_83098.html");

		crawlController.start(MenetStep1Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub
	}

}
