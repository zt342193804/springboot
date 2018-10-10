package com.jt.conf;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

	@Bean
	public Queue getQueue01(){
		return new Queue("itemUpdateQueue");
	}
	
	@Bean
	public Queue getQueue02(){
		return new Queue("itemAddQueue");
	}
	
	@Bean
	public DirectExchange getExchange(){
		return new DirectExchange("directSpringBoot");
	};
	
	@Bean
	public Binding binding01(){
		return BindingBuilder.bind(getQueue01())
				.to(getExchange()).with("item.update");
	}
	
	@Bean
	public Binding binding02(){
		return BindingBuilder.bind(getQueue02())
				.to(getExchange()).with("item.add");
	}
}
