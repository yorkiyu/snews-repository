package com.yz.snews.crawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yz.snews.task.LiveCrawlerTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class LiveCrawlerTaskTest {
	@Autowired
	private LiveCrawlerTask liveCrawlerTask;
	@Test
	public void run() throws Exception {
		liveCrawlerTask.exeCrawl();
	}
}
