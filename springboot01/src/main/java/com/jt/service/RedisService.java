package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {

	@Autowired
	private ShardedJedisPool pool;
	
	public void set(String key ,String value){
		ShardedJedis sJedis = pool.getResource();
		sJedis.set(key, value);
		pool.returnResource(sJedis);
	}
	
	public void set(String key,String value,Integer second){
		ShardedJedis sJedis=pool.getResource();
		sJedis.setex(key,second, value);
		pool.returnResource(sJedis);
		
	}
	
	public String get(String key){
		ShardedJedis sJedis = pool.getResource();
		String value=sJedis.get(key);
		pool.returnResource(sJedis);
		return value;
	}
	
	public boolean exists(String key){
		ShardedJedis sJedis=pool.getResource();
		boolean isExists=sJedis.exists(key);
		pool.returnResource(sJedis);
		return isExists;
	}
	
}
