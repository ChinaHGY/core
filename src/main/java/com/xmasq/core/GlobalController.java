package com.xmasq.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 默认全局控制器
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
@Controller
public class GlobalController {

	@RequestMapping("/error/{code}")
	public String error(@PathVariable(value = "code") String code) {
		return code;
	}

	@RequestMapping("/home")
	public String home() {
		return "home";
	}
}
