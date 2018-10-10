package com.jt.rabbitmq;

import java.io.IOException;

import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class WorkerTest {

	private static final String queue_name="work";
	
	/**
	 * 生产者
	 * @throws Exception
	 */
	@Test
	public void Sender() throws Exception{
		Channel channel=ChannelUtils.getResource();
		channel.queueDeclareNoWait(queue_name, false, false, false, null);
		String msg="hello work mode";
		for(int i=0;i<100;i++){
			String msgDelive=msg+i;
			channel.basicPublish("", queue_name, null, msgDelive.getBytes());
			System.out.println("生产者发送消息："+i+"条");
		}
		channel.close();
	};
	
	/**
	 * 消费者1 
	 * @throws Exception 
	 */
	@Test
	public void recv1() throws Exception{
		Channel channel=ChannelUtils.getResource();
		channel.queueDeclare(queue_name,false,false,false,null);
		channel.basicQos(1);
		QueueingConsumer consumer=new QueueingConsumer(channel);
		channel.basicConsume(queue_name, false,consumer);
		while(true){
			Delivery delivery=consumer.nextDelivery();
			String msg=new String(delivery.getBody());
			System.out.println("消费者1接收消息："+msg);
			Thread.sleep(50);
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	};
	
	/**
	 * 消费者2 
	 * @throws Exception 
	 */
	@Test
	public void recv2() throws Exception{
		Channel channel=ChannelUtils.getResource();
		channel.queueDeclare(queue_name,false,false,false,null);
		channel.basicQos(1);
		QueueingConsumer consumer=new QueueingConsumer(channel);
		channel.basicConsume(queue_name, false,consumer);
		while(true){
			Delivery delivery=consumer.nextDelivery();
			String msg=new String(delivery.getBody());
			System.out.println("消费者2接收消息："+msg);
			Thread.sleep(10);
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	};
}
