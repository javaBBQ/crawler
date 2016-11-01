/*
 *Project: glorypty-crawler
 *File: com.glorypty.unwall.hc360.HcLogin.java <2016年2月22日>
 ****************************************************************
 * 版权所有@2016 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.unwall.hc360;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.xkshow.commons.http.HttpClientHelper;
import cn.xkshow.unwall.ruokuai.api.RuoKuaiAPI;
import cn.xkshow.unwall.ruokuai.domain.RuoKuai;
import cn.xkshow.unwall.ruokuai.domain.RuoKuaiCreate;
import cn.xkshow.util.DateUtil;
import cn.xkshow.util.regex.RegexUtil;

import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.jdbc.CrawlerService;
import com.glorypty.unwall.UnwallConstants;
import com.glorypty.unwall.util.VCodeUtil;
/**
 *
 * @author hardy 
 * @Date 2016年2月22日 下午2:48:46
 * @version 1.0
 */
public class HcValidata {
	public static boolean firstOpenDetail = true;
	private static final Logger logger = Logger.getLogger(HcValidata.class);
	public static Scanner sc = new Scanner(System.in);
	
	/**
	 * 登录
	 * @author hardy<2016年2月23日>
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static boolean login() throws ParseException, IOException{
		HcConstants.cookiesSSL =  new BasicCookieStore();
		HcConstants.httpClient = HcConstants.createSSLClientDefault(true);
			
		HttpGet httpLoginInit = new HttpGet(HcConstants.URL_LOGIN_PAGE);
		String loginInitHtml = EntityUtils.toString(HcConstants.httpClient.execute(httpLoginInit).getEntity());			
		Document doc = Jsoup.parse(loginInitHtml);
		String token = doc.select("form > input[name=aToken]").attr("value");
				
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("LoginID", HcConstants.accID);
		param.put("Passwd", HcConstants.accPWD);
		param.put("logincode", "2013new");
		param.put("aToken", token);
		
		List<NameValuePair> pairs = null;
        if(param != null && !param.isEmpty()){
            pairs = new ArrayList<NameValuePair>(param.size());
            for(Map.Entry<String, Object> entry : param.entrySet()){
                if(null != entry.getValue()){
                    pairs.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
            }
        }
		HttpPost httpPost = new HttpPost(HcConstants.URL_LOGIN);
		HttpClientHelper.setHeaders(httpPost);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));  
		HttpResponse httpresponse = HcConstants.httpClient.execute(httpPost);  
//		HttpEntity entity = httpresponse.getEntity();  
//		String body = EntityUtils.toString(entity); 
//		System.out.println(body);
//		//获得跳转的网址
        Header locationHeader = httpresponse.getFirstHeader("Location");  
        //登陆不成功
        if (locationHeader == null){
        	logger.error("登陆不成功，请稍后再试!");
        	return false; 
        }//登录成功
        		
		HttpGet httpGet = new HttpGet(HcConstants.URL_WELCOME);
		HttpResponse httpresponse4 = HcConstants.httpClient.execute(httpGet);  
		String welcomeHtml = EntityUtils.toString(httpresponse4.getEntity());
		String hello = RegexUtil.findMatchContent("<div class=\"mmtTop_user\">.*</div>", welcomeHtml);
		logger.info("登录成功！！" + hello.substring(hello.indexOf("您好"), hello.indexOf("<span>")));				
		return true;
	}
	
	/**
	 * 进入详情页
	 * @author hardy<2016年2月23日>
	 * @param infoURL
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void detail(String infoURL) throws ClientProtocolException, IOException{		
		if(firstOpenDetail)
			HcValidata.login();
		
		HttpGet httpGet = new HttpGet(infoURL);
		HttpResponse httpresponse = HcConstants.httpClient.execute(httpGet); 		
		String infoHtml = EntityUtils.toString(httpresponse.getEntity());	
		if(infoHtml.contains("<li class=\"iv1\">请输入验证码：</li>")){
			if(!firstOpenDetail && !HcValidata.login())
				return;
			String seed = infoHtml.substring(infoHtml.indexOf("id=\"seedr\" value=")+18).split("\"")[0];	
			while(!inputVCode(infoURL, seed)) {	
				logger.info("验证码输入错误......");
			}
			firstOpenDetail = false;
			detail(infoURL);//验证码输入成功，重新加载页面
		}else{
			analyDetail(infoHtml);
		}
	}
	
	/**
	 * 解析详情页
	 * @author hardy<2016年2月23日>
	 * @param html
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void analyDetail(String html) throws ClientProtocolException, IOException{
		Document doc = Jsoup.parse(html);
//		System.out.println(doc.select(".contacti-vcode").text());	
		
		String type="1";//商讯类型
		String area_id = "";//地区ID
		
		String coname = ""; //公司名称
		String linkman = ""; //联系人
		String telephone = ""; //电话
		String mobile =""; //手机
		//商家所在地区
		String province = ""; 
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
			if(doc!=null){
				//公司信息
				Elements company_elements = doc.select("div.sbarbx>div.sbarbx-bb>div.companyi>ul>li");
				if(!company_elements.isEmpty()){
					for (Element element : company_elements) {
						Elements area_elements= element.select("span");
						if(area_elements.isEmpty()){
							coname = element.text();
						}else{
							for (Element element2 : area_elements) {
								String value = element2.text();
								if(value.indexOf("中国")!=-1){
									String[] val = value.split("-");
									if(val.length<3){
										province = val[1].substring(0, val[1].length()-1);
									}else{
										province = val[1].substring(0, val[1].length()-1);
										city = val[2].substring(0, val[2].length()-1);
									}
									area_id = ConstantsCrawler.commonAreaSingleton.getAreaId(province,city);
									logger.info("省："+province+",市："+city+",id:"+area_id);
									break;
								}
							}
			
						}
					}
				}
				
				//联系信息
				Elements linkMan_elements = doc.select(".lxname");
				if(!linkMan_elements.isEmpty()){
					linkman = linkMan_elements.text();
				}
				
				Elements link_elements = doc.select("div.sbarbx>div.sbarbx-bb>div.contacti-vcode>ul>li");
				if(!link_elements.isEmpty()){
					for (Element element : link_elements) {
						String contacttitle = element.getElementsByClass("contact-title").text();
						String i_info = element.getElementsByClass("i-info").text();
						contacttitle = contacttitle.replaceAll(" ", "").trim();
						if(contacttitle.indexOf("手机")!=-1){
							mobile = i_info;
						}else if(contacttitle.indexOf("电话")!=-1){
							telephone = i_info;
						}
					}
				}
				
				//标题
				Elements title_elements = doc.select("div.bmain-w>div.bmainw-i>h2");
				if(!title_elements.isEmpty()){
					title = title_elements.text();
				}
				
				//信息
				//div.bmain-w>div.bmainw-i>div.prodetail-list>div.pitem>span.pdtitle
				//div.bmain-w>div.bmainw-i>div.prodetail-list>div.pitem>span.i-info
				Elements info_elements = doc.select("div.bmain-w>div.bmainw-i>div.prodetail-list>div.pitem");
				if(!info_elements.isEmpty()){
					for (Element element : info_elements) {
						String pdtitle = element.getElementsByClass("pdtitle").text();
						String i_info = element.getElementsByClass("i-info").text();
						pdtitle = pdtitle.replaceAll(" ", "").trim();
						if(pdtitle.indexOf("规")!=-1){
							spec = i_info;
						}else if(pdtitle.indexOf("采购量")!=-1){
							num = "1";
						}else if(pdtitle.indexOf("目标价")!=-1){
							price = element.getElementsByClass("i-pprice").text();
						}
					}
				}
			}
			
			//写入DB
			CrawlerService.executeDataBankCmsGoods(type, area_id, title, price, num, spec,
					coname, linkman, telephone, mobile, email, qq, MySqlEscape.escape(main_picture), MySqlEscape.escape(description), MySqlEscape.escape(add_desc));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 输入及校验验证码
	private static boolean inputVCode(String codeUrl, String seed){
		try {
			if(null==codeUrl || codeUrl.equals("")){
				return false;
			}
			
			RuoKuaiCreate objVCode = null;
			String ValidKey = null;
			if(UnwallConstants.VCODE_AUTO){				
				objVCode = VCodeUtil.crackTopic(createVCode(seed, true));
				ValidKey = objVCode.getResult();
				logger.info("ValidKey:"+ValidKey);
			}else{			
				createVCode(seed, false);									
				System.out.println("请输入验证码：");
				ValidKey = sc.next();
			}
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("ValidKey", ValidKey));
			params.add(new BasicNameValuePair("seed", seed));		
			
			HttpPost httpPost = new HttpPost(codeUrl);
			HttpClientHelper.setHeaders(httpPost);
			httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			HttpResponse httpresponse = HcConstants.httpClient.execute(httpPost);  
			String infoHtml = EntityUtils.toString(httpresponse.getEntity());
			if(infoHtml.contains("<li class=\"iv1\">请输入验证码：</li>")){
				if(UnwallConstants.VCODE_AUTO){
					RuoKuai obj = RuoKuaiAPI.report(UnwallConstants.RUOKUAI_UNAME, UnwallConstants.RUOKUAI_PWD, objVCode.getId());
					System.out.println(obj.getResult());
				}
				return false;
			}		
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 	
	}	
	// 生成验证码
	private static byte[] createVCode(String seed, boolean returnVCodeByte){
		HttpGet hg = new HttpGet(MessageFormat.format(HcConstants.URL_VCODE, seed));
		try {			
			HttpResponse httpresponse = HcConstants.httpClient.execute(hg);
			HttpEntity entity = httpresponse.getEntity();
			InputStream content = entity.getContent();
			byte[] b = IOUtils.toByteArray(content);
			if(returnVCodeByte){
				return b;
			}
			FileUtils.writeByteArrayToFile(new File(MessageFormat.format(HcConstants.URI_VCODE, DateUtil.toUnsignedDateTime(new Date()))), b);
			EntityUtils.consume(entity);
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
