package com.xmasq.core.business;

import java.util.Map;

/**
 * SQL语句构建器
 * 
 * @author guoyu.huang
 */
public class SqlBuilder {

	private StringBuilder sql = new StringBuilder();
	private Map<String, Object> param;

	public SqlBuilder() {
	}

	public SqlBuilder(String sql, Map<String, Object> params) {
		append(sql, params);
	}

	public SqlBuilder(String sql) {
		append(sql, null);
	}

	/**
	 * 追加语句
	 * <p>
	 * 在追加时，默认在原来的sql语句后面添加上一个空格符
	 * <p>
	 *
	 * @param sql
	 * @param params
	 */
	public void append(String sql) {
		append(sql, null);
	}

	/**
	 * 追加语句以及参数
	 * <p>
	 * 在追加时，默认在原来的sql语句后面添加上一个空格符
	 * <p>
	 *
	 * @param sql
	 * @param params
	 */
	public void append(String sql, Map<String, Object> params) {
		if (this.sql.length() > 0 && !Character.isWhitespace(this.sql.charAt(this.sql.length() - 1))) {
			this.sql.append(' ');
		}
		this.sql.append(sql);
		if (params != null && !params.isEmpty()) {
			if (param == null) {
				param = params;
			} else {
				param.putAll(params);
			}
		}
	}

	public String getSql() {
		return sql.toString();
	}

	public Map<String, Object> getParams() {
		if (this.param == null) {
			return null;
		} else {
			return this.param;
		}
	}

}
