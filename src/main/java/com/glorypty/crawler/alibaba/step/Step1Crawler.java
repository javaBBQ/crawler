/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.yiyao.stepStep1Crawler.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.alibaba.step;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.alibaba.AlibabaConstants;
import com.glorypty.crawler.base.BaseCrawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author yiwen
 * @date 2015年6月24日
 * @version 1.0
 */
public class Step1Crawler extends BaseCrawler {
	
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
//		return super.shouldVisit(page, url);
		return true;
	}

	@Override
	public void visit(Page page) {
		//休眠15s
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		String href = page.getWebURL().getURL();
		if(href.indexOf(AlibabaConstants.Alibaba_URL)!=-1
				){
			visitToCommentPage(page);
		}
	}

	/**
	 * @author sunny
	 * @param doc
	 * @param href
	 */
	private void visitToCommentPage(Page page) {
		
		boolean isFinish = false;
		String href = page.getWebURL().getURL();
		this.logger.info("阿里巴巴公司列表页："+href);
		Document doc = this.getDocument(page);
		if(doc!=null){
			/*Elements elements = doc.select("div[class=list-item-left]>div[class=list-item-detail]>div[class=detail-left]>div[class=detail-float-items]>a");
			if(elements!=null&&!elements.isEmpty()){
				for (Element element : elements) {
					String url = element.getElementsByAttributeValue("rel", "nofollow").attr("href");
					AlibabaConstants.urlCompanyList.add(url);
				}
			}*/			
			Elements elementsIndex = doc.select("div[class=list-item-left]>div[class=wrap]>div[class=list-item-title]>a");
			if(elementsIndex!=null&&!elementsIndex.isEmpty()){
				for (Element element : elementsIndex) {
					String url = element.getElementsByAttributeValue("class", "list-item-title-text").attr("href");
					if(!StringUtils.isEmpty(url))
					AlibabaConstants.urlCompanyList.add(url);
					}
				}
				int nowlist = AlibabaConstants.urlCompanyList.size();
				if (AlibabaConstants.totalNum<=nowlist) {
					isFinish = true;
				}
				}
		
		if(isFinish){
			if(AlibabaConstants.urlCompanyList!=null&&AlibabaConstants.urlCompanyList.size()>0){
				try {
					new Step2Controller().door(AlibabaConstants.urlCompanyList);
					AlibabaConstants.urlCompanyList.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}

