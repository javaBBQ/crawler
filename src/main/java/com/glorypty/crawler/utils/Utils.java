/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.utils.Utils.java <2015年7月8日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @Author hardy 
 * @Date 2015年7月8日 上午11:36:00
 * @version 1.0
 */
public class Utils {
	
	/**
	 * 截取指定长度的字符串
	 * @Author hardy<2015年7月8日>
	 * @param str字符串
	 * @param len指定长度
	 * @return
	 */
	public static String substring(String str, int len){
		if(StringUtils.isEmpty(str) || len<1)
			return "";
		if(len < str.length())
			return str.substring(0, len);
		return str;
	}
	
	
	/**
	 * 获取数字
	 * @author ZhangLujun<2015年12月17日>
	 * @param value
	 * @return
	 */
	public static List<String> getNumber(String value){
		List<String> list = new ArrayList<String>();
		String regex = "\\d*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(value);
		while(m.find()){
			if(!"".equals(m.group()))
				list.add(m.group());
		}
		return list;
	}
	
	/**
	 * 获取Url参数
	 * @author ZhangLujun<2015年12月22日>
	 * @param baseUrl
	 * @return
	 */
	public static Map<String,Object> getKeyValue(String baseUrl){
		int index = baseUrl.indexOf("?");
		if(index<0){
			return null;
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		baseUrl = baseUrl.substring(index+1);
		String[] arr = baseUrl.split("&");
		for (String str : arr) {
			String[] key_value = str.split("=");
			map.put(key_value[0], key_value[1]);
		}
		return map;
	}
}
