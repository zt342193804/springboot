package com.et;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JspController {

	@RequestMapping(value="/testJsp")
	public String TestJsp(){
		return "test";
	}
}
