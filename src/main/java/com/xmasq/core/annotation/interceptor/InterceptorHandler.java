package com.xmasq.core.annotation.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.xmasq.core.AppContext;
import com.xmasq.core.annotation.BaseAnnotationHandler;

/**
 * 为了方便使用拦截器，针对{@code Interceptor}注解做了封装，使用拦截器只要实现{@code IIterceptor}接口即可
 * 
 * @see com.xmasq.core.annotation.interceptor.Interceptor
 * @see com.xmasq.core.annotation.interceptor.IInterceptor
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
public class InterceptorHandler extends BaseAnnotationHandler<Interceptor> {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Interceptor interceptor = super.getAnnotation(handler, Interceptor.class);
		if (interceptor != null) {
			boolean result = true;
			String[] interceptorNames = interceptor.value();
			for (String interceptorName : interceptorNames) {
				if (result) {
					IInterceptor interceptorImp = (IInterceptor) AppContext.getBeanByName(interceptorName);
					result = interceptorImp.preHandle(request, response, handler, interceptor);
				} else {
					return result;
				}
			}
			return result;
		} else {
			return true;
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Interceptor interceptor = super.getAnnotation(handler, Interceptor.class);
		if (interceptor != null) {
			String[] interceptorNames = interceptor.value();
			for (String interceptorName : interceptorNames) {
				IInterceptor annotationInterceptor = (IInterceptor) AppContext.getBeanByName(interceptorName);
				annotationInterceptor.afterCompletion(request, response, handler, ex, interceptor);
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Interceptor interceptor = super.getAnnotation(handler, Interceptor.class);
		if (interceptor != null) {
			String[] interceptorNames = interceptor.value();
			for (String interceptorName : interceptorNames) {
				IInterceptor annotationInterceptor = (IInterceptor) AppContext.getBeanByName(interceptorName);
				annotationInterceptor.postHandle(request, response, handler, modelAndView, interceptor);
			}
		}
	}
}