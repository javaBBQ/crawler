/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.cpi.stepCpiStep1Crawler.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.cpi.step;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.cpi.CpiConstants;
import com.glorypty.crawler.utils.ProcessDate;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Andy
 * @date 2015年6月16日
 * @version 1.0
 */
public class CpiStep1Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
//		return super.shouldVisit(page, url);
		return true;
	}

	@Override
	public void visit(Page page) {
		visitToPage(page);
	}

	/**
	 * 解析列表页
	 * @author Andy
	 * @param page
	 */
	private void visitToPage(Page page) {
		boolean isFinish = false;
		String href = page.getWebURL().getURL();
		this.logger.info("中医药网解析列表页："+href);

		Document doc = this.getDocument(page);
		if(doc!=null){
			Elements elements = doc.select("div[class=middle fl]>div.channel");
			if(elements!=null && !elements.isEmpty()){
				if(href.indexOf(CpiConstants.YYBG_YJBG_PAGE_URL_HOME)!=-1
						||href.indexOf(CpiConstants.YYBG_YWZLDFDT_PAGE_URL_HOME)!=-1
						||href.indexOf(CpiConstants.YYBG_YWZLGZ_PAGE_URL_HOME)!=-1
						||href.indexOf(CpiConstants.YYBG_YWZLTX_PAGE_URL_HOME)!=-1
						||href.indexOf(CpiConstants.YYBG_GMP_PAGE_URL_HOME)!=-1
						||href.indexOf(CpiConstants.YYBG_YYZL_PAGE_URL_HOME)!=-1){
					for (Element element : elements) {
						String detail_url = element.getElementsByAttributeStarting("href").attr("href");
						CpiConstants.detail_list.add(detail_url);
					}
				}else{
					for (Element element : elements) {
						String detail_url = element.getElementsByAttributeStarting("href").attr("href");
						String date = element.getElementsByAttributeValue("class", "sour fr").text();
						if(ProcessDate.compareDate(date,"yyyy-MM-DD", CpiConstants.PAST_MONTH)){
							CpiConstants.detail_list.add(detail_url);
						}else{
							isFinish = true;
							break;
						}
					}
				}
				
				if(CpiConstants.detail_list!=null && CpiConstants.detail_list.size()>0){
					Elements index_elements = doc.select("div[class=middle fl]>div.yellow");
					String str = index_elements.html();
					if(href.indexOf("/index.htm")!=-1 ){
						if(str.indexOf("disabled")!=str.lastIndexOf("disabled")){
							isFinish = true;
						}
					}else{
						if(str.indexOf("disabled")!=-1){
							isFinish = true;
						}
					}
				}
			}
		}
		
		if(isFinish){
			if(CpiConstants.detail_list!=null && CpiConstants.detail_list.size()>0){
				try {
					new CpiStep2Controller().door(CpiConstants.detail_list);
					CpiConstants.detail_list.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.getMyController().shutdown();
		}
	}

}

