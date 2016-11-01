package com.glorypty.crawler.yaofangwang.step;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.utils.HttpMethodSimulate;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.crawler.yaofangwang.YaoFangConstants;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;


/**
 * 药房网爬虫详情页解析
 * @author zhanglujun
 *
 */
public class YaoFangStep3Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		String href = url.getURL();
		if(href.indexOf(YaoFangConstants.YC_HOME)!=-1
				&&href.endsWith("/")){
			Pattern pattern = Pattern.compile("[/](\\d+)[/]");
			Matcher matcher = pattern.matcher(href);
			while(matcher.find()){
				return true;
			}
		}else if(href.indexOf(YaoFangConstants.YY_HOME)!=-1){
			Pattern pattern = Pattern.compile("[/](\\d+)[\\.]");
			Matcher matcher = pattern.matcher(href);
			while(matcher.find()){
				return true;
			}
		}else if(href.indexOf(YaoFangConstants.YF_HOME)!=-1){
			Pattern pattern = Pattern.compile("[/](\\d+)[/]");
			Matcher matcher = pattern.matcher(href);
			while (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		//医院
		if(href.indexOf(YaoFangConstants.YY_HOME)!=-1&&href.indexOf("_")==-1){
			Pattern pattern = Pattern.compile("[/](\\d+)[\\.]");
			Matcher matcher = pattern.matcher(href);
			while(matcher.find()){
				visitToYiyuan(page);
			}
		}
		//药房
		else if(href.indexOf(YaoFangConstants.YF_HOME)!=-1){
			Pattern pattern = Pattern.compile("[/](\\d+)[/]");
			Matcher matcher = pattern.matcher(href);
			while (matcher.find()) {
				visiToYaofang(page);
			}
		}
		//药企
		else if(href.indexOf(YaoFangConstants.YC_HOME)!=-1
				&&href.endsWith("/")){
			Pattern pattern = Pattern.compile("[/](\\d+)[/]");
			Matcher matcher = pattern.matcher(href);
			while(matcher.find()){
				visitToYaoqi(page);
			}
		}
	}

	/**
	 * 药房-解析详情页
	 * @param page
	 */
	private void visiToYaofang(Page page) {
		String href = page.getWebURL().getURL();
		this.logger.info("解析药房详情页："+href);
		Document doc = this.getDocument(page);
		if(doc!=null){
			String logo = "";
			String province = "";
			String certificate = "";
			String license = "";
			String gsp = "";
			String scope = "";
			String shopName = "";
			String shopAddrees = "";
			String business_hours = "";
			String type = "未分类";
			
			//http://yaodian.yaofangwang.com/339055/
			Elements info_elements =  doc.select("div.maininfo>dl.clearfix>dd");
			if(info_elements!=null&&info_elements.size()>5){
				certificate = info_elements.get(0).html();
				license = info_elements.get(1).html();
				gsp = info_elements.get(2).html();
				scope = info_elements.get(3).html();
				shopName = info_elements.get(4).html();
				shopAddrees = info_elements.get(5).html();
				if(shopAddrees!=null&&!"".equals(shopAddrees)){
					if(shopAddrees.indexOf("内蒙古")!=-1
							||shopAddrees.indexOf("黑龙江")!=-1
							){
						province = shopAddrees.substring(0,3);
					}else{
						province = shopAddrees.substring(0,2);
					}
				}
			}
			
			//商城 http://yaodian.yaofangwang.com/340159/
			Elements logo_elements = doc.select("div[class=saleinfo h2]>div.title>p.np>a>img");
			if(logo_elements!=null&&!logo_elements.isEmpty()){
				logo = logo_elements.attr("src");
				shopName = logo_elements.attr("alt");
			}
			
			Elements main_elements =  doc.select("div[class=saleinfo h2]>div[id=maininfo]>p");
			if(main_elements!=null&&!main_elements.isEmpty()){
				for (Element element : main_elements) {
					String str = element.text();
					String indexStr = "";
					if (str.indexOf(indexStr = "店铺地址") != -1) {
						shopAddrees = cutString(str,indexStr);
						if(shopAddrees!=null&&!"".equals(shopAddrees)){
							if(shopAddrees.indexOf("内蒙古")!=-1
									||shopAddrees.indexOf("黑龙江")!=-1
									){
								province = shopAddrees.substring(0,3);
							}else{
								province = shopAddrees.substring(0,2);
							}
						}
					}else if(str.indexOf(indexStr = "营业时间") != -1){
						business_hours = cutString(str,indexStr);
					}
				}
			}
			
			Elements sub_elements =  doc.select("div[class=saleinfo h2]>div[id=subinfo]>p");
			if(sub_elements!=null&&!sub_elements.isEmpty()){
				for (Element element : sub_elements) {
					String str = element.text();
					String indexStr = "";
					if (str.indexOf(indexStr = "营业执照") != -1) {
						license = cutString(str,indexStr);
					}else if (str.indexOf(indexStr = "GSP证号") != -1) {
						gsp = cutString(str,indexStr);
					}else if (str.indexOf(indexStr = "经营许可证") != -1) {
						certificate = cutString(str,indexStr);
					}else if (str.indexOf(indexStr = "经营范围") != -1) {
						scope = cutString(str,indexStr);
					}
				}
			}
			
			Elements type_elements =  doc.select("div[class=saleinfo h2]>div[id=subinfo]>div[class=zz clearfix]>i");
			if(type_elements!=null&&!type_elements.isEmpty()){
				for (Element element : type_elements) {
					String str = element.attr("title");
					if(str!=null&&!str.equals("")){
						if(str.equals("连锁分店")||str.equals("连锁总店")){
							type = "连锁";
							break;
						}else if(str.equals("单体药房")){
							type = "单体";
							break;
						}
					}
				}
			}
			
			//数据库操作
			if(type.equals("未分类") && shopName.indexOf("公司")!=-1){
				type = "批发商";
				CrawlerService.executeDataBankEnterprise(province, MySqlEscape.escape(logo), shopName, type, license, certificate, 
						scope, "", "", "", "", shopAddrees, "", "", "");
			}else{
				CrawlerService.addDataBankPharmacy(province, type,
						shopName, MySqlEscape.escape(logo), shopAddrees, business_hours,
						license, gsp, certificate, scope);
			}
		}
	}

	/**
	 * 医院-解析详情页
	 * @param page
	 */
	private void visitToYiyuan(Page page) {
		String href = page.getWebURL().getURL();
		this.logger.info("解析医院详情页："+href);
//		String province = YaoFangConstants.yy_detail_provinceMap.get(href);
//		String province = "北京市";
//		if (province != null && !"".equals(province)) {
			Document doc = this.getDocument(page);
			if(doc!=null){
				String province = "";
				String logo = "";
				String HospitalName = "";
				Elements img_elements = doc.select("div[class=ymain clearfix]>div.img>img");
				logo = img_elements.attr("src").toString();
				HospitalName = img_elements.attr("alt").toString().trim();
				
				String nature = "";
				String level = "";
				String tel = "";
				String addrees = "";
				String feature = "";
				String dayFlow = "0";
				String bedAmount = "0";
				Elements info_elements = doc.select("div[class=ymain clearfix]>div.info>p");
				for (Element element : info_elements) {
					String str = element.text();
					String indexStr = "";
					if (str.indexOf(indexStr = "性质") != -1) {
						nature = cutString(str,indexStr);
					} else if (str.indexOf(indexStr = "等级") != -1) {
						level = cutString(str,indexStr);
					} else if (str.indexOf(indexStr = "电话") != -1) {
						tel = cutString(str,indexStr);
					} else if (str.indexOf(indexStr = "地址") != -1) {
						addrees = cutString(str,indexStr);
						if(addrees.indexOf("内蒙古")!=-1
								||addrees.indexOf("黑龙江")!=-1
								){
							province = addrees.substring(0,3);
						}else{
							province = addrees.substring(0,2);
						}
					} else if (str.indexOf(indexStr = "专科") != -1) {
						feature = cutString(str,indexStr);
					} else if (str.indexOf(indexStr = "床位数量") != -1) {
						dayFlow = str.substring(str.indexOf("日门诊量")+ "日门诊量".length() + 1,str.indexOf("床")).trim();
						dayFlow = (filterNumber(dayFlow)==null||"".equals("")?"0":filterNumber(dayFlow));
						
						bedAmount = cutString(str.trim(),indexStr);
						bedAmount = (filterNumber(bedAmount)==null||"".equals(""))?"0":filterNumber(bedAmount);
					} 
				}
				
				String introduce = "";
				String equipment = "";
				Elements introduce_elements = doc.select("div.tbock>div.txt");
				introduce =  introduce_elements.get(0).html();
				equipment = introduce_elements.get(1).html();
				
				String url = "";
				String email = "";
				String postcode = "";
				Elements infoother_elements = doc.select("div.tbock>div.txt>p");
				for (Element element : infoother_elements) {
					String str = element.text();
					String indexStr = "";
					if (str.indexOf(indexStr = "网址") != -1) {
						url = cutString(str,indexStr);
					} else if (str.indexOf(indexStr = "邮件") != -1) {
						email = cutString(str,indexStr);
					} else if (str.indexOf(indexStr = "编") != -1) {
						postcode = cutString(str,indexStr);
					} 
				}
				
//				System.out.println(province+"|"
//						+logo.trim()+"|"
//						+HospitalName+"|"
//						+nature+"|"
//						+level+"|"
//						+tel+"|"
//						+dayFlow+"|"
//						+bedAmount+"|"
//						+equipment+"|"
//						+email+"|"
//						+postcode+"|");
				//数据库操作
				CrawlerService.executeDataBankHospital(province, logo, HospitalName, nature, level, tel, email, 
						postcode, addrees, MySqlEscape.escape(feature), dayFlow, bedAmount, 
						MySqlEscape.escape(equipment), url,"", MySqlEscape.escape(introduce));
			}
//		}
	}	
	
	/**
	 * 药企-详情解析
	 * @param page
	 */
	private void visitToYaoqi(Page page) {
		String href = page.getWebURL().getURL();
		this.logger.info("解析药企详情页:"+href);
		String province = YaoFangConstants.url_provinceMap.get(href);
//		String province = "北京市";
		if (province != null && !"".equals(province)) {
			Document doc = this.getDocument(page);
			if (doc != null) {
				String type = "厂商";
				String logo = "";
				String companyName = "";
				Elements top_elements = doc.select("div.saleinfo>div.title>p.np>a>img");
				logo = top_elements.attr("src").toString();
				companyName = top_elements.attr("alt");

				String license = "";
				String certificate = "";
				String scop = "";
				String product_address = "";
				Elements base_elements = doc.select("div.saleinfo>div.detail>p");
				for (Element element : base_elements) {
					String str = element.text();
					String indexStr = "";
					if (str.indexOf(indexStr = "执照") != -1) {
						license = cutString(str,indexStr);
					} else if (str.indexOf(indexStr = "许可证") != -1) {
						certificate = cutString(str,indexStr);
					} else if (str.indexOf(indexStr = "范围") != -1) {
						scop = cutString(str,indexStr);
					} else if (str.indexOf(indexStr = "地址") != -1) {
						product_address = cutString(str,indexStr);
					}
				}

				String introduce = "";
				Document intro_doc = HttpMethodSimulate.getReqSimulateByStream(href + "introduce.html");
				if (intro_doc != null) {
					Elements elements = intro_doc.select("div.sintro");
					introduce = elements.html();
				}

				String companyTel = "";
				String companyFax = "";
				String companyEmail = "";
				String companyAddrees = "";
				String companyUrl = "";
				String linkman = "";
				Document contact_doc = HttpMethodSimulate.getReqSimulateByStream(href + "contact.html");
				if (contact_doc != null) {
					Elements elements = contact_doc.select("div.sintro>ul.contact>li");
					for (Element element : elements) {
						String str = element.text();
						String indexStr = "";
						if (str.indexOf(indexStr = "电话") != -1) {
							companyTel = cutString(str,indexStr);
						} else if (str.indexOf(indexStr = "传真") != -1) {
							companyFax = cutString(str,indexStr);
						} else if (str.indexOf(indexStr = "邮件") != -1) {
							companyEmail = cutString(str,indexStr);
						} else if (str.indexOf(indexStr = "地址") != -1) {
							companyAddrees =cutString(str,indexStr);
						} else if (str.indexOf(indexStr = "网址") != -1) {
							companyUrl = cutString(str,indexStr);
						} else if (str.indexOf(indexStr = "人") != -1) {
							linkman = cutString(str,indexStr);
						}
					}
				}
//				this.logger.info(province+"|"
//						+logo+"|"
//						+companyName+"|"
//						+license+"|"
//						+certificate+"|"
//						+scop+"|"
//						+product_address+"|"
//						+introduce+"|"
//						+companyTel+"|"
//						+companyFax+"|"
//						+companyEmail+"|"
//						+companyAddrees+"|"
//						+companyUrl+"|"
//						+linkman);
				//数据库操作
				CrawlerService.executeDataBankEnterprise(province, logo, companyName, type, license, certificate, 
						scop, product_address, companyTel, companyFax, companyEmail, 
						companyAddrees, companyUrl, linkman, MySqlEscape.escape(introduce));
			}
		}
	}	
	
	 private String cutString(String str,String indexStr){
		 return str.substring(str.indexOf(indexStr)+indexStr.length()+1);
	 }
	 
	 private String filterNumber(String value){
		 String  val = "";
		if(value!=null && !"".equals(value)){
			char [] s = value.toCharArray();
			for (char c : s) {
				if(c>=48 && c<=57){
					val+= String.valueOf(c);
				}
			}
		} 
		return val;
	 }
}
