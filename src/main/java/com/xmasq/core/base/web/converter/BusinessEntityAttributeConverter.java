package com.xmasq.core.base.web.converter;

import com.xmasq.core.base.entity.AbstractAttribute;
import com.xmasq.core.base.entity.AbstractBusinessEntity;
import com.xmasq.core.base.web.vo.BusinessVO;

/**
 * 业务实体的扩展属性转换器
 *
 * @author guoyu.huang
 * @version 1.0.0
 */
public class BusinessEntityAttributeConverter<TARGET extends BusinessVO, ATTR extends AbstractAttribute<?>>
		extends AttributeConverter<TARGET, ATTR> {

	/**
	 * 将实体类转成VO，其中实体类有扩展属性，通过反射机制将扩展属性转成VO的属性
	 * 
	 * @param t
	 * @param vo
	 * @return
	 */
	public <T extends AbstractBusinessEntity> TARGET converterVO(T t, TARGET vo) {
		if (t == null) {
			return vo;
		} else {
			vo.setCreateTime(t.getCreateTime());
			vo.setId(t.getId());
			vo.setStatus(t.getStatus());
			vo.setUpdateTime(t.getUpdateTime());
			return vo;
		}
	}
}
