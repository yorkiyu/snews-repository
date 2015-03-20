package com.yz.snews.entity;

import java.math.BigInteger;
import java.util.List;

public class CrawlerChannel extends BaseEntity{
	private String name;
	private String code;
	private String sourceUrl;
	private String crawlerClass;
	private List<BigInteger> children;
	private BigInteger parentId;
	private List<String> seed;
	private List<String> regex;
	private List<String> crawlRegex;
	private Integer crawlType;
	//抓取任务间隔时间，以分为单位
	private float crawlFrequency; 
	private Integer threads;
	private Integer depth;
	
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
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getCrawlerClass() {
		return crawlerClass;
	}
	public void setCrawlerClass(String crawlerClass) {
		this.crawlerClass = crawlerClass;
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
	public List<String> getSeed() {
		return seed;
	}
	public void setSeed(List<String> seed) {
		this.seed = seed;
	}
	public List<String> getRegex() {
		return regex;
	}
	public void setRegex(List<String> regex) {
		this.regex = regex;
	}
	public List<String> getCrawlRegex() {
		return crawlRegex;
	}
	public void setCrawlRegex(List<String> crawlRegex) {
		this.crawlRegex = crawlRegex;
	}
	public Integer getCrawlType() {
		return crawlType;
	}
	public void setCrawlType(Integer crawlType) {
		this.crawlType = crawlType;
	}
	public float getCrawlFrequency() {
		return crawlFrequency;
	}
	public void setCrawlFrequency(float crawlFrequency) {
		this.crawlFrequency = crawlFrequency;
	}
	public Integer getThreads() {
		return threads;
	}
	public void setThreads(Integer threads) {
		this.threads = threads;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
}
