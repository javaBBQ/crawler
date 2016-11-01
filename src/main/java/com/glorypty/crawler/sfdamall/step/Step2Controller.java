/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.sfda.step.Step2Controller.java <2015年4月13日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.sfdamall.step;

import java.util.List;

import com.glorypty.crawler.base.BaseController;
import com.glorypty.crawler.sfdamall.Constants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 爬虫控制器:内容页码
 * @Author yiwen 
 * @Date 2015年4月13日 下午6:09:48
 * @version 1.0
 */
public class Step2Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		if(null==lstUrls || lstUrls.isEmpty())
			return;
		
		CrawlController crawlController = this.getController(0);
		for(String link : lstUrls){
			crawlController.addSeed(link);
		}
		lstUrls.clear();
		
        //本地测试
//		CrawlController crawlController = this.getController(0);
//		int type = 27;
//		String pre = "http://app1.sfda.gov.cn/datasearch/face3/search.jsp?curstart=";
//		String stax = "&tableId="+type+"&tableName=TABLE"+type;
//		for (int i = 1; i < 26; i++) {
//			crawlController.addSeed(pre+i+stax);
//		}
		
		crawlController.start(Step2Crawler.class, 1);
	}
	
	@Override
	public void doorError(){		
	}

}
