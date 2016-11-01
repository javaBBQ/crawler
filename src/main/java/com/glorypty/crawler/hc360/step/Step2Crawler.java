/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.hc360.step.Step1Crawler.java <2015年12月17日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.hc360.step;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.hc360.Hc360Constants;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.crawler.utils.Utils;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 爬取商品详情
 * @author ZhangLujun 
 * @Date 2015年12月17日 下午2:17:44
 * @version 1.0
 */
public class Step2Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			if(href.indexOf(Hc360Constants.PRD_DETAIL_PR_URL)!=-1){
				return true;
			}
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		
		try {
			this.getThread().sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		String href = page.getWebURL().getURL();
		this.logger.info("聪慧网-解析商品详情页链接:"+href);
		
		Map<String,String> result = new HashMap<String, String>();
		String type="0";//商讯类型
		String area_id = "";//地区ID
		
		String coname = ""; //公司名称
		String linkman = ""; //联系人
		String telephone = ""; //电话
		String mobile =""; //手机
		String province = ""; //商家所在地
		String city = ""; 
		String qq = ""; 
		String email = "";
		
		String title = ""; //标题
		String price = ""; //价格
		String num = ""; //供应数量
		String spec = ""; //规格或单位
		
		String main_picture = "";
		String description = ""; //商品信息
		String add_desc = ""; //详细说明
		try {
			
			if(href.indexOf(Hc360Constants.PRD_DETAIL_PR_URL)!=-1){
				Document doc = this.getDocument(page);
				if(doc!=null){
					//商家所在地
					Elements addressElements = doc.select("div.location-w>div.location-m>div.location-qcard>dl.qcard-tit>dd.view>div.view-con>ul.view-con-l>li");
					
					if(addressElements!=null && !addressElements.isEmpty()){
						Element element = addressElements.get(1);
						Elements els =element.getElementsByAttributeValueNot("class", "f-left-tit");
						String companyAddress = els.get(1).html().trim();
						if(companyAddress!=null && !"".equals(companyAddress)){
							String sp = "&nbsp;";
							int index = companyAddress.indexOf(sp);
							province = companyAddress.substring(0,index).trim();
							province = province.replace("省", "");
							province = province.replace("市", "");
							city = companyAddress.substring(index+sp.length()).trim();
							area_id = ConstantsCrawler.commonAreaSingleton.getAreaId(province,city);
							this.logger.info("省："+province+",市："+city+",id:"+area_id);
						}
					}
					
					//商家基本信息
					Elements shopElements = doc.select("div.check-num-box>div.check-num>div.word-box>div.word1>div.p");
					if(shopElements!=null && !shopElements.isEmpty()){
						for (Element element : shopElements) {
							Elements elements = element.getElementsByAttributeValue("class", "itemxunp");
							if(elements!=null && !elements.isEmpty()){
								String qqUrl = elements.attr("href");
								Map<String,Object> map = Utils.getKeyValue(qqUrl);
								if(map!=null){
									qq = map.get("uin").toString();
								}
							}
							String info = element.text().trim();
							if(info.indexOf("公司名称：")!=-1){
								coname = filterValue(info,"公司名称：","：");
								continue;
							}else if(info.indexOf("联系人 ：")!=-1){
								linkman = filterValue(info,"联系人 ：","：");
								continue;
							}else if(info.indexOf("电话：")!=-1){
								telephone = filterValue(info,"电话：","：");
								continue;
							}else if(info.indexOf("联系方式：")!=-1){
								telephone = filterValue(info,"联系方式：","：");
								continue;
							}else if(info.indexOf("手机：")!=-1){
								mobile = filterValue(info,"手机：","：");
								continue;
							}
						}
					}
					
					//商品信息
					Elements titleElements = doc.select("div.detail-right-con>h1.item-top-tit");
					if(titleElements!=null && !titleElements.isEmpty()){
						title = titleElements.get(0).text();
					}
					
					Elements priceElements = doc.select("div.detail-right-con>div>span.item-price-r");
					if(priceElements!=null && !priceElements.isEmpty()){
						price = priceElements.get(0).text();
					}
					
					Elements amuontElements = doc.select("div.detail-right-con>div>span.supply-numb");
					if(amuontElements!=null && !amuontElements.isEmpty()){
						String amuont_unit = amuontElements.get(0).text();
						if(amuont_unit.indexOf("不限")!=-1){
							num = "-1";
							spec = "";
						}else{
							num = Utils.getNumber(amuont_unit).get(0);
							spec = amuont_unit.replace(num, "").trim();
						}
						
					}
					
					//商品详细
					Elements prdInfoElements = doc.select("div#pdetail>div.d-vopy");
					if(prdInfoElements!=null && !prdInfoElements.isEmpty()){
						for (Element element : prdInfoElements) {
							description += element.html();
						}
//						System.out.println("基本参数："+description);
					}
					
					Elements prdInstrElements = doc.select("div#pdetail>div.d-xi-b>div");
					if(prdInstrElements!=null && !prdInstrElements.isEmpty()){
						for (Element element : prdInstrElements) {
							add_desc += element.html();
						}
					}
				}
			}
			
//			this.logger.info("标题："+title+",标准价："+price+",供应数量："+num+",规格："+spec+",基本参数："+description+",详细说明："+add_desc);
			
			//写入DB
			CrawlerService.executeDataBankCmsGoods(type, area_id, title, price, num, spec,
					coname, linkman, telephone, mobile, email, qq, MySqlEscape.escape(main_picture), MySqlEscape.escape(description), MySqlEscape.escape(add_desc));
			
		} catch (Exception e) {
			this.logger.error("异常："+e.getMessage());
		}
	}
	
	private String filterValue(String value,String key,String separator){
		if(value.indexOf(key)!=-1){
			value = value.substring(value.indexOf(separator)+1);
//			System.out.println(key+value);
		}
		else{
			value = "";
		}
		return value;
	}
	
}
