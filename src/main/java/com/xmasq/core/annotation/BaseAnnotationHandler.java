package com.xmasq.core.annotation;

import java.lang.annotation.Annotation;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 注解处理器的简单封装，可以简单获取指定注解
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
public abstract class BaseAnnotationHandler<T extends Annotation> extends HandlerInterceptorAdapter {

	/**
	 * 获取指定注解
	 * 
	 * @param handler
	 * @return
	 */
	public T getAnnotation(Object handler, Class<T> annotationClass) {
		if (handler instanceof HandlerMethod) {
			T annotation = null;
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			annotation = handlerMethod.getMethodAnnotation(annotationClass);
			if (annotation == null) {
				annotation = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), annotationClass);
			}
			return annotation;
		} else {
			return AnnotationUtils.findAnnotation(ClassUtils.getUserClass(handler), annotationClass);
		}
	}

}
