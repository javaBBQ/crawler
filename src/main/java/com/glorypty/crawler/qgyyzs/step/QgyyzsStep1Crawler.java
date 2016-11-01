package com.glorypty.crawler.qgyyzs.step;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.qgyyzs.QgyyzsConstants;
import com.glorypty.crawler.utils.ProcessDate;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class QgyyzsStep1Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		String href = url.getURL();
		if(href.startsWith(QgyyzsConstants.ZHLT_LIST_PRE)) {
			return true;
		}else if(href.contains(QgyyzsConstants.ZBXX_LIST_PRE2)){
			return true;
		}else if(href.contains(QgyyzsConstants.QGXX_LIST_PRE )){
			return true;
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		this.logger.info("环球医院网解析列表页："+href);
		//解析一个列表页删除一个
		QgyyzsConstants.listUrlSize--;
		
		Document doc = getDocument(page);
		Elements elements = null;
		//两种情况会退出列表页，进行解析详情页，1-日期不满足条件，2-所有列表页都已解析完
		boolean isFinish = false;
		
		if(href.startsWith(QgyyzsConstants.ZHLT_LIST_PRE)) {
			elements = doc.select("div[class=zh_news] span[class=title] a");
			for (Element e : elements) {
				QgyyzsConstants.detailUrls.add(e.attr("href"));
				if(!judgeZhltDate(doc)){
					isFinish = true;
					break;
				}
			}
			
		}else if(href.contains(QgyyzsConstants.ZBXX_LIST_PRE2)){
			elements = doc.select("div[class=zb_news] span[class=title] a");
			for (Element e : elements) {
				QgyyzsConstants.detailUrls.add(e.attr("href"));
				if(!judgeZbxxDate(doc)){
					isFinish = true;
					break;
				}
			}
		}else if(href.contains(QgyyzsConstants.QGXX_LIST_PRE )){
			elements = doc.select("a[class=linkzs]");
			for (Element e : elements) {
				QgyyzsConstants.detailUrls.add(e.attr("href"));
				if(!judgeQgxxDate(doc)){
					isFinish = true;
					break;
				}
			}
		}
		
		if(isFinish || QgyyzsConstants.listUrlSize<=0){
			try {
				if(QgyyzsConstants.detailUrls != null && QgyyzsConstants.detailUrls.size() > 0){
					new QgyyzsStep2Controller().door(QgyyzsConstants.detailUrls);
					QgyyzsConstants.detailUrls.clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.getMyController().shutdown();
		}
	}
	
	private boolean judgeQgxxDate(Document doc) {
		boolean flag = true;
		Elements elements = doc.select("td[class=dot][width=90]");
		String date = null;
		for (Element e : elements) {
			date = e.html();
			//（抓取2014.07.01之后内容）"  --- 2015/6/24
			if(!ProcessDate.compareDate("2014/7/1", date, "yyyy/M/d")){
				flag = false;
				break;
			}
		}
		return flag;
	}

	private boolean judgeZbxxDate(Document doc) {
		boolean flag = true;
		Elements elements = doc.select("span[class=riqi]");
		String date = null;
		for (Element e : elements) {
			date = e.html();
			//2015的数据就行
			if(date.compareTo("2015") < 0){
				flag = false;
				break;
			}
		}
		return flag;
	}

	private boolean judgeZhltDate(Document doc) {
		boolean flag = true;
		Elements elements = doc.select("span[class=riqi]");
		String date = null;
		for (Element e : elements) {
			date = e.html().replaceAll("至.*", "");
			if(date.compareTo("2015-01-01") < 0){
				flag = false;
				break;
			}
		}
		return flag;
	}

}
