package com.yz.snews.crawler;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yz.snews.entity.CrawlerChannel;
import com.yz.snews.service.NewsService;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.net.Proxys;


@Component
public abstract class BaseCrawler extends DeepCrawler implements TaskCrawler{
	public CrawlerChannel curCrawlerChannel;
	@Autowired
	private NewsService newsService;
	
	public BaseCrawler(String crawlDbPath){
		super(crawlDbPath);
	}
	
	@Override
	public void run() throws Exception {
		// 判断是否是第一次抓取,并添加种子
		for (String tempSeed : curCrawlerChannel.getSeed()) {
			this.addSeed(tempSeed);
		}
		/* 2.x版本直接支持多代理随机切换 */
        Proxys proxys = new Proxys();
	    /*
	         可用代理可以到 http://www.brieftools.info/proxy/ 获取
	         添加代理的方式:
	         1)ip和端口
	         proxys.add("123.123.123.123",8080);
	         2)文件
	         proxys.addAllFromFile(new File("xxx.txt"));
	         文件内容类似:
	         123.123.123.123:90
	         234.234.324.234:8080
	         一个代理占一行
	    */
        this.setProxys(proxys);
        this.setThreads(curCrawlerChannel.getThreads());
        this.setResumable(false);
        this.start(curCrawlerChannel.getDepth());
    }
	@Override
	public void setCurCrawlerChannel(CrawlerChannel curCrawlerChannel) {
		this.curCrawlerChannel = curCrawlerChannel;
	}
	
}
