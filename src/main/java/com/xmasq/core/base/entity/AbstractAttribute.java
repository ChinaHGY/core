package com.xmasq.core.base.entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

/**
 * 属性，主要功能是针对具体一条记录（OWNER），增加额外属性
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
@Setter
@Getter
@MappedSuperclass
public abstract class AbstractAttribute<OWNER extends AbstractBaseEntity> extends AbstractBaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", columnDefinition = "varchar(32) comment '关联记录ID'")
	private OWNER owner;

	@Column(name = "code", columnDefinition = "varchar(255) comment '属性码'")
	private String code;

	@Column(name = "value", columnDefinition = "varchar(255) comment '属性值'")
	private String value;

}
