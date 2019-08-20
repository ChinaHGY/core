package com.xmasq.core.script.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.xmasq.core.base.entity.AbstractBaseEntity;
import com.xmasq.core.base.entity.AbstractBusinessEntity;
import com.xmasq.core.script.ScriptExecuteStatusEnum;
import com.xmasq.core.script.ScriptTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 脚本
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
@Setter
@Getter
@Entity(name = Script.TABLE_NAME)
public class Script extends AbstractBusinessEntity {

	public static final String TABLE_NAME = "t_common_script";

	@Column(name = "name", columnDefinition = "varchar(255) comment '名称'")
	private String name;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "type", columnDefinition = "varchar(255) comment '类型'")
	private ScriptTypeEnum type;

	@Column(name = "path", columnDefinition = "varchar(1024) comment '路径'")
	private String path;

	@Column(name = "version", columnDefinition = "varchar(100) comment '版本'")
	private String version;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "execute_status", columnDefinition = "varchar(100) comment '执行状态'")
	private ScriptExecuteStatusEnum executeStatus = ScriptExecuteStatusEnum.FAIL;

	@Column(name = "memo", columnDefinition = "varchar(255) comment '备注'")
	private String memo;


}
