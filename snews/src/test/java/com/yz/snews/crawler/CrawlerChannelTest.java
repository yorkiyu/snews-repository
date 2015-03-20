package com.yz.snews.crawler;

import java.math.BigInteger;
import java.util.List;

import org.apache.bcel.generic.NEW;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.yz.snews.entity.CrawlerChannel;
import com.yz.snews.entity.News;
import com.yz.snews.repository.CrawlerChannelDao;
import com.yz.snews.service.CrawlerChannelService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class CrawlerChannelTest {
	@Autowired
	private  CrawlerChannelService crawlerChannelService;
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
	@Test
	public void exe() throws Exception {
//		List<CrawlerChannel> crawlerChannels = crawlerChannelService.getAll();
//		for (CrawlerChannel crawlerChannel : crawlerChannels) {
//			System.out.println(crawlerChannel.getName()+"  "+crawlerChannel.getId());
//		}
//		CrawlerChannel crawlerChannel = crawlerChannelService.get(new BigInteger("26312303753787076449960452897"));
//		crawlerChannel.setParentId(new BigInteger("26312303772233820523670004514"));
//		crawlerChannelService.update(new BigInteger("26312303753787076449960452897"), crawlerChannel);
//		System.out.println("update down");
//		CrawlerChannel crawlerChannel = crawlerChannelService.get(new BigInteger("26312303772233820523670004514"));
//		List<BigInteger> tempList = Lists.newArrayList();
//		tempList.add(new BigInteger("26312303753787076449960452897"));
//		crawlerChannel.setChildren(tempList);
//		crawlerChannelService.update(new BigInteger("26312303772233820523670004514") , crawlerChannel);
	}
}
