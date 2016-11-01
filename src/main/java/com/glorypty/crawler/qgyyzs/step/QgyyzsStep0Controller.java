package com.glorypty.crawler.qgyyzs.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.qgyyzs.QgyyzsConstants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫入口控制器
 * @author ZhangLujun
 *
 */
public class QgyyzsStep0Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		CrawlController crawlController = this.getController(0);
		
		List<String> urls = QgyyzsConstants.getUrlList();
		
		if(urls != null && urls.size() > 0){
			for (String url : urls) {
				crawlController.addSeed(url);
			}
		}
		crawlController.start(QgyyzsStep0Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub

	}

}
