package com.jt.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ChannelUtils {

	public static Channel getResource(){
		
		try {
			//创建一个工厂
			ConnectionFactory factory=new ConnectionFactory();
			//配置工厂参数
			factory.setHost("192.168.194.133");
			factory.setPort(5672);
			factory.setUsername("root");
			factory.setPassword("root");
			factory.setVirtualHost("/jt");
			//获取连接
			Connection conn=factory.newConnection();
			Channel channel=conn.createChannel();
			return channel;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
