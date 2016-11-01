package com.glorypty.crawler.ehaoyao.step;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.transaction.annotation.Transactional;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.ehaoyao.EHaoYaoConstants;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.image.ThumbnailatorImageUtil;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;

/**
 * 信息采集
 * @author liuj
 *
 */
public class Step2Crawler extends BaseCrawler {


	@Override
	public void visit(Page page) {
		try {
			Map<String,String> prodValues = new HashMap<String,String>();
			List<String> prodPics = new ArrayList<String>();
			
			Document doc = this.getDocument(page);
			this.logger.info("好药师网解析详情页："+page.getWebURL().getURL());
			if(doc != null){
				/**** 商品属性参数 ****/
				Elements goodsParams = doc.select("div[class=bd cf]>div.myli>div.tab1>p[class=bg-color p]>span");
				for(Element para : goodsParams){
					String text = para.text().trim();
					
					if(text.contains("：")){
						String keyVa[] = text.split("：");
						if(keyVa.length == 2){
							String key = keyVa[0].trim();
							String val = keyVa[1].trim();
							if(EHaoYaoConstants.deltailParam.containsKey(key)){
								key = EHaoYaoConstants.deltailParam.get(key);
								val = MySqlEscape.escape(val);
								prodValues.put(key, val);
							}else{
								this.logger.error("存在不明商品属性：" + key + " 请核对程序与网站页面元素！");
							}
						}
					}
				}
				
				/**** 商品详情tab页元素 ****/
				Elements goodsDetilDivs = doc.select("div[class=bd cf]>div.myli>div.tab1>div.goods-details");
				Elements goodsDetilImages = goodsDetilDivs.select("div");
				String detialText = goodsDetilDivs.toString();
				boolean hasDetailPic = false;
				for(Element div : goodsDetilImages){
					String goodsDetilPic = div.select("img").attr("src");
					if(goodsDetilPic == null || "".equals(goodsDetilPic.trim())){
						continue;
					}
					String realPath = uploadPic(goodsDetilPic);
					//如果上传成功
					if(realPath != null){
						//把说明书内容替换
						detialText = detialText.replaceAll(goodsDetilPic, realPath);
						hasDetailPic = true;
					}
					//如果上传失败把这个<div>干掉
					else{
						detialText = detialText.replaceAll(div.toString(), "");
					}
				}
				//如果详情tab页没有文本，页没有图片则说明没有信息
				
				if((null == goodsDetilDivs.text() || "".equals(goodsDetilDivs.text().trim())
						&& !hasDetailPic)){
					
				}else{
					detialText = MySqlEscape.escape(detialText);
					prodValues.put("detail_text", detialText);
				}
				
				
				/**** 商品价格元素 ****/
				String price = doc.select("div.detail-main>div.main-top>table>tbody>tr>td[style=font-size:24px;font-weight:bolder;color:#F15A28]>i[id=meal_price]").text().trim();
				prodValues.put("price", price);
				
				//商品所在类型
				/*Elements typePaths = doc.select("div[class=path1200 center]>a");
				String path ="";
				for(int i = 1; i < typePaths.size() -1 ; i++){
					path = path +" "+ typePaths.get(i).text();
				}
				//System.out.println("path :" +path);
				*/
				
				/**** 商品的图片这里只存了原图 ****/
				Elements imagLi = doc.select("div[class=small-img cf]>div.wrap>ul[class=imgs cf]>li");
				
				for(Element imags : imagLi){
					String temp = imags.select("a").attr("rel");
					if(temp.contains("largeimage:") ){
						String  largeImage = temp.split("largeimage:")[1].split("'")[1];
						String realPath = uploadPic(largeImage);
						//如果上传成功
						if(realPath != null){
							//把图片存放入MAP
							realPath = MySqlEscape.escape(realPath);
							prodPics.add(realPath);
						}
					}
				}
				
				/**** 说明书整个tab 页的内容****/
				Elements explanDiv= doc.select("div[class=bottom cf]>div.trandition>div[class=bd cf]>div.myli>div.tab5");
				String explanText = explanDiv.toString();
				
				//http://www.ehaoyao.com/product-3512.html 说明书有多个图片
				Elements explanPics = doc.select("div[class=bottom cf]>div.trandition>div[class=bd cf]>div.myli>div.tab5>img");
				boolean hasExplanPic = false;
				for(Element pic : explanPics){
					 String explanPic = pic.attr("src");
					 String realPath = null;
						if(explanPic != null){
							//如果src 属性不是loading
							if(!explanPic.endsWith("loadings.gif")){
								
								realPath = uploadPic(explanPic);
								//如果上传成功
								if(realPath != null){
									//把说明书内容替换
									explanText = explanText.replaceAll(explanPic, realPath);
									hasExplanPic= true;
								}
								//如果图片处理失败则取"_src"属性试试
								else{
									explanPic = pic.attr("_src");
									realPath = uploadPic(explanPic);
									//如果上传成功
									if(realPath != null){
										//把说明书内容src 与_src属性替换
										String temp = pic.attr("src");
										int loadIndex = explanText.indexOf(temp);
										int loadLength = temp.length();
										String explanTextPre = explanText.substring(0,loadIndex);
										String explanTextSub = explanText.substring(loadIndex+loadLength);
										explanText = explanTextPre + realPath +explanTextSub;
										
										hasExplanPic= true;
									}
									//如果上传失败把这个<img>干掉
									else{
										explanText = explanText.replaceAll(pic.toString(), "");
									}
								}
							}
							//如果src 属性是loading 则取"_src"属性试试
							else{
								explanPic = pic.attr("_src");
								realPath = uploadPic(explanPic);
								//如果上传成功
								if(realPath != null){
									//src属性变为_src属性
									String temp = pic.attr("src");
									int loadIndex = explanText.indexOf(temp);
									int loadLength = temp.length();
									String explanTextPre = explanText.substring(0,loadIndex);
									String explanTextSub = explanText.substring(loadIndex+loadLength);
									explanText = explanTextPre + realPath +explanTextSub;
									hasExplanPic= true;
								}
								//如果上传失败把这个<img>干掉
								else{
									explanText = explanText.replaceAll(pic.toString(), "");
								}
							}
						}
				}
				//如果说明tab页没有文本，页没有图片则说明没有信息
				if((null == explanDiv.text() || "".equals(explanDiv.text().trim()))
						&& !hasExplanPic){
				}else{
					explanText = MySqlEscape.escape(explanText);
					prodValues.put("explan_text", explanText);
				}
			}
			insert(prodValues, prodPics);
		} catch (Exception e) {
			this.logger.error(e);
		}
	}

