package com.xmasq.core.base.web.vo;

import java.util.Date;

import com.xmasq.core.common.StatusEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * AbstractBusinessEntity对应的VO类
 *
 * @author guoyu.huang
 * @version 1.0.0
 */
@Setter
@Getter
@ToString
public class BusinessVO {

	private String id;

	private StatusEnum status;

	private Date createTime;

	private Date updateTime;

}
