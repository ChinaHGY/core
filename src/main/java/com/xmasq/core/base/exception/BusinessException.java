package com.xmasq.core.base.exception;

/**
 * 业务异常，主要用于在业务场景中
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
public class BusinessException extends BaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * 错误代码前缀，取首字母
	 */
	private static final String CODE_PREFIX = "BE";

	public BusinessException() {
		super();
	}

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String code, String msg) {
		super(CODE_PREFIX + code, msg);
	}

	public BusinessException(String code, String msg, Throwable cause) {
		super(CODE_PREFIX + code, msg, cause);
	}
}