	@Transactional
	private void insert(Map<String, String> prodValues,List<String> prodPics) {
		CrawlerService.executeBasedata("cj_ehaoyao_prd", prodValues,"jiaoyi");
		Map<String, Object> map = CrawlerService.queryMallWithMap("SELECT MAX(id) id FROM cj_ehaoyao_prd");
		if(map.containsKey("id")){
			Integer id = (Integer) map.get("id");
			for(String path : prodPics){
				CrawlerService.executeMallProImageData(path, id);
			}
		}
	}
	
	private String uploadPic(String srcUrl) {
		URL url;
		try {
			url = new URL(srcUrl);

			// 程序运行时图片名的前缀生成策略
			String picName = ThumbnailatorImageUtil.rename();

			// 程序运行时存储路径
			String outFile = EHaoYaoConstants.OUT_FILE + picName+ EHaoYaoConstants.PIC_SUF;

			// 存储到数据库的图片路径
			String realPath = EHaoYaoConstants.STORE_PATH_PRE + picName+ EHaoYaoConstants.PIC_SUF;

			// 如果上传成功
			if (ThumbnailatorImageUtil.downToFile(url, EHaoYaoConstants.WIDTH,EHaoYaoConstants.HEIGHT, outFile)) {
				return realPath;
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());;
		}
		return null;
	}
}
