package com.glorypty.crawler.ehaoyao.step;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.ehaoyao.EHaoYaoConstants;

import edu.uci.ics.crawler4j.crawler.Page;

/**
 * 列表页抓取
 * @author liuj
 *
 */
public class Step0Crawler extends BaseCrawler {

	public int page = 1;
	
	public static List<String> list = null;

	@Override
	public void visit(Page page) {
		try {
			String href = EHaoYaoConstants.URL_HOME;
			this.logger.info("开始ehaoyao网主页解析:"+href);
			Document doc = this.getDocument(page);
			if(doc != null){
				Elements elements = doc.select("div.sub-category>a");
				if(elements != null){
					for(Element element : elements){
						String url = element.attr("href");
						String text = element.text().trim();
						
						int preBreakIndex = text.indexOf("(");
						int sufBreakIndex = text.indexOf(")");
						if(preBreakIndex > 0 && sufBreakIndex > 0 && sufBreakIndex > preBreakIndex){
							//分类下没商品则跳过
							if(text.contains("(0)")){
								continue;
							}
							//获取个数
							Integer categorySize = Integer.valueOf(text.substring(preBreakIndex + 1, sufBreakIndex));
							//算一下分页数
							Integer pageSize = categorySize%EHaoYaoConstants.PAGE_SIZE == 0 ? categorySize/EHaoYaoConstants.PAGE_SIZE : categorySize/EHaoYaoConstants.PAGE_SIZE + 1;
							//构造分页请求
							if(url.contains("-") && url.split("-").length == 2){
								url = href + url;
							}else{
								new Exception("网站分页规则变化,需要重新编码");
							}
							
							for(int pageNo = 1; pageNo <= pageSize; pageNo ++ ){
								String urltemp = url.replace("-", "-n"+pageNo+"-");
								EHaoYaoConstants.list_page.add(urltemp);
//								this.logger.debug("ehaoyao网主页解析得到的url :"+ urltemp + ";text :" + text);
							}
//							break;
						}
					}
					
				}
			}
			if(EHaoYaoConstants.list_page!=null && EHaoYaoConstants.list_page.size()>0){
				new Step1Controller().door(EHaoYaoConstants.list_page);
			}
			
		} catch (Exception e) {
			this.logger.error(e);
		}
	}
}
