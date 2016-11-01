package com.glorypty.crawler.alibaba.step;



import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.alibaba.AlibabaConstants;
import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.utils.CommonAreaSingleton;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 信息采集
 * @author yiwen
 *
 */
public class Step5Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			String parent = url.getParentUrl();
			if(href.indexOf("http://detail.1688.com/offer/")!=-1
					 && href.indexOf("html")!=-1){
				/*if(parent.indexOf(AlibabaConstants.Alibaba_PAGE_URL_HOME_PRE)!=-1){
					return false;
				}*/
				
				return true;
			}
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		try {
			String href = page.getWebURL().getURL();
			Document doc = this.getDocument(page);
			if(doc!=null){
				//剔除图片
				ConstantsCrawler.clearImg(doc, href);
				visitToContent(doc,href,105);
			}
		} catch (Exception e) {
			this.logger.error(e);
		}
		
	}
	
	private void visitToContent(Document doc, String href,int groupid) throws UnsupportedEncodingException {
		
		this.logger.info("阿里巴巴解析详细页:"+href);
//		标题、价格、规格、详细信息、供应商信息（公司名称、联系人、地区、经营模式、联系方式）
		
		String param = href.substring(href.indexOf("?"),href.length());
		String type="0";//商讯类型
		String area_id = "";//地区ID
		String main_picture = "";

		String title = "";//标题
		String price = "";//价格
		String spec = "";//规格或单位
		String description = ""; //商品信息
		String add_desc = ""; //详细说明
		String coname = "";//公司名称
		String linkman = "";//联系人
		String area = "";
		String telephone = "";//电话
		String mobile = "";//手机
		String num = "";//供应数量
		String province = ""; //商家所在地 省
		String city = ""; //商家所在地 市
		String qq = ""; 
		String email = "";
		
		Element titleElement = doc.select("div[class=widget-custom-container]>div>h1[class=d-title]").get(0);
		Element infoElement = doc.select("div[class=d-content]>table>tbody").get(0);
		Elements priceElements1 = doc.select("div[class=d-content]>table>tbody>tr[class=price]>td[class=ladder-1-1]>span");
		Elements priceElements12 = doc.select("div[class=d-content]>table>tbody>tr[class=price]>td[class=ladder-1-1]>div[class=price-original-sku]>span");
		Elements priceElements2 = doc.select("div[class=d-content]>table>tbody>tr[class=price]>td[class=ladder-2-1]>span");
		Elements priceElements3 = doc.select("div[class=d-content]>table>tbody>tr[class=price]>td[class=ladder-3-1]>span");
		Elements priceElements4 = doc.select("div[class=d-content]>table>tbody>tr[class=price]>td[class=ladder-4-1]>span");
		Elements specElements1 = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-1-1]>span[class=unit]");
		Elements specElements2 = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-2-1]>span[class=unit]");
		Elements specElements3 = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-3-1]>span[class=unit]");
		Elements specElements4 = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-4-1]>span[class=unit]");
		Elements num1Elements = doc.select("div[class=obj-sku]>div[class=obj-content]>table[class=table-sku]>tbody>tr[class=last-row]>td[class=count]>span>em[class=value]");
 		Elements num2Elements = doc.select("div[class=d-content]>span[class=total]");

 		Element descriptionElement = doc.select("div[class=obj-content]").get(0);
		
		if (null!=titleElement) {
			title = titleElement.text();
		}
		
		if (null!=infoElement) {
			if(priceElements1.size()>1){
				spec = specElements1.get(0).text();
				price = priceElements1.get(1).text();
				price = filterValue(price,spec);
				Elements amountElements = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-1-1]>span[class=value]");
				price = "起批价："+amountElements.get(0).text()+spec+":"+price+"元"+"/"+spec;
			}else if(priceElements12.size()>1){
				spec = specElements1.get(0).text();
				price = priceElements1.get(1).text();
				price = filterValue(price,spec);
				
				Elements amountElements = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-1-1]>span[class=value]");
				price = "起批价："+amountElements.get(0).text()+spec+":"+price+"元"+"/"+spec;
			}else if(priceElements2.size()>1){
				spec = specElements2.get(0).text();
				
				Elements amountElements2 = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-2-1]>span[class=value]");
				String firstPrice = amountElements2.get(0).text()+spec+":"+priceElements2.get(1).text()+"元"+"/"+spec;
				Elements amountElements22 = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-2-2]>span[class=value]");
				Elements priceElements22 = doc.select("div[class=d-content]>table>tbody>tr[class=price]>td[class=ladder-2-2]>span");

				String secondPrice = amountElements22.get(0).text()+spec+":"+priceElements22.get(1).text()+"元"+"/"+spec;
				price = "区间价："+firstPrice+";"+secondPrice;
				
				
			}else if(priceElements3.size()>1){
				spec = specElements3.get(0).text();
				price = priceElements3.get(1).text();

				Elements amountElements3 = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-3-1]>span[class=value]");
				String firstPrice = amountElements3.get(0).text()+spec+":"+priceElements3.get(1).text()+"元"+"/"+spec;
				Elements amountElements32 = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-3-2]>span[class=value]");
				Elements priceElements32 = doc.select("div[class=d-content]>table>tbody>tr[class=price]>td[class=ladder-3-2]>span");
				String secondPrice = amountElements32.get(0).text()+spec+":"+priceElements32.get(1).text()+"元"+"/"+spec;

				Elements amountElements33 = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-3-3]>span[class=value]");
				Elements priceElements33 = doc.select("div[class=d-content]>table>tbody>tr[class=price]>td[class=ladder-3-3]>span");
				String thirdPrice = amountElements33.get(0).text()+spec+":"+priceElements33.get(1).text()+"元"+"/"+spec;
				price = "区间价："+firstPrice+";"+secondPrice+";"+thirdPrice;
			
			}else if(priceElements4.size()>1){
				spec = specElements4.get(0).text();
				price = priceElements4.get(1).text();

				Elements amountElements4 = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-4-1]>span[class=value]");
				String firstPrice = amountElements4.get(0).text()+spec+":"+priceElements4.get(1).text()+"元"+"/"+spec;
				Elements amountElements42 = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-4-2]>span[class=value]");
				Elements priceElements42 = doc.select("div[class=d-content]>table>tbody>tr[class=price]>td[class=ladder-4-2]>span");
				String secondPrice = amountElements42.get(0).text()+spec+":"+priceElements42.get(1).text()+"元"+"/"+spec;
				Elements amountElements43 = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-4-3]>span[class=value]");
				Elements priceElements43 = doc.select("div[class=d-content]>table>tbody>tr[class=price]>td[class=ladder-4-3]>span");
				String thirdPrice = amountElements43.get(0).text()+spec+":"+priceElements43.get(1).text()+"元"+"/"+spec;
				Elements amountElements44 = doc.select("div[class=d-content]>table>tbody>tr[class=amount]>td[class=ladder-4-4]>span[class=value]");
				Elements priceElements44 = doc.select("div[class=d-content]>table>tbody>tr[class=price]>td[class=ladder-4-4]>span");
				String fourthPrice = amountElements44.get(0).text()+spec+":"+priceElements44.get(1).text()+"元"+"/"+spec;
				price = "区间价："+firstPrice+";"+secondPrice+";"+thirdPrice+";"+fourthPrice;
			}
		}
		
		if (null!=descriptionElement) {
			description = descriptionElement.html();
		}
		
		if (null!=num1Elements && num1Elements.size()>0) {
			num = num1Elements.get(0).html();
		}else if(null!=num2Elements && num2Elements.size()>0) {
			num= num2Elements.get(0).html();
		}
		num= filterValue(num, spec);
		linkman = URLDecoder.decode(param.substring(param.indexOf("contactName="), param.indexOf("&pageNum")).replace("contactName=", ""),"UTF-8");
		if(linkman.indexOf("联 系  人： ")!=-1){
			linkman = linkman.replace("联 系  人： ","");
		}
		mobile = URLDecoder.decode(param.substring(param.indexOf("telphone="), param.indexOf("&zone=")).replace("telphone=", ""),"UTF-8");
		telephone = URLDecoder.decode(param.substring(param.indexOf("&phone="), param.indexOf("&telphone=")).replace("&phone=", ""),"UTF-8");
		
		
		coname = URLDecoder.decode(param.substring(param.indexOf("company="), param.indexOf("&contactName=")).replace("company=", ""),"UTF-8");
		AlibabaConstants.count++;
		
		area = URLDecoder.decode(param.substring(param.indexOf("&zone="), param.length()).replace("&zone=", ""),"UTF-8");
		
		if(area.indexOf(" ")!=-1){
			province=area.split(" ")[0];
			city=area.split(" ")[1];
		}else{
			province=area;
		}
		area_id = ConstantsCrawler.commonAreaSingleton.getAreaId(province,city);

		
		System.out.println("id="+AlibabaConstants.count+", 单价:"+price+",规格："+spec+",商品名："+title+",数量："+num+",联系人："+linkman+",手机："+mobile+",电话："+telephone+",地区："+area+",公司名："+coname+",地区编号:"+area_id+",描述"+description);
		//写入DB
		CrawlerService.executeDataBankCmsGoods(type, area_id, title, price, num, spec,
				coname, linkman, telephone, mobile, email, qq, MySqlEscape.escape(main_picture), MySqlEscape.escape(description), MySqlEscape.escape(add_desc));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	private String filterValue(String value,String spec) {
		if(!StringUtils.isEmpty(value)&&value.indexOf("（")!=-1&&value.indexOf("）")!=-1&&value.indexOf(spec)!=-1){
			return value.substring(1,value.indexOf(spec));
		}
		return value;
	}

	public static void main(String[] args) {
		String s = "广东 广州";
		String[] sa = s.split(" ");	
		System.out.println(sa[0]+","+sa[1]);
	}
}


