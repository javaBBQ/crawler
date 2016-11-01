package com.glorypty.crawler.menet.step;

import java.util.ArrayList;
import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.menet.MenetConstants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫入口控制器
 * @author Zhanglujun
 *
 */
public class MenetStep0Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		CrawlController crawlController = this.getController(0);
		ArrayList<String> menetUrlList = MenetConstants.getList();
		for (String str : menetUrlList) {
			if(str.indexOf(MenetConstants.HYZX_YGXW_URL_CONTENT)==-1)
				crawlController.addSeed(str);
		}
//		crawlController.addSeed(MenetConstants.HYZX_YGXW_URL_HOME);
//		crawlController.addSeed(MenetConstants.SYJ_YXJQ_URL_HOME);
//		crawlController.addSeed(MenetConstants.SYJ_JDAL_URL_HOME);
//		crawlController.addSeed(MenetConstants.ZXKY_XYFX_URL_HOME);
//		crawlController.addSeed(MenetConstants.DCBG_URL_HOME);
		crawlController.start(MenetStep0Crawler.class, 1);
	}

	@Override
	protected void doorError() {

	}

}
