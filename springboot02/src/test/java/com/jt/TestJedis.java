package com.jt;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.omg.CORBA.INTERNAL;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class TestJedis {

	//测试jedis链接redis
	@Test
	public void testLinkRedis(){
		Jedis jedis=new Jedis("192.168.194.133",6379);
		//设置保护模式的密码
		jedis.auth("123456");
		jedis.set("test", "linkRedis");
		System.out.println(jedis.get("test"));
	}
	
	//模拟缓存逻辑在系统中的步骤
	@Test
	public void testRedisCache(){
		String key = "Item12306";
		String value="";
		Jedis jedis=new Jedis("192.168.194.133",6379);
		if(jedis.exists(key)){
			System.out.println("商品key被缓存拦截，从缓存获取商品信息...");
			value=jedis.get(key);
			System.out.println("缓存获取到的信息："+value);
		}else{
			System.out.println("商品key未被缓存拦截，从而从数据库获取信息...");
			value="冰箱";
			System.out.println("从数据获取到的商品信息："+value);
			jedis.set("Item123", value);
		}
	}
	
	//实现自定义的数据分片计算
	@Test
	public void testDataDevide(){
		Jedis jedis79=new Jedis("192.168.194.133",6379);
		Jedis jedis80=new Jedis("192.168.194.133",6380);
		Jedis jedis81=new Jedis("192.168.194.133",6381);
		
		for(int i=0;i<100;i++){
			String key="itm_key_"+i;
			String value="item_value_"+i;
			
			if(i<30){
				jedis79.set(key, value);
			}else if(i<60){
				jedis80.set(key, value);
			}else if(i<100){
				jedis81.set(key, value);
			}else{
				jedis79.set(key, value);
			}
		}
	}
	
	//通过hash计算数据分片
	@Test
	public void testDataDevideByHash(){
		Jedis jedis79=new Jedis("192.168.194.133",6379);
		Jedis jedis80=new Jedis("192.168.194.133",6380);
		Jedis jedis81=new Jedis("192.168.194.133",6381);
		
		for(int i=0;i<100;i++){
			String key="itm_key_"+i;
			String value="item_value_"+i;
			int retNum=((key.hashCode())&Integer.MAX_VALUE)%3;
			if(retNum==0){
				jedis79.set(key, value);
			}else if(retNum==1){
				jedis80.set(key, value);
			}else{
				jedis81.set(key, value);
			}
		}
	}
	
	    //redis的节点收集
		@Test
		public void testShardedJedis(){
			List<JedisShardInfo> infoList=new ArrayList<JedisShardInfo>();
			
			JedisShardInfo jedis79=new JedisShardInfo("192.168.194.133",6379);
			JedisShardInfo jedis80=new JedisShardInfo("192.168.194.133",6380);
			JedisShardInfo jedis81=new JedisShardInfo("192.168.194.133",6381);
			
			infoList.add(jedis79);
			infoList.add(jedis80);
			infoList.add(jedis81);
			
			ShardedJedis sJedis=new ShardedJedis(infoList);
			
			for(int i=0;i<100;i++){
				String key="itm_key_"+i;
				String value="item_value_"+i;
				sJedis.set(key, value);
			}
			sJedis.close();
		}
		
		//redis的线程池管理
		@Test
		public void testRedisPool(){
			List<JedisShardInfo> infoList=new ArrayList<JedisShardInfo>();
			
			JedisShardInfo info79=new JedisShardInfo("192.168.194.133",6379);
			JedisShardInfo info80=new JedisShardInfo("192.168.194.133",6380);
			JedisShardInfo info81=new JedisShardInfo("192.168.194.133",6381);
			
			infoList.add(info79);
			infoList.add(info80);
			infoList.add(info81);
			
			JedisPoolConfig config=new JedisPoolConfig();
			config.setMaxIdle(8);
			config.setMaxTotal(100);
			
			ShardedJedisPool pool=new ShardedJedisPool(config,infoList);
			
			ShardedJedis sJedis=pool.getResource();
			sJedis.set("test01", "value01");
			String value=sJedis.get("test01");
			System.out.println(value);
			pool.returnResource(sJedis);
		}
}
