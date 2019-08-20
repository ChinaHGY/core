package com.xmasq.core.annotation.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/**
 * 同{@link org.springframework.web.servlet.HandlerInterceptor}一样，增加一个参数，携带注解
 * 
 * @see org.springframework.web.servlet.HandlerInterceptor
 * @author guoyu.huang
 * @version 1.0.0
 */
public interface IInterceptor {

	/**
	 * 进入业务程序之前，先执行该程序，返回true才会执行业务程序。参考{@link org.springframework.web.servlet.HandlerInterceptor}
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param interceptor
	 * @return
	 * @throws Exception
	 */
	boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler, Interceptor interceptor)
			throws Exception;

	/**
	 * 业务程序执行后，执行该程序。参考{@link org.springframework.web.servlet.HandlerInterceptor}
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @param interceptor
	 * @throws Exception
	 */
	void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView,
			Interceptor interceptor) throws Exception;

	/**
	 * 请求结束后，执行该程序。参考{@link org.springframework.web.servlet.HandlerInterceptor}
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @param interceptor
	 * @throws Exception
	 */
	void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex,
			Interceptor interceptor) throws Exception;

}
