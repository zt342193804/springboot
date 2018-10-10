package com.jt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.dao.UserDaoIntef;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

import redis.clients.jedis.ShardedJedisPool;

@Service
public class UserService {

	@Autowired
	UserDaoIntef userDao;
	@Autowired
	private ShardedJedisPool shardedJedisPool;
	
	public String  addUser(String name,Integer age){
		User user=new User();
		user.setName(name);
		user.setAge(age);
		userDao.save(user);
		return "success";
	}
	
	//mybatis和springboot整合，查询数据库数据
	@Autowired
	private UserMapper userMapper;
	public List<User> findAllUser(){
		return userMapper.findAllUser();
	}
}
