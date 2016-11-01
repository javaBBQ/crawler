/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.hc360.step.Step0Controller.java <2015年12月17日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.hc360.step;

import java.util.List;
import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.hc360.Hc360Constants;
import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫入口控制器
 * @author ZhangLujun 
 * @Date 2015年12月17日 上午10:56:15
 * @version 1.0
 */
public class Step0Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		CrawlController crawlController = this.getController(0);
		List<String> list = Hc360Constants.HOME_LIST_URL;
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
