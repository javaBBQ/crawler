/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.common.PageParam.java <2015年4月2日>
 ****************************************************************
 * 版权所有@2015 国裕秀网络科技  保留所有权利.
 ***************************************************************/ 
package com.glorypty.crawler.common;

/**
 * 简单分页组件
 * @Author hardy(admin@xkshow.cn)
 * @Date 2015年4月2日 下午10:48:59
 */
public class PageParam {
	/**每页行数*/
	int perpage = 10;
	/**数据集总数*/
	long total = -1L;
	
	/**结果：页码总数*/
	int pageTotal = 1;

	public PageParam(int perpage){
		this.perpage = perpage;
	}
	
	public PageParam(int perpage, long total){
		this.perpage = perpage;
		this.total = total;
	}
	
	/**
	 * 分页组件计算
	 *  页码数、当前页数据索引
	 * @param 每页行数(perpage),数据总数(dataTotal)
	 * @Author hardy
	 * 2015年1月18日 下午11:42:05
	 */
	public void executeCalculate() {
		this.pageTotal = (int) Math.ceil((this.total + this.perpage - 1L)
				/ this.perpage);
	}

	public int getPerpage() {
		return perpage;
	}

	public void setPerpage(int perpage) {
		this.perpage = perpage;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
	
	
}
