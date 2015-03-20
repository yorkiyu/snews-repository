package com.yz.snews.crawler;


import com.yz.snews.entity.CrawlerChannel;

public interface TaskCrawler {
	public void run() throws Exception;
	public void setCurCrawlerChannel(CrawlerChannel curCrawlerChannel);
}
