package com.xmasq.core.business.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.xmasq.core.business.Param;

/**
 * 业务模块持久类封装，如果是单一模块使用{@link com.xmasq.core.base.repository.BaseRepository}，
 * 如果需要使用到复杂的查询可以使用{@link org.springframework.data.jpa.repository.JpaSpecificationExecutor}
 * 
 * 
 * @author guoyu.huang
 */
public abstract class AbstractBusinessRepository {

	private final static String WHERE = " where ";
	private final static String AND = " and ";
	private final static int QUERY_INVALID_MAX_NUMBER = -1;

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 添加查询参数
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	protected Param addParam(String key, Object value) {
		return new Param(key, value);
	}

	/**
	 * 根据已有的SQL语句，返回where或者and
	 *
	 * @param sql
	 * @return " and "或者 " where "
	 */
	protected String getConditionKey(String sql) {
		return sql.toLowerCase().contains(WHERE) ? AND : WHERE;
	}

	/**
	 * 翻页查询数据
	 *
	 * @param queryString
	 * @param params
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> find(String queryString, Map<String, Object> params, int offset, int pageSize) {
		Query query = buildQuery(queryString, params);
		if (offset > QUERY_INVALID_MAX_NUMBER) {
			query.setFirstResult(offset);
		}
		if (pageSize > QUERY_INVALID_MAX_NUMBER) {
			query.setMaxResults(pageSize);
		}
		return (List<T>) query.getResultList();
	}

	/**
	 * 查询列表
	 * @param queryString
	 * @param params
	 * @param <T>
	 * @return
	 */
	public <T> List<T> find(String queryString, Map<String, Object> params) {
		return find(queryString, params, QUERY_INVALID_MAX_NUMBER, QUERY_INVALID_MAX_NUMBER);
	}

	/**
	 * 查询返回数字结果，支持IN函数
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	public <T> T findUniqueResult(String hql, Map<String, Object> params) {
		return getUniqueResult(find(hql, params));
	}

	private Query buildQuery(String queryString, Map<String, Object> params) {
		Query query = this.entityManager.createQuery(queryString);
		if (params != null) {
			params.keySet().forEach(key -> {
				query.setParameter(key, params.get(key));
			});
		}
		return query;
	}

	private <T> T getUniqueResult(List<T> results) {
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new NonUniqueResultException("result returned more than one element, returnedSize=" + results.size());
		} else {
			return results.get(0);
		}
	}
}
