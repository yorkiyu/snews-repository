package com.yz.snews.entity;

import java.math.BigInteger;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class News extends BaseEntity{
	private String title;
	private String contentPath;
	private Integer type;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date editTime;
	private BigInteger channelId;
	private String spicture;
	private String mpicture;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContentPath() {
		return contentPath;
	}
	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getEditTime() {
		return editTime;
	}
	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
	public BigInteger getChannelId() {
		return channelId;
	}
	public void setChannelId(BigInteger channelId) {
		this.channelId = channelId;
	}
	public String getSpicture() {
		return spicture;
	}
	public void setSpicture(String spicture) {
		this.spicture = spicture;
	}
	public String getMpicture() {
		return mpicture;
	}
	public void setMpicture(String mpicture) {
		this.mpicture = mpicture;
	}
	
}
