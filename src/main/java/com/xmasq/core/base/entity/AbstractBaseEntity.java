package com.xmasq.core.base.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

/**
 * 实体基类
 *
 * @author guoyu.huang
 * @version 1.0.0
 */
@Setter
@Getter
@MappedSuperclass
public abstract class AbstractBaseEntity {

	/**
	 * ID,自动生成策略
	 */
	@Id
	@Column(name = "id", columnDefinition = "char(32) comment '主键'")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@GeneratedValue(generator = "paymentableGenerator")
	protected String id;

}