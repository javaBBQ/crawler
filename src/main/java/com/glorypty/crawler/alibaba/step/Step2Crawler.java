package com.glorypty.crawler.alibaba.step;



import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
 * 列表页
 * @author yiwen
 *
 */
public class Step2Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		return true;
	}

	@Override
	public void visit(Page page) {
 		String href = page.getWebURL().getURL();
		if(href.indexOf("1688.com")!=-1
				){
			//休眠30s
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
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
			Elements elements = doc.select("div[class=top-nav-bar-box]>ul>li>a[class=show-category]");
			Elements elementsContact = doc.select("div[class=m-body]>div[class=m-content]>dl");
			Elements elementsCompany = doc.select("div[class=company-name]");
			Elements elementsMobile = doc.select("div[class=m-body]>div[class=m-content]>dl[class=m-mobilephone]");
			Elements elementsPhone = doc.select("div[class=m-body]>div[class=m-content]>dl");
			Elements elementsZone = doc.select("div[class=m-body]>div[class=m-content]>div>div[class=supplierinfo-common]>div[class=content]>div[class=detail]>div>span[class=disc]");
			if(elements!=null && elements.size()>0){
				/*&&href.indexOf("shop")!=-1  spm=0.0.0.0.Wgy70r*/
				if(href.indexOf(AlibabaConstants.Alibaba_DOMAIN)!=-1){
					String telphone = "";
					String contactName = "";
					String company =  "";
					String zone =  "";
					String phone =  "";
					String profession =  "";
						if(elementsContact!=null && elementsContact.size()>0){
							Element e = elementsContact.get(0);
							telphone = elementsMobile.get(0).getElementsByAttributeValue("class", "mobile-number").text();
							company = elementsCompany.get(0).attr("title");
							phone = elementsPhone.get(1).getElementsByTag("dd").text();
							zone = elementsZone.get(elementsZone.size()-1).text();
							contactName = elementsContact.get(0).getElementsByAttributeValue("class", "membername").text();
							profession = elementsContact.get(0).text();
							
							if(StringUtils.isEmpty(contactName)){
								elementsContact = doc.select("div[class=m-body]>div[class=m-content]>div>a[class=membername]");
								contactName = elementsContact.get(0).getElementsByAttributeValue("class", "membername").text();
								profession = elementsContact.parents().get(0).text();
								phone = elementsPhone.get(0).getElementsByTag("dd").text();
							}
							/*phone = elementsContact.get(0).getElementsByTag("dd").text();*/
						}else{
							return;
						}
						String url = elements.get(0).attr("href")+"?telphone="+telphone+"&contactName="+profession+"&zone="+zone+"&phone="+phone+"&company="+company;
						
						urlList.add(url);
					}
					if(urlList!=null&&urlList.size()>0){
						try {
							this.logger.info(MessageFormat.format("爬虫预爬取数据汇总：共{0}条", urlList.size()));
							new Step3Controller().door(urlList);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				}
				
		}
	}
