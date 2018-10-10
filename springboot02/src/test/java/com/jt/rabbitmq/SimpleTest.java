package com.jt.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class SimpleTest {

	/**
	 * 生产者
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	@Test
	public void Sender() throws IOException, TimeoutException{
		//创建一个工厂
		ConnectionFactory factory=new ConnectionFactory();
		//连接工厂配置参数
		factory.setHost("192.168.194.133");
		factory.setPort(5672);
		factory.setUsername("root");
		factory.setPassword("root");
		factory.setVirtualHost("/jt");
		//获取连接
		Connection conn=factory.newConnection();
		//获取信道对象短连接
		Channel channel=conn.createChannel();
		//利用短连接信息对象来操作rabbitmq
		String queue_name="simple";
		channel.queueDeclare(queue_name, false, false, false, null);
		//发送消息，利用channel发送，发送给默认交换机
		String msg="hello simple model";
		channel.basicPublish("", queue_name, null, msg.getBytes());
		channel.close();
		conn.close();
		
	}
	
	/**
	 * 消费者
	 */
	@Test
	public void receiver(){
		
		try {
			Channel channel=ChannelUtils.getResource();
			String queue_name="simple";
			channel.queueDeclare(queue_name, false, false, false, null);
			QueueingConsumer consumer=new QueueingConsumer(channel);
			channel.basicConsume(queue_name, true,consumer);
			
			while(true){
				Delivery delivery=consumer.nextDelivery();
				String msg=new String(delivery.getBody());
				System.out.println("消费者消息："+msg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
