package com.xmasq.core.base.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.xmasq.core.base.entity.AbstractAttribute;

/**
 * 额外属性持久类
 *
 * @author guoyu.huang
 * @version 1.0.0
 */
@NoRepositoryBean
public interface BaseAttributeRepository<T extends AbstractAttribute<?>> extends BaseRepository<T, String> {

	/**
	 * 通过owner编号和属性code进行查询
	 * 
	 * @param ownerId
	 * @param code
	 * @return
	 */
	T findByOwnerIdAndCode(@Param(value = "owner.id") String ownerId, @Param(value = "code") String code);

	/**
	 * 通过属性code查询属性数量
	 * 
	 * @param code
	 * @return
	 */
	int findCountByCode(String code);

	/**
	 * 通过owner编号和属性code删除属性
	 * 
	 * @param ownerId
	 * @param code
	 */
	@Modifying
	@Query("delete from #{#entityName} t where t.owner.id = :ownerId and t.code = :code")
	void deleteByInfo(@Param(value = "ownerId") String ownerId, @Param(value = "code") String code);

	/**
	 * 通过owner编号删除属性
	 * 
	 * @param ownerId
	 */
	@Modifying
	@Query("delete from #{#entityName} t where t.owner.id = :ownerId")
	void deleteByOwner(@Param(value = "ownerId") String ownerId);

}
