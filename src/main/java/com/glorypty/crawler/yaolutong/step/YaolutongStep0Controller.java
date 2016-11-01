package com.glorypty.crawler.yaolutong.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.yaolutong.YaolutongConstants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫入口控制器
 * @author yiwen
 *
 */
public class YaolutongStep0Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		CrawlController crawlController = this.getController(0);
		
		List<String> urls = YaolutongConstants.getUrlList();
		
		if(urls != null && urls.size() > 0){
			for (String url : urls) {
				crawlController.addSeed(url);
			}
		}
		crawlController.start(YaolutongStep0Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub

	}

}
