package com.xmasq.core.base.entity;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xmasq.core.common.StatusEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务实体的基类
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
@Setter
@Getter
@MappedSuperclass
public abstract class AbstractBusinessEntity extends AbstractBaseEntity {

	/**
	 * 状态
	 */
	@Column(name = "status", columnDefinition = "varchar(100) comment '状态'")
	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	/**
	 * 创建时间
	 * JsonFormat注解是为了固定时间展示格式，timezone是
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
	private Date createTime;

	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "update_time", columnDefinition = "datetime comment '更新时间'")
	private Date updateTime;

	@PrePersist
	public void prePersist() {
		this.updateTime = new Date();
		this.createTime = new Date();
		this.status = StatusEnum.NORMAL;
	}

	@PreUpdate
	public void preUpdate() {
		this.updateTime = new Date();
	}
}
