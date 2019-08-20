package com.xmasq.core.annotation.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 主要用于控制器的拦截器注解，支持对类，方法进行注解
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Interceptor {

	/**
	 * 要处理的拦截器注册的的beanName.
	 * 
	 * @return
	 */
	String[] value();

	/**
	 * 具体拦截器实现需要携带的参数仓库
	 * 
	 * @return
	 */
	String[] params() default {};
}
