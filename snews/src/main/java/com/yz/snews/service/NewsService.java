package com.yz.snews.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.yz.snews.entity.News;
import com.yz.snews.util.SearchFilter2;
import com.yz.snews.util.SearchFilter2.Operator;

@Service
public class NewsService extends BaseService<News, BigInteger>{
	
	public  List<News> findByCrawlerChannelId(BigInteger channelId) {
		Map<String,SearchFilter2> filters = Maps.newHashMap();
		filters.put("channelId", new SearchFilter2("channelId",Operator.EQ, channelId));
		return findBySearchFilter(filters);
	}
	public boolean isExitByChannelIdAndEditTime(BigInteger channelId,Date editTime){
		  Map<String,SearchFilter2> filters = Maps.newHashMap();
		  filters.put("channelId", new SearchFilter2("channelId",Operator.EQ, channelId));
		  filters.put("editTime", new SearchFilter2("editTime",Operator.EQ, editTime));
		  return !findBySearchFilter(filters).isEmpty();
	}
}
