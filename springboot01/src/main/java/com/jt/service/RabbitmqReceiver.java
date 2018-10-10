package com.jt.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqReceiver {

	@RabbitListener(queues="itemUpdateQueue")
	public void proces01(String msg){
		System.out.println("更新端收到的消息："+msg);
	}
	
	@RabbitListener(queues="itemAddQueue")
	public void proces02(String msg){
		System.out.println("新增端收到的消息："+msg);
	}
}
