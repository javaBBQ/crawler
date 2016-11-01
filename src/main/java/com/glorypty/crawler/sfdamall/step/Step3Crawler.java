/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.sfda.step.Step3Crawler.java	<2015年4月2日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.sfdamall.step;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.xkshow.net.URLHelper;

import com.glorypty.crawler.sfdamall.Constants;
import com.glorypty.crawler.sfdamall.StepCrawler;
import com.glorypty.crawler.sfdamall.TempDB;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;

/**
 * 采集详情页内容 by 详情页
 * @Author Andy
 * @Date 2015年7月3日 
 */
public class Step3Crawler extends StepCrawler {
	
	@Override
	public void visit(Page page) {
		//**商品标准库*/
		Map<String,String> prd_normal = new  Hashtable<String, String>();
		String url = page.getWebURL().getURL();
		try {
			Document doc = this.getDocument(page);
			// 仅解析三级[详情]页面
			if (null != doc && url.startsWith(Constants.URL_PREFIX_LEV3)) {
				
				String tableId = URLHelper.getValue(url, "tableId");
				Elements contents = doc.select("div table[align=center]>tbody>tr[bgcolor!=#659ace]");
				if (null != contents && !contents.isEmpty()) {
					for (Element element : contents) {
						String filed = element.select("td[nowrap=true]").text();
						String value = element.select("td[nowrap!=true]").text();
						filed = Constants.getFiledPrdNormal(tableId).get(filed);
						if(filed!=null && !"".equals(filed)){
							prd_normal.put(filed, value);
//							this.logger.info("tableId:"+tableId+",filed:"+filed+",value:"+value);
						}
					}
					String groupName = TempDB.DATA_TYPE_NAME.get(tableId);
					this.saveDB(prd_normal,groupName);
				}
			}
		} catch (Exception e) {
			this.logger.error("执行：内容页码(" + url + ")： " + e);
		}
	}
	
