package com.yz.snews.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import com.yz.snews.entity.Channel;

@Service
public class ChannelService extends BaseService<Channel, BigInteger>{
	public Channel findByCode(String code){
		return this.getOne("code", code);
	}
}
