/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.drug39.step.DrugStep0Controller.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.drug39.step;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.drug39.DrugConstants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * @author Andy
 * @date 2015年6月24日
 * @version 1.0
 */
public class DrugStep0Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		
		CrawlController crawlController = this.getController(0);
		Map<String,Integer> map = DrugConstants.getTypeMap();
		Set<String> set = map.keySet();
		for (String homeLink : set) {
			crawlController.addSeed(homeLink);
		}
//		crawlController.addSeed(DrugConstants.RDFT_HYGC_URL_HOME);
//		crawlController.addSeed(DrugConstants.RDFT_XYDT_URL_HOME);
		crawlController.start(DrugStep0Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub

	}

}

