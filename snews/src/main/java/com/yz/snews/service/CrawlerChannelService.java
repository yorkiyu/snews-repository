package com.yz.snews.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.yz.snews.entity.CrawlerChannel;
import com.yz.snews.util.SearchFilter2;
import com.yz.snews.util.SearchFilter2.Operator;

@Service
public class CrawlerChannelService extends BaseService<CrawlerChannel, BigInteger>{
	
	public List<CrawlerChannel> findByCrawlTypeParent(int crawlType) {
		Map<String,SearchFilter2> filters = Maps.newHashMap();
		filters.put("crawlType", new SearchFilter2("crawlType",Operator.EQ, crawlType));
		filters.put("children", new SearchFilter2("children",Operator.NE, null));
		return findBySearchFilter(filters);
	}
	public List<CrawlerChannel> findCurCrawlerChannels(int crawlType,float crawlFrequency,List<BigInteger> children) {
		Map<String,SearchFilter2> filters = Maps.newHashMap();
		filters.put("sourceType", new SearchFilter2("crawlFrequency",Operator.EQ, crawlFrequency));
		filters.put("crawlType", new SearchFilter2("crawlType",Operator.EQ, crawlType));
		filters.put("id", new SearchFilter2("id",Operator.IN, children));
		return findBySearchFilter(filters);
	}
}
