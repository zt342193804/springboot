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

public class test {
	
	private static final String exchange_name="public";
	private static final String queue01="queue1";
	private static final String queue02="queue2";

	@Test
	public void sender() throws IOException, TimeoutException{
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost("192.168.194.133");
		factory.setPort(5672);
		factory.setUsername("root");
		factory.setPassword("root");
		factory.setVirtualHost("/jt");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		
//		channel.queueDeclareNoWait(exchange_name, false, false, false, null);
		channel.exchangeDeclare(exchange_name, "fanout");
		String msg="public model";
		for(int i=0;i<100;i++){
			String deliveryMsg=msg+" "+i;
			channel.basicPublish(exchange_name,"",null,deliveryMsg.getBytes());
			System.out.println("生产者生成："+i+" 条消息");
		}
		
		channel.close();
//		conn.close();
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void consumer1() throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost("192.168.194.133");
		factory.setPort(5672);
		factory.setUsername("root");
		factory.setPassword("root");
		factory.setVirtualHost("/jt");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(queue01, false, false, false, null);
		channel.exchangeDeclare(exchange_name, "fanout");
		channel.queueBind(queue01, exchange_name, "");
	    QueueingConsumer consumer = new QueueingConsumer(channel);
	    channel.basicConsume(queue01, true, consumer);
	    while(true){
	    	Delivery delivery = consumer.nextDelivery();
	    	System.out.println("消费1："+new String(delivery.getBody()));
//	    	Thread.sleep(50);
//	    	channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	    }
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void consumer2() throws Exception{
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost("192.168.194.133");
		factory.setPort(5672);
		factory.setUsername("root");
		factory.setPassword("root");
		factory.setVirtualHost("/jt");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(queue02, false, false, false, null);
		channel.exchangeDeclare(exchange_name, "fanout");
		channel.queueBind(queue02, exchange_name, "");
//		channel.basicQos(1);
	    QueueingConsumer consumer = new QueueingConsumer(channel);
	    channel.basicConsume(queue02, true, consumer);
	    
	    while(true){
	    	Delivery delivery = consumer.nextDelivery();
	    	System.out.println("消费2："+new String(delivery.getBody()));
//	    	
	    }
	}
}
