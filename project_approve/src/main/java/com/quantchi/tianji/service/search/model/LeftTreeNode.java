package com.quantchi.tianji.service.search.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 导航树类
 * @author kouiz
 *
 */
public class LeftTreeNode implements Serializable {

	private static final long serialVersionUID = -498670123205012636L;

	private String id;
	
	private String name;
	
	private String field;
	
	private String parentId;
	
	private List<LeftTreeNode> children = new ArrayList<>();
	
	private Long quantity;
	

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public List<LeftTreeNode> getChildren() {
		return children;
	}
	
	public void setChildren(List<LeftTreeNode> children) {
		this.children = children;
	}
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Override
    public String toString() {
    	String str = String.format(
    			"{id=%s, " +
    			"name=%s, " + 
    			"field=%s, " +
    			"parentId=%s, " +
    			"children=%s, "+
    			"quantity=%s"+
    			"}", 
    			id, name, field, parentId, children, quantity);
    	return str;
    }
}

