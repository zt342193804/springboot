package com.jt.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

@Configuration
public class RedisCofig {

	@Value("${spring.redis.nodes}")
	private String nodes;
	@Value("${spring.redis.pool.maxIdle}")
	private Integer maxIdle;
	@Value("${spring.redis.pool.minIdle}")
	private Integer minIdle;
	@Value("${spring.redis.pool.minIdle}")
	private Integer maxTotal;
	@Value("${spring.redis.pool.maxWait}")
	private Integer maxWait;
	
	//配置一个config
	private JedisPoolConfig getConfig(){
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		config.setMaxTotal(maxTotal);
		config.setMaxWaitMillis(maxWait);
		return config;
	}
	
	@Bean
	public ShardedJedisPool getPool(){
		String[] nodeList=nodes.split(",");
		List<JedisShardInfo> list=new ArrayList<JedisShardInfo>();
		
		for(String node:nodeList){
			String[] hostAndPort=node.split(":");
			int port=Integer.parseInt(hostAndPort[1]);
			JedisShardInfo jedis=new JedisShardInfo(hostAndPort[0],port);
			list.add(jedis);
		
		}
		ShardedJedisPool pool=new ShardedJedisPool(getConfig(),list);
		return pool;
	}
	
}
