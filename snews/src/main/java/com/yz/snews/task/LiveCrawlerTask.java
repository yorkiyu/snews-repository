package com.yz.snews.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.yz.snews.crawler.TaskCrawler;
import com.yz.snews.entity.CrawlerChannel;
import com.yz.snews.service.CrawlerChannelService;
import com.yz.snews.util.Constants;
import com.yz.snews.util.SpringContextHolder;

public class LiveCrawlerTask {
	private static Logger logger = LoggerFactory.getLogger(LiveCrawlerTask.class);
	@Autowired
	private CrawlerChannelService crawlerChannelService;
	
	private TaskCrawler taskCrawler;
	
	@Scheduled(cron="0 0/1 * * * ?")
	public void exeCrawl() throws Exception{
		logger.info("crawl task start {}", new Date());
		List<CrawlerChannel> parentCrawlerChannels = crawlerChannelService.findByCrawlTypeParent(Constants.CRAWLER_ON);
		for (CrawlerChannel parentCrawlerChannel : parentCrawlerChannels) {
			List<CrawlerChannel> curCrawlerChannels =crawlerChannelService.findCurCrawlerChannels(Constants.CRAWLER_ON, 1, parentCrawlerChannel.getChildren());
			for (CrawlerChannel curCrawlerChannel : curCrawlerChannels) {
				taskCrawler = SpringContextHolder.getBean(curCrawlerChannel.getCrawlerClass());
				taskCrawler.setCurCrawlerChannel(curCrawlerChannel);
				taskCrawler.run();
			}
		}
	}
}
