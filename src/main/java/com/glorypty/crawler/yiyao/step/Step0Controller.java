package com.glorypty.crawler.yiyao.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.yiyao.YiYaoConstants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫入口控制器
 * @author ZhangLujun
 *
 */
public class Step0Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		CrawlController crawlController = this.getController(0);
//		crawlController.addSeed(YiYaoConstants.ZCFG_URL_HOME);
//		crawlController.addSeed(YiYaoConstants.SHGZ_URL_HOME);
//		crawlController.addSeed(YiYaoConstants.YYZBZB_URL_HOME);
//		crawlController.addSeed(YiYaoConstants.YYZS_URL_HOME);
//		crawlController.addSeed(YiYaoConstants.DLXX_URL_HOME);
//		crawlController.addSeed(YiYaoConstants.YYYX_URL_HOME);
//		crawlController.addSeed(YiYaoConstants.ZH_URL_HOME);
		List<String> list = YiYaoConstants.getUrlList();
		if(list!=null&&list.size()>0){
			for (String url : list) {
				crawlController.addSeed(url);
			}
		}
		crawlController.start(Step0Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		
	}

}
