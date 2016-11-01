package com.glorypty.crawler.utils;

/**
 * 写入Mysq，转义字符
 * @author Administrator
 *
 */
public class MySqlEscape {

	/**
	 * 字符转义
	 * @param txt
	 * @return
	 */
	public static String escape(String txt){
		if(txt==null||txt.equals("")){
			return "";
		}else{
			if(txt.indexOf("\"")!=-1){
				txt = txt.replaceAll("\"", "\\\\\"");
			}else if(txt.indexOf("\'")!=-1){
				txt = txt.replaceAll("\'", "\\\\\'");
			}
			return txt;
		}
	}
}
