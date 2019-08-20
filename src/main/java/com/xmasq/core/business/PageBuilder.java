package com.xmasq.core.business;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.xmasq.core.common.util.NumberUtils;
import com.xmasq.core.common.util.StringUtils;

/**
 * 翻页查询构造器
 * 
 * @author guoyu.huang
 */
public class PageBuilder<T> {

	private SqlBuilder listHql;
	private SqlBuilder countHql;
	private int offset;
	private int pageSize;
	private String orderBy;
	private Map<String, Object> params;

	public PageBuilder(int offset, int pageSize, String listHql, String orderBy, String countHql,
			Map<String, Object> params) {
		this.listHql = new SqlBuilder(listHql);
		this.countHql = new SqlBuilder(countHql);
		this.params = params;
		this.orderBy = " ";
		if (StringUtils.isNotBlank(orderBy)) {
			this.orderBy += orderBy;
		}
		this.offset = offset;
		this.pageSize = pageSize;
	}

	public PageBuilder(int offset, int pageSize, String listHql, String orderBy, String countHql) {
		this.listHql = new SqlBuilder(listHql);
		this.countHql = new SqlBuilder(countHql);
		this.orderBy = " ";
		if (StringUtils.isNotBlank(orderBy)) {
			this.orderBy += orderBy;
		}
		this.offset = offset;
		this.pageSize = pageSize;
	}

	public PageBuilder<T> append(String hql, Map<String, Object> params) {
		listHql.append(hql);
		countHql.append(hql);
		if (this.params != null) {
			this.params.putAll(params);
		} else {
			this.params = params;
		}
		return this;
	}

	public PageBuilder<T> append(String hql) {
		listHql.append(hql);
		countHql.append(hql);
		return this;
	}

	public PageBuilder<T> appendList(String hql) {
		listHql.append(hql);
		return this;
	}

	public PageBuilder<T> appendCount(String hql) {
		countHql.append(hql);
		return this;
	}

	public Page<T> query(EntityManager entityManager) {

		List<T> content = find(entityManager, this.listHql.getSql() + this.orderBy, params, offset, pageSize);
		long totalRecord = findCount(entityManager, this.countHql.getSql(), this.params);
		return new PageImpl<T>(content, PageRequest.of(offset / pageSize, pageSize), totalRecord);
	}

	public String getListHql() {
		return this.listHql.getSql();
	}

	public String getCountHql() {
		return this.countHql.getSql();
	}

	@SuppressWarnings("unchecked")
	private List<T> find(EntityManager entityManager, String hql, Map<String, Object> params, int offset,
			int pageSize) {
		Query query = buildQuery(entityManager, hql, params);
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	private long findCount(EntityManager entityManager, String counHql, Map<String, Object> params) {
		Query query = buildQuery(entityManager, counHql, params);
		return NumberUtils.toLong(String.valueOf(query.getResultList().get(0)));
	}

	private Query buildQuery(EntityManager entityManager, String queryString, Map<String, Object> params) {
		Query query = entityManager.createQuery(queryString);
		if (params != null) {
			params.keySet().forEach(key -> {
				query.setParameter(key, params.get(key));
			});
		}
		return query;
	}
}
