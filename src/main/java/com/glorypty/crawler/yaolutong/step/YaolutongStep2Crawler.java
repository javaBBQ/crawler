package com.glorypty.crawler.yaolutong.step;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.crawler.yaolutong.YaolutongConstants;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class YaolutongStep2Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		boolean flag = false;
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			do{
				// 代理
				if(href.indexOf(YaolutongConstants.DAILI_URL_CONTENT_PRE)!=-1){
					flag =  true;
					break;
				}
				// 招商信息
				if(href.indexOf(YaolutongConstants.ZHAOSHANG_URL_CONTENT_PRE)!=-1 ){
					flag =  true;
					break;
				}
			}while(false);
		}
		return flag;
	}

	@Override
	public void visit(Page page) {
		
		String href = page.getWebURL().getURL();
		this.logger.info("抓取信息启动:"+href);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
		try {
				do{
					//代理
					if(href.startsWith(YaolutongConstants.DAILI_PAGE_URL_HOME_PRE)){
						visitDLToContent(page);
						break;
					}
					//招商
					if(href.startsWith(YaolutongConstants.ZHAOSHANG_URL_CONTENT_PRE)){
						visitZSToContent(page);
						break;
					}
					
				}while(false);
		} catch (Exception e) {
			this.logger.info("详情页异常信息:"+e.getMessage());
		}
	}
	
	/**
	 * 招商信息
	 * @param page
	 * @param parent
	 * @throws UnsupportedEncodingException 
	 */
	private void visitZSToContent(Page page) throws UnsupportedEncodingException {
		
		Document doc = this.getDocument(page);
		String href = page.getWebURL().getURL();
		if(doc !=  null){
			//剔除图片
			ConstantsCrawler.clearImg(doc, page.getWebURL().getURL());
			
			
			String param = href.substring(href.indexOf("?"),href.length());
			String type = getGroupId(page.getWebURL().getURL());
			String area_id = "9";//地区ID
			String main_picture = "";

			String price = "";//价格
			String num = "";//供应数量
			
			String linkman = "";
			String telephone = "";
			String mobile = "";
			String qq = "";
			String email = "";
			String area = "";
			
			
			
			String title = doc.select("div[class=youb]>ul>li[class=tim]").get(0).html();//标题 小儿止泻灵颗粒   企业待认证  
			if(title.indexOf("&nbsp")!=-1){
				title = title.substring(0,title.indexOf("&nbsp"));
			}
			String spec = doc.select("div[class=youb]>ul>li").get(3).text();//规格或单位 产品规格：6g＊5袋
			if(spec.indexOf("产品规格：")!=-1){
				spec = spec.substring(5,spec.length());
			}
			String description = doc.select("div[class=cont_y]").get(0).html(); //商品信息
			Elements detailElements = doc.select("div[class=lxfs]>div>table>tbody>tr>td[class=ming]");
			if(detailElements==null||detailElements.size()<1){
				detailElements = doc.select("div[class=lxfs]>table>tbody>tr>td[class=ming]");
			}
			String coname = detailElements.get(0).text();//公司名称
			Elements e = doc.select("div[class=lxfs]>div>table>tbody>tr>td");
			if(e==null||e.size()<1){
				e = doc.select("div[class=lxfs]>table>tbody>tr>td");
				linkman = e.get(3).text();//联系人
				telephone = e.get(5).text();//电话
				mobile = e.get(9).text();//手机
				qq = e.get(11).text(); 
				email = e.get(15).text();
			}else{
				linkman = e.get(5).text();//联系人
				telephone = e.get(9).text();//电话
				mobile = e.get(11).text();//手机
				qq = e.get(13).text(); 
				email = e.get(17).text();
			}
			String province = doc.select("div[class=cont_y]>ul>li[class=nei]").get(1).html(); //商家所在地 省
//			北京市,山东省,江西省,湖北省,山西省,内蒙古,湖南省,广西区,海南省,云南省,西藏区,贵州省,甘肃省,宁夏区,青海省,新疆区 
			String city = doc.select("div[class=youb]>ul>li").get(3).html();; //商家所在地 市 <b>产品规格：</b>6g＊5袋
			
			String add_desc = doc.select("div[class=cont_y]>ul>li[class=nei]").get(9).html();//订购[要求]说明
			
			
			

//			area = URLDecoder.decode(param.substring(param.indexOf("&zone="), param.length()).replace("&zone=", ""),"UTF-8");
			
			
			if(!StringUtils.isEmpty(province)&&province.indexOf("全国")!=-1){
				area_id="9";
			}else{
				//area_id = ConstantsCrawler.commonAreaSingleton.getAreaId(province,city);
			}
			
			
			
//			System.out.println("单价:"+price+",规格："+spec+",商品名："+title+",数量："+num+",联系人："+linkman+",手机："+mobile+",电话："+telephone+",地区："+area+",公司名："+coname+",地区编号:"+area_id);
			//写入DB
			CrawlerService.executeDataBankCmsGoods(type, area_id, title, price, num, spec,
					coname, linkman, telephone, mobile, email, qq, MySqlEscape.escape(main_picture), MySqlEscape.escape(description), MySqlEscape.escape(add_desc));

		}
		
	}



	/**代理*/
	private void visitDLToContent(Page page) {
		String source = "";
		String title = "";
		String content = "";
		String groupId = getGroupId(page.getWebURL().getURL());
		
		Document doc = this.getDocument(page);
		if(doc !=  null){
			//剔除图片
			ConstantsCrawler.clearImg(doc, page.getWebURL().getURL());
			
			title = doc.getElementById("title").html();
			source = doc.getElementById("ly").html();
			content = doc.select("table[width=630]").get(0).select("td[colspan=4]").get(2).html().replaceAll("div", "span");
			
		}
	}

	private String getGroupId(String parent) {
		String groupId = null;
		do{
			if(parent.indexOf("zhaoshang")!=-1){//新品抢鲜
				groupId = "2";
				break;
			}
			if(parent.indexOf("daili")!=-1){//警示平台
				groupId = "3";
				break;
			}
		}while(false);
		return groupId;
	}
	
}
