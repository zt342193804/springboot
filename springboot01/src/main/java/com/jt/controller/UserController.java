package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.pojo.User;
import com.jt.service.RedisService;
import com.jt.service.UserService;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	//springboot和jpa整合
	@RequestMapping(value="addUser")
	@ResponseBody
	public String addUser(String name,Integer age){
		String ret=userService.addUser(name, age);
		return ret;
	}
	
	//mybatis和springboot整合
	@RequestMapping("findAllUser")
	public String findAllUser(){
		List<User> list=userService.findAllUser();
		return "test";
	}
	
	//springboot和redis整合
	@Autowired
	private RedisService redisService;
	
	@RequestMapping("testRedis")
	@ResponseBody
	public String testRedis(){
		String key="Item_key_12306";
		String value="";
		if(redisService.exists(key)){
			System.out.println("从redis获取信息...");
			value=redisService.get(key);
			System.out.println("获取的值为："+value);
		}else{
			System.out.println("从数据库获取信息...");
			value="冰箱";
			redisService.set(key, value);
			System.out.println("获取的值为："+value);
		}
		return value;
	}
	
	@Autowired
	private ShardedJedisPool pool;
	
	@RequestMapping("testRedis2")
	@ResponseBody
	public String testRedis2(){
		ShardedJedis sJedis=pool.getResource();
		String ret="";
		for(int i=0;i<100;i++){
			String key="Item_key_"+i;
			String value="";
			if(sJedis.exists(key)){
				System.out.println("从redis获取信息...");
				value=sJedis.get(key);
				System.out.println("获取的值为："+value);
			}else{
				System.out.println("从数据库获取信息...");
				value="商品_"+i;
				sJedis.set(key, value);
				System.out.println("获取的值为："+value);
			}
			ret+=value;
			
		}
		pool.returnResource(sJedis);
		
		return ret;
	}
	
}
