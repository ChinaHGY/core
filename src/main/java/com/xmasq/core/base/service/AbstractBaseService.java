package com.xmasq.core.base.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.xmasq.core.base.entity.AbstractBaseEntity;
import com.xmasq.core.base.repository.BaseRepository;

/**
 * 
 * @author guoyu.huang
 */
public abstract class AbstractBaseService<T extends AbstractBaseEntity, ID extends Serializable, DAO extends BaseRepository<T, ID>> {

	/**
	 * 返回当前实体的持久类
	 *
	 * @return
	 */
	protected abstract DAO getRepository();

	@Transactional(rollbackFor = Exception.class)
	public T saveOrUpdate(T entity) {
		return getRepository().save(entity);
	}

	/**
	 * 多条记录保存，底层使用for循环，单条保存
	 * 
	 * @param entities
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public <S extends T> List<S> saveAll(Iterable<S> entities) {
		return getRepository().saveAll(entities);
	}

	/**
	 * 该方法删除前，会先执行查询
	 * 
	 * @param id
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteById(ID id) {
		getRepository().deleteById(id);
	}

	/**
	 * 翻页查询
	 * 
	 * @param pageable
	 *            可以使用{@link com.xmasq.core.common.PageableBuilder.build}
	 * @return
	 */
	public Page<T> page(Pageable pageable) {
		return getRepository().findAll(pageable);
	}

	/**
	 * 查询所有记录
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return getRepository().findAll();
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	public List<T> findAllById(Iterable<ID> ids) {
		return getRepository().findAllById(ids);
	}

	/**
	 * 通过ID查询记录
	 * 
	 * @param id
	 * @return 不存在则返回null
	 */
	public T findById(ID id) {
		return getRepository().findById(id).orElse(null);
	}

}
