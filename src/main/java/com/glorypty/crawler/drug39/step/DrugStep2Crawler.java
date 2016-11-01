/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.drug39.step.DrugStep2Crawler.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.drug39.step;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.drug39.DrugConstants;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Andy
 * @date 2015年6月25日
 * @version 1.0
 */
public class DrugStep2Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		String href = url.getURL();
		if(href.indexOf(DrugConstants.RDFT_HYGC_URL_HOME)!=-1){
			return true;
		}else if(href.indexOf(DrugConstants.RDFT_XYDT_URL_HOME)!=-1){
			return true;
		}else if(href.indexOf(DrugConstants.RDFT_YQDT_URL_HOME)!=-1){
			return true;
		}else if(href.indexOf(DrugConstants.RDFT_YYXW_URL_HOME)!=-1){
			return true;
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		visitToDetail(page);
	}

	
	/**
	 * @author Andy
	 * @param page
	 */
	private void visitToDetail(Page page) {
		String href = page.getWebURL().getURL();
		this.logger.info("39健康网解析详细页："+href);
		
		String title = "";
		String digest = "";
		String origin = "";
		String originurl = "";
		String content = "";
		Integer groupId = null;
		
		Document doc = this.getDocument(page);
		if(doc!=null){
			//剔除图片
			ConstantsCrawler.clearImg(doc, href);
			
			//栏目
			Elements channel_elements = doc.select("div.art_topbar>span.art_location>a");
			if(channel_elements!=null && !channel_elements.isEmpty()){
				String link = channel_elements.get(channel_elements.size()-1).attr("href");
				Map<String,Integer> map = DrugConstants.getTypeMap();
				Set<String> set = map.keySet();
				for (String homeLink : set) {
					if(link.indexOf(homeLink)!=-1){
						groupId = map.get(homeLink);
						break;
					}
				}
			}
			
			//标题
			Elements title_elements = doc.select("div.art_box>h1");
			if(title_elements!=null && !title_elements.isEmpty()){
				title = title_elements.get(0).text();
			}
			
			//来源
			origin = "39健康网";
			originurl = href;
			
			//摘要
			Elements digest_elements = doc.select("div.art_box>p.summary");
			if(digest_elements!=null && !digest_elements.isEmpty()){
				digest = digest_elements.get(0).text();
			}
			
			//内容
			Elements content_elements = doc.select("div.art_box>div.art_con>p");
			if(content_elements!=null && !content_elements.isEmpty()){
				content = content_elements.outerHtml();
			}
			
//			System.out.println(type+"\n"
//					+title+"\n"
//					+digest+"\n"
//					+content+"\n");
			//数据库操作
			CrawlerService.executeContent(groupId, MySqlEscape.escape(title), origin, originurl, MySqlEscape.escape(digest), MySqlEscape.escape(content));
		}
	}

}

