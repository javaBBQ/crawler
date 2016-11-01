/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.hc360.step.Step1Crawler.java <2015年12月17日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.hc360qg.step;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.unwall.hc360.HcValidata;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 爬取所有列表页，获取商品详情页链接
 * @author ZhangLujun 
 * @Date 2015年12月17日 下午2:17:44
 * @version 1.0
 */
public class Step3Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			return true;
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		
		try {
			this.getThread().sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		String href = page.getWebURL().getURL();
		this.logger.info("聪慧网-解析详情页链接:"+href);
		
		//登录
		try {
			HcValidata.detail(href);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
