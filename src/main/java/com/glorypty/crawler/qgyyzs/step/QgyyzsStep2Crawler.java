package com.glorypty.crawler.qgyyzs.step;

import org.jsoup.nodes.Document;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.qgyyzs.QgyyzsConstants;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class QgyyzsStep2Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		String href = url.getURL();
		if(href.startsWith(QgyyzsConstants.ZHLT_DETAIL_PRE) 
				|| href.startsWith(QgyyzsConstants.ZBXX_DETAIL_PRE)
				|| href.startsWith(QgyyzsConstants.QGXX_DETAIL_PRE)){
			return true;
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		
		String href = page.getWebURL().getURL();
		this.logger.info("环球医院网解析详情页:"+href);
		
		if(href.startsWith(QgyyzsConstants.ZHLT_DETAIL_PRE) 
				|| href.startsWith(QgyyzsConstants.ZBXX_DETAIL_PRE)
				|| href.startsWith(QgyyzsConstants.QGXX_DETAIL_PRE)){
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
			String origin = "环球医药网 ";
			String txt = null;
			Integer groupId = null;
			
			//热点话题
			if(href.startsWith(QgyyzsConstants.ZHLT_DETAIL_PRE)){
				//展会论坛;
				groupId = 105;
				title = doc.select("h1").html();
				txt = doc.select("div[class=zh_view] dl").html();
			}else if(href.startsWith(QgyyzsConstants.ZBXX_DETAIL_PRE)){
				//招标信息
				groupId = 108;
				title = doc.select("h1").html().replaceAll("<br.*", "");
				txt = doc.select("div[class=zb_view] dl").html();
			}else if(href.startsWith(QgyyzsConstants.QGXX_DETAIL_PRE)){
				//求购信息(采购信息)
				groupId = 109;
				title = doc.select("div[class=current_d]").html();
				title = title.substring(title.lastIndexOf("/") + 1).trim();
				txt = doc.select("div table").get(0).html();
			}
			
			CrawlerService.executeContent(groupId, title, origin, href, "", MySqlEscape.escape(txt));
		}
	}

}