	/**
	 * 插入数据库
	 * @author Andy
	 * @param map
	 */
	private void saveDB(Map<String, String> map,String groupName) {
		if(map!=null && !map.isEmpty()){
			String name	= MySqlEscape.escape(map.get("name")==null?"": map.get("name"));			
			String name_en = MySqlEscape.escape(map.get("name_en")==null?"": map.get("name_en"));				
			String common_name = MySqlEscape.escape(map.get("common_name")==null?"": map.get("common_name"));			
			String manufacture = MySqlEscape.escape(map.get("manufacture")==null?"": map.get("manufacture"));			
			String manufacture_en = MySqlEscape.escape(map.get("manufacture_en")==null?"": map.get("manufacture_en"));				
			String country = MySqlEscape.escape(map.get("country")==null?"": map.get("country"));	
			String country_en = MySqlEscape.escape(map.get("country_en")==null?"": map.get("country_en"));	
			String chinesedrugyieldly = MySqlEscape.escape(map.get("chinesedrugyieldly")==null?"": map.get("chinesedrugyieldly"));	
			String chinesedrugyieldly_en = MySqlEscape.escape(map.get("chinesedrugyieldly_en")==null?"": map.get("chinesedrugyieldly_en"));	
			String approvalno = MySqlEscape.escape(map.get("approvalno")==null?"": map.get("approvalno"));			
			String proddosageformnotext	= MySqlEscape.escape(map.get("proddosageformnotext")==null?"": map.get("proddosageformnotext"));
			String prodspecification = MySqlEscape.escape(map.get("prodspecification")==null?"": map.get("prodspecification"));	
			String prescriptionclasstext = MySqlEscape.escape(map.get("prescriptionclasstext")==null?"": map.get("prescriptionclasstext"));
			String busitypetext = MySqlEscape.escape(map.get("busitypetext")==null?"": map.get("busitypetext"));			
			String approvalvalidtime = MySqlEscape.escape(map.get("approvalvalidtime")==null?"":  map.get("approvalvalidtime"));		
			String prodvalidtime = MySqlEscape.escape(map.get("prodvalidtime")==null?"": map.get("prodvalidtime"));			
			String managementtypename = MySqlEscape.escape(map.get("managementtypename")==null?"":  map.get("managementtypename"));		
			String registerno = MySqlEscape.escape(map.get("registerno")==null?"": map.get("registerno"));				
			String respectivelicense = MySqlEscape.escape(map.get("respectivelicense")==null?"": map.get("respectivelicense"));		
			String prodstandard = MySqlEscape.escape(map.get("prodstandard")==null?"": map.get("prodstandard"));			
			String indication = MySqlEscape.escape(map.get("indication")==null?"": map.get("indication"));				
			String storageconditiontext = MySqlEscape.escape(map.get("storageconditiontext")==null?"": map.get("storageconditiontext"));	
			String drug_standard_code = MySqlEscape.escape(map.get("drug_standard_code")==null?"": map.get("drug_standard_code"));	
			String raw_material = MySqlEscape.escape(map.get("raw_material")==null?"": map.get("raw_material"));
			
			/*
			 * SPU标准库-扩展
			 * */
			String specification = MySqlEscape.escape(map.get("specification")==null?"": map.get("specification"));
			String usageanddosage = MySqlEscape.escape(map.get("usageanddosage")==null?"": map.get("usageanddosage"));	
			String notices = MySqlEscape.escape(map.get("notices")==null?"": map.get("notices"));
			String remark = MySqlEscape.escape(map.get("remark")==null?"": map.get("remark"));			
			
			/*
			 * SPU标准库特有属性
			 * */
			String pub_hygiene_license = MySqlEscape.escape(map.get("pub_hygiene_license")==null?"": map.get("pub_hygiene_license"));
			String pub_suitable_people = MySqlEscape.escape(map.get("pub_suitable_people")==null?"": map.get("pub_suitable_people"));
			String pub_unsuitable_people = MySqlEscape.escape(map.get("pub_unsuitable_people")==null?"": map.get("pub_unsuitable_people"));
			String pub_functional_composition = MySqlEscape.escape(map.get("sp_nutrient")==null?"": map.get("pub_functional_composition"));
			//保健品		
			String bjp_health_function = MySqlEscape.escape(map.get("bjp_health_function")==null?"": map.get("bjp_health_function"));
			String bjp_health_composition = MySqlEscape.escape(map.get("bjp_health_composition")==null?"": map.get("bjp_health_composition"));
			
			/*
			 * 数据字典 
			 * */
			//剂型
			Set<String> textKeySet = Constants.FORMULATION_CODE.keySet();
			for (String s : textKeySet) {
				if(proddosageformnotext.contains(s)){
					proddosageformnotext = Constants.FORMULATION_CODE.get(s);
					break;
				}
			}
			if(!proddosageformnotext.startsWith("jx"))
				proddosageformnotext = Constants.FORMULATION_CODE.get("其它");
			//处方分类	
			textKeySet = Constants.PRESCRIPTION_CODE.keySet();
			for (String s : textKeySet) {
				if(prescriptionclasstext.contains(s)){
					prescriptionclasstext = Constants.PRESCRIPTION_CODE.get(s);
					break;
				}
			}
			if(!prescriptionclasstext.startsWith("cf"))
				prescriptionclasstext = Constants.PRESCRIPTION_CODE.get("其它");
			//业务类型
			textKeySet = Constants.BUSINESS_CODE.keySet();
			for (String s : textKeySet) {
				if(busitypetext.contains(s)){
					busitypetext = Constants.BUSINESS_CODE.get(s);
					break;
				}
			}
			if(!busitypetext.startsWith("yw"))
				busitypetext = Constants.BUSINESS_CODE.get("其它");
			//管理类别名称
			textKeySet = Constants.MANAGE_CODE.keySet();
			for (String s : textKeySet) {
				if(managementtypename.contains(s)){
					managementtypename = Constants.MANAGE_CODE.get(s);
					break;
				}
			}
			if(!managementtypename.startsWith("gl"))
				managementtypename = Constants.MANAGE_CODE.get("其它");
			//储存条件
			textKeySet = Constants.STORE_CODE.keySet();
			for (String s : textKeySet) {
				if(storageconditiontext.contains(s)){
					storageconditiontext = Constants.STORE_CODE.get(s);
					break;
				}
			}
			if(!storageconditiontext.startsWith("cc"))
				storageconditiontext = Constants.STORE_CODE.get("其它");
			
			//数据库操作
			CrawlerService.executeMallBaseData(name, name_en, common_name, manufacture, manufacture_en, chinesedrugyieldly, approvalno, proddosageformnotext, prodspecification, prescriptionclasstext, busitypetext, approvalvalidtime, prodvalidtime, managementtypename, registerno, respectivelicense, prodstandard, indication, storageconditiontext, drug_standard_code, 
					country, country_en, chinesedrugyieldly_en,raw_material,
					/*扩展*/specification,usageanddosage, notices, remark, 
					/*特有*/pub_hygiene_license, pub_suitable_people, pub_unsuitable_people, pub_functional_composition,
						bjp_health_function,bjp_health_composition,
					groupName);
		}
	}

}
