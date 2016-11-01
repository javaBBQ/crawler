package com.glorypty.crawler.yaolutong.step;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.common.ConstantsCrawler.AllianceTypeEnum;
import com.glorypty.crawler.utils.HttpMethodSimulate;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.crawler.utils.ProcessDate;
import com.glorypty.crawler.yaolutong.YaolutongConstants;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class YaolutongStep0Crawler extends BaseCrawler {
	
	/**论坛会展 分页起始页*/
	public int pageNum = 2;

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
		List<String> urlList = new ArrayList<String>();
		String href = page.getWebURL().getURL();
		this.logger.info("药路通首页解析："+href);
		
		Document doc = this.getDocument(page);
		if(doc!=null){
			do{
				//解析分页并访问详细页
				//招商信息
				if(href.indexOf(YaolutongConstants.ZHAOSHANG_URL_HOME) != -1){
					visitZSToPage(doc,href,urlList);
					break;
				}
				//代理信息
				if(href.indexOf(YaolutongConstants.DAILI_URL_HOME) != -1){
					visitDLToPage(doc,href,urlList);
					break;
				}
			}while(false);
			
			try {
				new YaolutongStep1Controller().door(urlList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void visitDLToPage(Document doc, String href, List<String> urlList) {
		
		String jicount = doc.select("ul[class=fenye]>div[id=Pager1]>a:last").get(0).attr("href");
		jicount = jicount.substring(jicount.lastIndexOf("?"));
		String pageStr = doc.getElementById("topage").html();
		pageStr = pageStr.substring(1, pageStr.indexOf("页"));
		
		//最大页数
//		int totalPage = Integer.parseInt(pageStr);
		int totalPage = 5;
		for(int i = 1 ; i <= totalPage && i <= YaolutongConstants.MAX_PAGE_SIZE; i++){
			urlList.add(YaolutongConstants.DAILI_PAGE_URL_HOME_PRE + "page=" + i + jicount);
			/*if(!judgeDate(urlList.get(urlList.size() - 1), YaolutongConstants.DAILI_URL_HOME)){
				break;
			}*/
		}
	}

	private void visitZSToPage(Document doc, String href, List<String> urlList) {
		Elements e = doc.select("ul[class=fenye]>div[id=Pager1]>a");
		String jicount = e.get(e.size()-1).attr("href");
//		Document doc2 = HttpMethodSimulate.getReqSimulate(href);
		String html =  doc.html();
		String total = html.substring(html.indexOf("<span id=\"lbRecordCount1\" class=\"lbRecordCount\">共")+49,html.indexOf("条　</span>"));
		/*<span><!--<span id="lbRecordCount1" class="lbRecordCount">共37738条　37529 209 37605</span>--></span> */
		YaolutongConstants.TotalNum = Integer.valueOf(total);	
		
		jicount = jicount.substring(jicount.lastIndexOf("=")+1);
//		String pageStr = doc.select("form b").get(0).html();
		
		//最大页数
		int totalPage = Integer.parseInt(jicount);
//		int totalPage = 5;
		for(int i = 1 ; i <= totalPage;  i++){
			urlList.add(YaolutongConstants.ZHAOSHANG_PAGE_URL_HOME_PRE + i);
			/*if(!judgeDate(YaolutongConstants.ZHAOSHANG_PAGE_URL_HOME_PRE + i, YaolutongConstants.ZHAOSHANG_URL_HOME)){
				break;
			}*/
		}
	}

}