package com.jt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.service.UserService;

@Controller
public class DemoController {

	@RequestMapping(value="hello")
	@ResponseBody
	public String SayHello(){
		return "hello world";
	}
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("SayHello")
	@ResponseBody
	public String SayHello2(){
		return userService.SayHello();
	}
}
