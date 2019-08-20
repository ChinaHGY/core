package com.xmasq.core.error;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 自定义异常页面
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
public class CommonErrorPageRegistrar implements ErrorPageRegistrar {

	@Override
	public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
		ErrorPage page401 = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401");
		ErrorPage page403 = new ErrorPage(HttpStatus.FORBIDDEN, "/error/403");
		ErrorPage page404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
		ErrorPage page500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
		errorPageRegistry.addErrorPages(page401, page403, page404, page500);
	}

}
