package com.xmasq.core.business;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author guoyu.huang
 */
public class Param {

	private Map<String, Object> params = new HashMap<>();

	public Param() {

	}

	public Param(String key, Object value) {
		this.addParam(key, value);
	}

	public Param addParam(String key, Object value) {
		this.params.put(key, value);
		return this;
	}

	public Map<String, Object> param() {
		return this.params;
	}
}
