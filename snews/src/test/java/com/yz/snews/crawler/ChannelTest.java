package com.yz.snews.crawler;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.yz.snews.entity.Channel;
import com.yz.snews.service.ChannelService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-config.xml")
public class ChannelTest {
	@Autowired
	private ChannelService channelService;
	@Test
	public void run() throws Exception {
//		List<Channel> Channels = channelService.getAll();
//		for (Channel Channel : Channels) {
//			System.out.println(Channel.getName()+"  "+Channel.getId());
//		}
//		Channel Channel = channelService.get(new BigInteger("26312537437141002202560324388"));
//		Channel.setParentId(new BigInteger("26312535869167755937248437027"));
//		channelService.update(new BigInteger("26312537437141002202560324388"), Channel);
//		System.out.println("update down");
		Channel Channel1 = channelService.get(new BigInteger("26312535869167755937248437027"));
		List<BigInteger> tempList = Lists.newArrayList();
		tempList.add(new BigInteger("26312537437141002202560324388"));
		Channel1.setChildren(tempList);
		channelService.update(new BigInteger("26312535869167755937248437027") , Channel1);
		System.out.println("update down");
	}
}
