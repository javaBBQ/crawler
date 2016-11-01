/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.sfda.StepCrawler.java <2015年4月2日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/ 
package com.glorypty.crawler.sfdanew;

import com.glorypty.crawler.base.BaseCrawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 
 * @Author yiwen
 * @Date 2015年4月2日 下午11:24:22
 */
public abstract class StepCrawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			String href = url.getURL().toLowerCase(); 
			if (href.startsWith(Constants.URL_PREFIX_LEV2) || href.startsWith(Constants.URL_PREFIX_LEV22)){
				return true;
			} else if(href.startsWith(Constants.URL_PREFIX_LEV3)){
				return true;
			}
		}
		return false;
	}
		
}
