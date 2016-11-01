/*
 *Project: glorypty-crawler
 *File: com.glorypty.unwall.Hc360Test.java <2016年2月22日>
 ****************************************************************
 * 版权所有@2016 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.unwall;

import java.io.IOException;

import org.apache.http.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.glorypty.unwall.hc360.HcConstants;
import com.glorypty.unwall.hc360.HcValidata;

/**
 *
 * @author hardy 
 * @Date 2016年2月22日 下午2:53:12
 * @version 1.0
 */
public class Hc360Test {

	@Before
	public void setUp() throws Exception {		
		HcConstants.accID = "l1192821822"; 
		HcConstants.accPWD = "hc2015";  	
		
		UnwallConstants.VCODE_AUTO = true;
	}
	
	@Test
	public void test() throws ParseException, IOException {		
		HcValidata.detail("http://b2b.hc360.com/buy/425840361.html");
		HcValidata.detail("http://b2b.hc360.com/buy/526914106.html");
		HcValidata.detail("http://b2b.hc360.com/buy/526914106.html");
		HcValidata.detail("http://b2b.hc360.com/buy/425840361.html");
				
		HcValidata.detail("http://b2b.hc360.com/buy/240426401.html");
		HcValidata.detail("http://b2b.hc360.com/buy/425840293.html");
		HcValidata.detail("http://b2b.hc360.com/buy/425839915.html");
		HcValidata.detail("http://b2b.hc360.com/buy/425839832.html");
		HcValidata.detail("http://b2b.hc360.com/buy/425839840.html");
		
		HcValidata.sc.close();
	}

}
