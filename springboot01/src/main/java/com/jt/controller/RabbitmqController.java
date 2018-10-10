package com.jt.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RabbitmqController {

	@Autowired
	private RabbitTemplate rabbitmq;
	
	@RequestMapping("send")
	@ResponseBody
	public String sendMsg(String msg){
		rabbitmq.convertAndSend("directSpringBoot","item.add",msg);
		return "success";
	}
}
