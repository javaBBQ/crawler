/*
 *Project: glorypty-crawler
 *File: com.glorypty.jdbc.ApplicationContextCmsGoodsHelperTest.java <2016年2月22日>
 ****************************************************************
 * 版权所有@2016 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.jdbc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ZhangLujun 
 * @Date 2016年2月22日 上午11:27:05
 * @version 1.0
 */
public class ApplicationContextCmsGoodsHelperTest {

	/**
	 * @author ZhangLujun<2016年2月22日>
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
//		fail("Not yet implemented");
		String linkman="钟先生";
		String mobile = "18666895605";
		String sql = "select count(0) from CMSG_INFORMATION_TMP where linkman like'%"+linkman+"%' and mobile='"+mobile+"'";
		int num = ApplicationContextCmsGoodsHelper.query(sql);
		System.out.println(num);
	}

}
