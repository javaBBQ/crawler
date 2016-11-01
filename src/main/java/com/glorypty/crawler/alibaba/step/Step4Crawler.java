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
import com.glorypty.crawler.yiyaojie.YiYaojieConstants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author yiwen
 * @date 2015年6月24日
 * @version 1.0
 */
public class Step4Crawler extends BaseCrawler {
	
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
//		return super.shouldVisit(page, url);
		return true;
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		if(href.indexOf("1688.com/page/offerlist.htm")!=-1
				){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
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
		this.logger.info("阿里巴巴商品列表页："+href);
		Document doc = this.getDocument(page);
		if(doc!=null){
			Elements elementsIndex = doc.select("div[class=common-column-150]>ul>li>div[class=image]>a");
			if(elementsIndex!=null&&!elementsIndex.isEmpty()){
				/*解析href "&telphone="+telphone+"&contactName="+contactName; 获取参数*/
				String param = href.substring(href.indexOf("?"),href.length());
				for (Element element : elementsIndex) {
					String url = element.attr("href")+param;
					if(!StringUtils.isEmpty(url))
					AlibabaConstants.urlList.add(url);
					}
				}
				int nowlist = AlibabaConstants.urlList.size();
				if (AlibabaConstants.productTotalNum<=nowlist) {
					isFinish = true;
				}
		   }
		
		if(isFinish){
			if(AlibabaConstants.urlList!=null&&AlibabaConstants.urlList.size()>0){
				try {
					new Step5Controller().door(AlibabaConstants.urlList);
					AlibabaConstants.urlList.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}

