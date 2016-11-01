package com.glorypty.crawler.cpi.step;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.cpi.CpiConstants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫控制器： 入口
 * @author Zhanglujun
 *
 */
public class CpiStep0Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		
		CrawlController crawlController = this.getController(0);
		LinkedHashMap<String, String> urlMap= CpiConstants.getMap();
		Set set = urlMap.entrySet();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Entry<String, String> entry = (Entry<String, String>) iterator.next();
			crawlController.addSeed(entry.getKey());
		}
//		crawlController.addSeed(CpiConstants.HYZX_HYZX_URL_HOME);
//		crawlController.addSeed(CpiConstants.YYBG_YJBG_URL_HOME);
//		crawlController.addSeed(CpiConstants.YYBG_YWZLDFDT_URL_HOME);
		crawlController.start(CpiStep0Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub

	}

}
