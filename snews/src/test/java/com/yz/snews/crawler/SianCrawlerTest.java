package com.yz.snews.crawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yz.snews.crawler.live.SianLiveCrawler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class SianCrawlerTest {
	@Autowired
	private SianLiveCrawler sianCrawler;
	
	@Test
	public void testRun() throws Exception {
		this.sianCrawler.run();
	}
 	
}
