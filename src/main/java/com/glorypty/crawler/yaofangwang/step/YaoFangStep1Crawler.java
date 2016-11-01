package com.glorypty.crawler.yaofangwang.step;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.yaofangwang.YaoFangConstants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 药房网爬虫入口解析
 * @author zhanglujun 
 *
 */
public class YaoFangStep1Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		String href = url.getURL();
		if(href.indexOf(YaoFangConstants.YC_HOME)!=-1){
			return true;
		}else if(href.indexOf(YaoFangConstants.YY_URL)!=-1){
			return true;
		}else if(href.indexOf(YaoFangConstants.YF_HOME)!=-1){
			return true;
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		
		if(href.indexOf(YaoFangConstants.YC_HOME)!=-1){
			visitToYaoqi(page);
		}else if(href.indexOf(YaoFangConstants.YY_URL)!=-1){
			visitToYiyuan(page);
		}else if(href.indexOf(YaoFangConstants.YF_HOME)!=-1){
			visiToYaofang(page);
		}
	
	}
	
	/**
	 * 药房-获取分页
	 * @param page
	 */
	private void visiToYaofang(Page page) {
		List<String> urlList = new ArrayList<String>();
		String href = page.getWebURL().getURL();
//		System.out.println(href);
		Document doc = this.getDocument(page);
		if(doc!=null){
			Elements elements = doc.select("span.current");
			if(elements!=null){
				YaoFangConstants.totalPage = Integer.parseInt(elements.text());
				for (int i = 1; i <= YaoFangConstants.totalPage; i++) {
					String url = YaoFangConstants.YF_PAGE_URL.replace("{0}", i+"");
					urlList.add(url);
				}
			}
		}
		
		if(urlList!=null&&urlList.size()>0){
			try {
				new YaoFangStep2Controller().door(urlList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * 医院-获取分页
	 * @param page
	 */
	private void visitToYiyuan(Page page) {
		List<String> urlList = new ArrayList<String>();
		String href = page.getWebURL().getURL();
		Document doc = this.getDocument(page);
		if(doc!=null){
//			Elements elements = doc.select("div[class=select clearfix]>ul[class=sitems tl]>li>a[class!=cur]");
//			for (Element element : elements) {
//				String url = element.attr("href");
//				if(url.indexOf(".html")!=-1){
//					YaoFangConstants.yy_provinceMap.put(YaoFangConstants.YY_HOME+url, element.text());
//				}
//			}
//		
//		   if(YaoFangConstants.yy_provinceMap!=null){
//			  Set<Entry<String, String>> set = YaoFangConstants.yy_provinceMap.entrySet();
//			  for (Entry<String, String> entry : set) {
//				String url = entry.getKey();
//				String province = entry.getValue();
//				Document document = HttpMethodSimulate.getReqSimulateByStream(url);
//				if(document!=null){
//					YaoFangConstants.yy_page_provinceMap.put(url,province);
//					urlList.add(url);
//					Elements elements = document.select("div[class=select clearfix]>div.opt>div.tj>b");
//					int total = Integer.parseInt(elements.text());
//					if(total>0){
//						int pageNum = total/YaoFangConstants.perPageNum;
//						if(total%YaoFangConstants.perPageNum!=0){
//							pageNum = pageNum + 1;
//						}
//						
//						for (int i = 2; i <= pageNum; i++) {
//							String pageUrl = url.replaceAll("[p]\\d+", "p"+i);
//							YaoFangConstants.yy_page_provinceMap.put(pageUrl,province);
//							urlList.add(pageUrl);
//						}
//						YaoFangConstants.yy_province_PageMap.put(province, pageNum);
//					}else{
//						YaoFangConstants.yy_province_PageMap.put(province, 1);
//					}
//					
//				}
//			}
//		}
			Elements elements = doc.select("div[class=select clearfix]>div.opt>div.tj>b");
			int total = Integer.parseInt(elements.text());
			if(total>0){
				int pageNum = total/YaoFangConstants.perPageNum;
				if(total%YaoFangConstants.perPageNum!=0){
					pageNum = pageNum + 1;
				}
				
				for (int i = 1; i <= pageNum; i++) {
					String pageUrl = href.replaceAll("[p]\\d+", "p"+i);
					urlList.add(pageUrl);
				}
				YaoFangConstants.yy_province_PageMap.put("全国", pageNum);
			}else{
				YaoFangConstants.yy_province_PageMap.put("全国", 1);
			}	
		}
		
		if(urlList!=null&&urlList.size()>0){
			try {
				new YaoFangStep2Controller().door(urlList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 药企-获取各省份
	 * @param page
	 */
	private void visitToYaoqi(Page page) {
		List<String> urlList = new ArrayList<String>();
		String href = page.getWebURL().getURL();
		Document doc = this.getDocument(page);
		if(doc!=null){
			Elements elements = doc.select("div[class=bg_huise w108]>a[class!=cur]");
			if(elements!=null){
				for (Element element : elements) {
					String url = element.attr("href");
					String province = element.text();
					YaoFangConstants.provinceMap.put(url, province);
					urlList.add(url);
				}
			}
		}
		
		if(urlList!=null&&urlList.size()>0){
			try {
				new YaoFangStep2Controller().door(urlList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
