package com.xmasq.core.base.web;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.xmasq.core.base.AjaxResponse;
import com.xmasq.core.base.exception.BusinessException;
import com.xmasq.core.base.exception.SystemException;

/**
 * RestController基类，处理BusinessException,SystemException,Exception(按顺序先后处理)
 * 
 * <p>
 * RestController注解的页面处理异常，返回AjaxResponse.failureMsg("异常信息")
 * <p>
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
public abstract class AbstractRestController {

	@ExceptionHandler(BusinessException.class)
	public AjaxResponse handleBusinessException(BusinessException e) {
		e.printStackTrace();
		return AjaxResponse.failureMsg(e.getMessage());
	}

	@ExceptionHandler(SystemException.class)
	public AjaxResponse handleSystemException(SystemException e) {
		e.printStackTrace();
		return AjaxResponse.failureMsg(e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public AjaxResponse handleException(Exception e) {
		e.printStackTrace();
		return AjaxResponse.failureMsg(e.getMessage());
	}

}
