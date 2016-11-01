package com.glorypty.crawler.yao1.step;

import org.jsoup.nodes.Document;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.crawler.yao1.Yao1Constants;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class Yao1Step2Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		String href = url.getURL();
		if(href.startsWith(Yao1Constants.RDHT_DETAIL_PRE) || 
				href.startsWith(Yao1Constants.JYFT_DETAIL_PRE)){
			return true;
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		
		String href = page.getWebURL().getURL();
		this.logger.info("医药公信网解析详情页:"+href);
		
		if(href.startsWith(Yao1Constants.RDHT_DETAIL_PRE) || 
				href.startsWith(Yao1Constants.JYFT_DETAIL_PRE)){
			visitToContent(page);
		}

	}
	
	private void visitToContent(Page page) {
		Document doc = this.getDocument(page);
		String href = page.getWebURL().getURL();
		if(doc != null){
			//剔除图片
			ConstantsCrawler.clearImg(doc, href);
			
			String title = null;
			String origin = null;
			String txt = null;
			Integer groupId = null;
			
			//热点话题
			if(href.startsWith(Yao1Constants.RDHT_DETAIL_PRE)){
				//"热点话题";
				groupId = 106;
				
			}else{
				//"精英访谈";
				groupId = 107;
			}
			
			title = doc.select("h1[class=f_L del_left del_contlt]").html();
			origin =  doc.select("div[class=del_ly gray2] a").first().html();
			txt = doc.select("span[class=f_R del_left del_detail txt14]").html();
			
//			logger.info(title);
//			logger.info(origin);
//			logger.info(txt);
			
			CrawlerService.executeContent(groupId, title, origin, href, "", MySqlEscape.escape(txt));
		}
	}

}
