/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.yiyao.stepStep1Crawler.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.ehaoyao.step;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.ehaoyao.EHaoYaoConstants;

import edu.uci.ics.crawler4j.crawler.Page;

/**
 * @author liuj
 * @date 2015年6月16日
 * @version 1.0
 */
public class Step1Crawler extends BaseCrawler {

	@Override
	public void visit(Page page) {
		try {
			String href = page.getWebURL().getURL();
			Document doc = this.getDocument(page);
			if (doc != null) {
				Elements elemets = doc.select("div.product>p>a.lazy-loading");
				if (elemets != null) {
					EHaoYaoConstants.list_page.remove(href);
					for (Element element : elemets) {
						String url = element.attr("href");
						url = EHaoYaoConstants.URL_HOME + url;
						String text = element.select("img").attr("alt").trim();
//						this.logger.info("ehaoyao网解析商品列表页： url: "+ url + ", prd_name: " + text);
						EHaoYaoConstants.detail_page.add(url);
//						break;
					}
				}
			}

			if(EHaoYaoConstants.list_page.size()==0){
				if (EHaoYaoConstants.detail_page != null && EHaoYaoConstants.detail_page.size() > 0) {
					new Step2Controller().door(EHaoYaoConstants.detail_page);
//					System.out.println(EHaoYaoConstants.detail_page.size());
				}
			}

		} catch (Exception e) {
			this.logger.error(e);
		}
	}

}
