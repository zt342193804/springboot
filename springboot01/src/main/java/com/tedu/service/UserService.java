package com.tedu.service;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class UserService {


	public String SayHello(){
		return "hello other";
	}
}
