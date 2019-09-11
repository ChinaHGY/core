package com.xmasq.core.base.exception;

/**
 * 系统异常，主要用于非业务异常场景
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
public class SystemException extends BaseException {

	/**
	 * 错误代码前缀，取首字母
	 */
	private static final String CODE_PREFIX = "SE";

	public SystemException(String code, String msg) {
		super(CODE_PREFIX + code, msg);
	}

	public SystemException(String code, String msg, Throwable cause) {
		super(CODE_PREFIX + code, msg, cause);
	}

}
