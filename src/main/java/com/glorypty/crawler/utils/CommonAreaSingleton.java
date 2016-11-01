/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.utils.CommonAreaSingleton.java <2015年12月22日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import cn.xkshow.io.file.PropertyFile;

import com.glorypty.common.TreeGrid;

/**
 * 地区单例
 * @author ZhangLujun 
 * @Date 2015年12月22日 下午2:35:18
 * @version 1.0
 */
public class CommonAreaSingleton {
	private final static Logger logger = Logger.getLogger(CommonAreaSingleton.class);
	
	private static CommonAreaSingleton singleton = new CommonAreaSingleton();
	public static TreeGrid treeGrid = null;
	private CommonAreaSingleton() {}
	
	public static CommonAreaSingleton getSingleton(){
		if(treeGrid == null){
			System.out.println("获取地区单例启动....");
			getAll();
		}
			
		return singleton;
	}
	
	/**
	 * 获取所有地区树节点
	 * @author ZhangLujun<2015年12月23日>
	 * @return
	 */
	private static TreeGrid getAll(){
		try {
			Properties pros = PropertyFile.getInstance().loadingProperties("system.properties");
			String url = pros.getProperty("cmsgoods_area");
			String obj = executeGet(url);
			
			Object[] objArr  = JsonPluginsUtil.jsonToObjectArray(obj);
			for (Object object : objArr) {
				obj = object.toString();
			}
			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("children", TreeGrid.class);
			treeGrid = (TreeGrid) JsonPluginsUtil.jsonToBean(obj, TreeGrid.class, classMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return treeGrid;
	}
	
	
	/**
	 * 根据地区名称获取地区ID
	 * @author ZhangLujun<2015年12月22日>
	 * @param province 省 例如：北京，湖南
	 * @param city 市 例如 海定区，长沙市
	 * @return 
	 */
	public String getAreaId(String province, String city){
		String id = "";
		try {
			List<TreeGrid> provinceList = treeGrid.getChildren();
			for (TreeGrid treeGrid : provinceList) {
				if(treeGrid.getText().indexOf(province)!=-1){
					if(city!=null&&!"".equals(city)){
						List<TreeGrid> cityList = treeGrid.getChildren();
						for (TreeGrid treeGrid2 : cityList) {
							if(treeGrid2.getText().indexOf(city)!=-1){
								id = String.valueOf(treeGrid2.getId());
								break;
							}else{
								id = String.valueOf(treeGrid2.getId());
							}
						}
					}else{
						id = String.valueOf(treeGrid.getId());
						break;
					}
				}
			}	
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return id;
	}

	public static void main(String[] args) {
//		System.out.println(CommonAreaSingleton.getSingleton().getAreaId("广东", "广州"));
		String url = "http://localhost:8088/webservice/cdb/search/area/9.json";
		try {
			System.out.println(CommonAreaSingleton.getSingleton().executeGet(url));
//			String r = HttpClientHelper.create().getString(url, HttpMethodEnum.GET, new HashMap<String, Object>(), "");
//			System.out.println(r);
//			String aa="涓浗";
//			aa = new String(aa.getBytes("gbk"),"utf-8");
//			System.out.println(aa);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public static String executeGet(String url) throws Exception {  
        BufferedReader in = null;  
        String content = null;  
        try {  
            // 定义HttpClient  
            HttpClient client = new DefaultHttpClient();  
            // 实例化HTTP方法  
            HttpGet request = new HttpGet();  
            request.setURI(new URI(url));  
            HttpResponse response = client.execute(request);  
  
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"utf-8"));  
            StringBuffer sb = new StringBuffer("");  
            String line = "";  
            String NL = System.getProperty("line.separator");  
            while ((line = in.readLine()) != null) {  
                sb.append(line + NL);  
            }  
            in.close();  
            content = sb.toString();  
        } finally {  
            if (in != null) {  
                try {  
                    in.close();// 最后要关闭BufferedReader  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            return content;  
        }  
    }  
}
