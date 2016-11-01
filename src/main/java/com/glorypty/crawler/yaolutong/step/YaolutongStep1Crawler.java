package com.glorypty.crawler.yaolutong.step;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.utils.ProcessDate;
import com.glorypty.crawler.yaolutong.YaolutongConstants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class YaolutongStep1Crawler extends BaseCrawler {
	

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		boolean flag = false;
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			List<String> list = YaolutongConstants.getUrlList();
			for (String home : list) {
				if(href.startsWith(home)){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	
	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if(href.indexOf(YaolutongConstants.ZHAOSHANG_PAGE_URL_HOME_PRE)!=-1
				){
			visitToDetailPage(page);
		}
	}
	
	/**
	 * @author sunny
	 * @param doc
	 * @param href
	 */
	private void visitToDetailPage(Page page) {
		boolean isFinish = false;
		String href = page.getWebURL().getURL();
		this.logger.info("药路通首页解析："+href);
		Document doc = this.getDocument(page);
		if(doc!=null){
				//招商信息
				if(href.indexOf(YaolutongConstants.ZHAOSHANG_PAGE_URL_HOME_PRE) != -1){
					isFinish = visitZSToPage(doc,href);
				}
				//代理信息
				if(href.indexOf(YaolutongConstants.DAILI_PAGE_URL_HOME_PRE) != -1){
					visitDLToPage(doc,href);
				}
				/*if (YaolutongConstants.TotalNum<=YaolutongConstants.urlTotalList.size()) {
					isFinish = true;
				}*/
//				if(YaolutongConstants.urlDetailList.size()>=95){
//					isFinish = true;
//				}
		   }
		
		if(isFinish){
			if(YaolutongConstants.urlDetailList!=null&&YaolutongConstants.urlDetailList.size()>0){
				try {
					new YaolutongStep2Controller().door(YaolutongConstants.urlDetailList);
					YaolutongConstants.urlDetailList.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void visitDLToPage(Document doc, String href) {
		
	}

	private boolean visitZSToPage(Document doc, String href) {
		boolean result = false;
//		.zuo_center ul li.chanpm a
		Elements e = doc.select("div[class=zuo_center]>ul>li[class=chanpm]>a");
		Elements eDate = doc.select("div[class=danpai]>div[class=zuo_center]>ul>li");
		
		for(int i =0;i<e.size();i++){
			String url = e.get(i).attr("href");
			String id = url.substring(url.lastIndexOf("=")+1);
			String date = "";
			if(i==0){
				date = eDate.get(5).text();
			}else{
				date = eDate.get(6*i-1).text();
			}
//			YaolutongConstants.urlTotalList.add(YaolutongConstants.ZHAOSHANG_URL_CONTENT_PRE+id);

			if(judgeDate(date, YaolutongConstants.ZHAOSHANG_URL_HOME)){
				YaolutongConstants.urlDetailList.add(YaolutongConstants.ZHAOSHANG_URL_CONTENT_PRE+id);
				result = false;
			}else{
				result = true;
			}
		}
		return result;
	}

	/** 过滤掉 6个月前的招商数据 
	 *  6个月以内的数据返回true
	 *  否则返回false
	 * */
	private boolean judgeDate(String publishDate, String type) {
		boolean flag = true;
			switch(type){
			//招商信息
			case YaolutongConstants.ZHAOSHANG_URL_HOME:
						if(publishDate.indexOf("更新日期：")!=-1){
							publishDate = publishDate.substring(publishDate.lastIndexOf("：")+1);
						}
						flag = ProcessDate.compareDate(ProcessDate.convertToDate(publishDate,"yyyy-MM-dd"), YaolutongConstants.EFFECTIVE_MONTH);
						if(!flag){
							flag = false;
						}
				}
		return flag;
	}	
	
	public static void main(String[] args) {
		boolean a = ProcessDate.compareDate(ProcessDate.convertToDate("2015-9-27","yyyy-MM-dd"), YaolutongConstants.EFFECTIVE_MONTH);
		System.out.println(a);
	}
}