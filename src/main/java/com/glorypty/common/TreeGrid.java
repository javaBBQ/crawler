/*
 *Project: glorypty-crawler
 *File: com.glorypty.common.TreeGrid.java <2015年12月23日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 树结构
 * @author ZhangLujun 
 * @Date 2015年12月23日 下午2:22:52
 * @version 1.0
 */
public class TreeGrid {
	private Long id;  //分类编号  
    private Long parentId; //分类父编号
    private String text; //分类名称  
    private Boolean checked = false; //是否选中  
    private List<TreeGrid> children; //子节点  
    private String state = "open";
    private Long orderby;
    private String href;  //菜单链接
    
    private Integer typeId;
    private String typeName;
    
    public TreeGrid() {}
    
    public void addChild(TreeGrid treeGrid){
    	if(this.children == null){
    		this.children = new ArrayList<TreeGrid>();
    	}
    	this.children.add(treeGrid);
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public List<TreeGrid> getChildren() {
		return children;
	}
	public void setChildren(List<TreeGrid> children) {
		this.children = children;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getOrderby() {
		return orderby;
	}

	public void setOrderby(Long orderby) {
		this.orderby = orderby;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
