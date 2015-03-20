package com.yz.snews.entity;

import java.math.BigInteger;
import java.util.List;

public class Channel extends BaseEntity{
	private String name;
	private String code;
	private Integer type;
	private List<BigInteger> children;
	private BigInteger parentId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<BigInteger> getChildren() {
		return children;
	}
	public void setChildren(List<BigInteger> children) {
		this.children = children;
	}
	public BigInteger getParentId() {
		return parentId;
	}
	public void setParentId(BigInteger parentId) {
		this.parentId = parentId;
	}
	
}
