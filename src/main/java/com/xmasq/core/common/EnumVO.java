package com.xmasq.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 将枚举转成给前端使用的vo，使用案例:
 * 
 * <pre>
 * public static List<EnumVo> build() { List<EnumVo> enumList = new
 * ArrayList<>(); for (Status status : Status.values()) { enumList.add(new
 * EnumVo(status.getCode(), status.getName())); } return enumList; }
 *
 * 
 * 调用： List<EnumVo> enumList = Status.build();
 * 
 * <pre>
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnumVO {

	private String code;
	private String name;

}
