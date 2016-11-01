package com.glorypty.crawler.yiyaolm.step;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.yiyaolm.YiYaolmConstants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 列表页抓取
 * @author yiwen
 *
 */
public class Step0Crawler extends BaseCrawler {

	public int page = 1;
	
	public static List<String> list = null;
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			List<String> list = YiYaolmConstants.getUrlList();
			if(list!=null&&list.size()>0){
				for (String str : list) {
					if(href.indexOf(str)!=-1){
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void visit(Page page) {
			List<String> urlList = new ArrayList<String>();
			String href = page.getWebURL().getURL();
			Document doc = this.getDocument(page);
			if(doc!=null){
				Elements elements = doc.select("div[class=page]>ul>li>a");
				if(elements!=null){
//					YiYaolmConstants.totalPage = Integer.parseInt(elements.text());
					YiYaolmConstants.totalPage = 106;
					for (int i = 1; i <= YiYaolmConstants.totalPage; i++) {
						String url = YiYaolmConstants.YYYX_PAGE_URL_HOME.replace("{0}", i+"");
						urlList.add(url);
					}
				}
			}
			
			if(urlList!=null&&urlList.size()>0){
				try {
					this.logger.info(MessageFormat.format("爬虫预爬取数据汇总：共{0}条", urlList.size()));
					new Step1Controller().door(urlList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
}
