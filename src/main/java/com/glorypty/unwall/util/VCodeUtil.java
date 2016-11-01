/*
 *Project: glorypty-crawler
 *File: com.glorypty.unwall.util.VCodeUtil.java <2016年2月26日>
 ****************************************************************
 * 版权所有@2016 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.unwall.util;

import cn.xkshow.unwall.ruokuai.api.RuoKuaiAPI;
import cn.xkshow.unwall.ruokuai.domain.RuoKuaiCreate;

import com.glorypty.unwall.UnwallConstants;

/**
 *
 * @author hardy 
 * @Date 2016年2月26日 下午12:57:22
 * @version 1.0
 */
public class VCodeUtil {
	
	/**
	 * 自动答题(题目破解)
	 * @author hardy<2016年2月26日>
	 * @param byteImage 题目图像二进制字节
	 * @return RuoKuaiCreate(result题目答案,id题目标识,其它错误信息)
	 */
	public static RuoKuaiCreate crackTopic(byte[] byteImage){
		return RuoKuaiAPI.createByPost(UnwallConstants.RUOKUAI_UNAME, UnwallConstants.RUOKUAI_PWD, "3000", "60", byteImage);
	}
	
}
