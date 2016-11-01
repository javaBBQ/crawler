/*
 *Project: glorypty-crawler
 *File: com.glorypty.jdbc.ApplicationContextHelperTest.java <2015年5月14日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @Author hardy 
 * @Date 2015年5月14日 上午10:37:09
 * @version 1.0
 */
public class ApplicationContextHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ApplicationContextHelper.insert(
				"insert into user(uname, upwd) values(?,?)",
				new Object[] {"aa", "dd"}, 
				new int[] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR });
	}

}
