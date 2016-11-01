/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.sfda.step.Step2Crawler.java <2015年4月13日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.sfdanew.step;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.sfdanew.Constants;
import com.glorypty.crawler.sfdanew.StepCrawler;

import edu.uci.ics.crawler4j.crawler.Page;

/**
 * 采集内容URL by 内容页码
 * 
 * @Author yiwen
 * @Date 2015年4月13日 下午6:10:20
 * @version 1.0
 */
public class Step2Crawler extends StepCrawler {

	@Override
	public void visit(Page page) {
		boolean isFinished = false;
		String url = page.getWebURL().getURL();
		int cupageNum = 1;
		try {
			Document doc = this.getDocument(page);
			if (null != doc && url.startsWith(Constants.URL_PREFIX_LEV22)) {
				this.logger.info("药监局解析列表页："+ url);
				Elements contents = doc.select("table>tbody>tr>td>p[align=left]>a");
				if (null != contents && !contents.isEmpty()) {
					for (Element element : contents) {
						String link = element.attr("href");
						link = link.substring(link.indexOf(",")+2,link.lastIndexOf(",")-1);
						String detailUrl = Constants.URL_PREFIX_LEV3_PRE + link;
						Constants.detailList.add(detailUrl);
					}
				}
				
				Elements page_elements = doc.select("table>tbody>tr>td[align=center]");
				if(page_elements!=null && !page_elements.isEmpty()){
					String  page_numurl = page_elements.text();
					cupageNum =Integer.parseInt(page_numurl.substring(page_numurl.indexOf("第")+1, page_numurl.indexOf("页")).trim());
					int totalPage = Integer.parseInt(page_numurl.substring(page_numurl.indexOf("共")+1, page_numurl.lastIndexOf("页")).trim());
					if(cupageNum==totalPage){
						isFinished = true;
					}
				}
			}
			
			if(isFinished){
				if(Constants.detailList.size()>0 && !Constants.detailList.isEmpty()){
					new Step3Controller().door(Constants.detailList);
					Constants.detailList.clear();
				}
			}
		} catch (Exception e) {
			this.logger.error("执行：内容页码(" + url + ")： " + e);
		}
	}
}
