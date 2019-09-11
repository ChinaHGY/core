package com.xmasq.core.base.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 基础持久类
 * @author guoyu.huang
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	/**
	 * 返回EntityManager，用于直接在接口中调用SQL语句。
	 * 
	 * 注意：如果返回为null，则应该在@SpringBootApplication的类上添加注解@EnableJpaRepositories(repositoryFactoryBeanClass
	 * = BaseRepositoryFactoryBean.class)
	 * 
	 * @return
	 */
	EntityManager getEntityManager();

	/**
	 * 批量保存
	 * 
	 * @param list
	 * @param batchSize
	 */
	default void saveAllInBatch(List<T> list, int batchSize) {
		for (int i = 0; i < list.size(); i++) {
			getEntityManager().persist(list.get(i));
			if (i % batchSize == 0 || i == list.size() - 1) {
				getEntityManager().flush();
				getEntityManager().clear();
			}
		}
	}

	/**
	 * 批量保存，默认为1000条提交一次
	 * 
	 * @param list
	 */
	default void saveAllInBatch(List<T> list) {
		saveAllInBatch(list, 1000);
	}
}
