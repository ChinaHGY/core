package com.xmasq.core.base.web;

import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.xmasq.core.base.exception.BusinessException;
import com.xmasq.core.base.exception.SystemException;

/**
 * Controller基类，处理BusinessException,SystemException,Exception(按顺序先后处理)
 * <p>
 * Controller注解的页面处理异常，返回页面
 * <p>
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
public abstract class AbstractController {

	@ExceptionHandler(BusinessException.class)
	public ModelAndView handleBusinessException(BusinessException e, Map<String, Object> model) {
		e.printStackTrace();
		return new ModelAndView("500").addObject("message", e.getMessage());
	}

	@ExceptionHandler(SystemException.class)
	public ModelAndView handleSystemException(BusinessException e, Map<String, Object> model) {
		e.printStackTrace();
		return new ModelAndView("500").addObject("message", e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception e, Map<String, Object> model) {
		e.printStackTrace();
		return new ModelAndView("500").addObject("message", e.getMessage());
	}
}
