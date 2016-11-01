/*
 *Project: glorypty-crawler
 *File: com.glorypty.unwall.taobao.TaobaoConstants.java <2015年12月23日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.unwall.taobao;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

/**
 *
 * @author hardy
 * @Date 2015年12月23日 下午4:50:54
 * @version 1.0
 */
public class TaobaoConstants {
	// 安全Cookies(SSL)
	public static CookieStore cookiesSSL = new BasicCookieStore();
	// 普通Cookies
	public static CookieStore cookies = new BasicCookieStore();

	// 登录用户名
	public static String TPL_username = "";
	// 登录密码
	public static String TPL_password = "";

	// 验证码输出路径
	public static String URI_VCODE = "target/vcode/taobao/{0}.jpeg";
}
