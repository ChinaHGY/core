package com.xmasq.core.base;

import lombok.Getter;
import lombok.Setter;

/**
 * AJAX请求返回内容
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
@Setter
@Getter
public class AjaxResponse {

	private static final String SUCCESS_MESSAGE = "操作成功";
	private static final String FAILURE_MESSAGE = "操作失败";

	private String message;
	private Object data;
	private boolean success;

	public AjaxResponse() {
	}

	protected AjaxResponse(boolean success) {
		this.success = success;
	}

	public static AjaxResponse success() {
		return new AjaxResponse(true).message(SUCCESS_MESSAGE);
	}

	public static AjaxResponse successMsg(String message) {
		return new AjaxResponse(true).message(message);
	}

	public static AjaxResponse successData(Object data) {
		return new AjaxResponse(true).message(SUCCESS_MESSAGE).data(data);
	}

	public static AjaxResponse success(String message, Object data) {
		return new AjaxResponse(true).message(message).data(data);
	}

	public static AjaxResponse failure() {
		return new AjaxResponse(false).message(FAILURE_MESSAGE);
	}

	public static AjaxResponse failureMsg(String message) {
		return new AjaxResponse(false).message(message);
	}

	public static AjaxResponse failureData(Object data) {
		return new AjaxResponse(false).message(SUCCESS_MESSAGE).data(data);
	}

	public static AjaxResponse failure(String message, Object data) {
		return new AjaxResponse(false).message(message).data(data);
	}

	protected AjaxResponse message(String message) {
		this.setMessage(message);
		return this;
	}

	protected AjaxResponse data(Object data) {
		this.setData(data);
		return this;
	}
}