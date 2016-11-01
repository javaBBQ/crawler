package com.glorypty.crawler.alibaba.step;



import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.alibaba.AlibabaConstants;
import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.crawler.yiyaojie.YiYaojieConstants;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 信息采集
 * 公司详情页
 * @author yiwen
 *
 */
public class Step3Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		return true;
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		if(href.indexOf("1688.com")!=-1&&href.indexOf("http://www.1688.com/company")==-1
				){
			visitToCompanyPage(page);
		}
	}

	/**
	 * @author sunny
	 * @param doc
	 * @param href
	 */
	private void visitToCompanyPage(Page page) {
		
		
		List<String> urlList = new ArrayList<String>();
		String href = page.getWebURL().getURL();
		Document doc = this.getDocument(page);
		if(doc!=null){
			Elements elements = doc.select("div[class=wp-paging-unit]>ul>li>em[class=page-count]");
			Elements elementsTotalNum = doc.select("div[class=wp-paging-unit]>ul>li>em[class=offer-count]");
			if(elements!=null && elements.size()>0){
					String pages= elements.get(0).text();
					AlibabaConstants.productTotalPage = Integer.parseInt(pages);
					AlibabaConstants.productTotalNum = Integer.parseInt(elementsTotalNum.get(0).text());
					
					for (int i = 1; i <= AlibabaConstants.productTotalPage; i++) {
						String url = href+"&pageNum="+i;
						System.out.println("productPage:"+url);
						urlList.add(url);
					}
			}else{
				//只有一页
				urlList.add(href);
			}
					if(urlList!=null&&urlList.size()>0){
						try {
							this.logger.info(MessageFormat.format("爬虫预爬取数据汇总：共{0}条", urlList.size()));
							new Step4Controller().door(urlList);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				
		}}
}
