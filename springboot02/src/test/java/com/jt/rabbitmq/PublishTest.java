package com.jt.rabbitmq;

import java.io.IOException;

import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class PublishTest {

	private static final String exchange_name="publish1805";
	private static final String queue01="pqueue01";
	private static final String queue02="pqueue02";
	
	/**
	 * 生产端
	 * @throws Exception
	 */
	@Test
	public void Sender() throws Exception{
		Channel channel=ChannelUtils.getResource();
		channel.exchangeDeclare(exchange_name, "fanout");
		for(int i=0;i<100;i++){
			String msg="hello publish mode"+i;
			channel.basicPublish(exchange_name, "", null, msg.getBytes());
		}
	}
	
	/**
	 * 消费者1
	 * @throws Exception
	 */
	@Test
	public void recev01() throws Exception{
		Channel channel=ChannelUtils.getResource();
		channel.queueDeclare(queue01, false, false, false, null);
		channel.exchangeDeclare(exchange_name, "fanout");
		channel.queueBind(queue01, exchange_name, "");
		QueueingConsumer consumer=new QueueingConsumer(channel);
		channel.basicConsume(queue01, true,consumer);
		while(true){
			Delivery deliver=consumer.nextDelivery();
			String msg=new String(deliver.getBody());
			System.out.println("消费者01接收消息："+msg);
		}
	}
	
	/**
	 * 消费者2
	 * @throws Exception
	 */
	@Test
	public void recev02() throws Exception{
		Channel channel=ChannelUtils.getResource();
		channel.queueDeclare(queue02, false, false, false, null);
		channel.exchangeDeclare(exchange_name, "fanout");
		channel.queueBind(queue02, exchange_name, "");
		QueueingConsumer consumer=new QueueingConsumer(channel);
		channel.basicConsume(queue02, true,consumer);
		while(true){
			Delivery deliver=consumer.nextDelivery();
			String msg=new String(deliver.getBody());
			System.out.println("消费者02接收消息："+msg);
		}
	}
}
