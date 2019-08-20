package com.xmasq.core.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 状态码的枚举字段
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
public enum StatusEnum {

	/** 未使用 */
	UNUSED("未使用"),
	/** 正常 */
	NORMAL("正常"),
	/** 已删除 */
	DELETED("已删除"),
	/** 其他 */
	OTHER("其他");
	
	private String text;
	
	StatusEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static List<EnumVO> build() {
		List<EnumVO> enumList = new ArrayList<>();
		for (StatusEnum status : StatusEnum.values()) {
			enumList.add(new EnumVO(status.name(), status.getText()));
		}
		return enumList;
	}

}