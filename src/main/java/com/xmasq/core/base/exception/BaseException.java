package com.xmasq.core.base.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 基础异常类
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
@Setter
@Getter
public class BaseException extends RuntimeException {

	private String code;

	public BaseException() {
		super();
	}

	public BaseException(String msg) {
		super(msg);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public BaseException(String code, String msg, Throwable cause) {
		super(msg, cause);
		this.code = code;
	}
}
