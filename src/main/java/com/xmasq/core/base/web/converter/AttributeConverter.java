package com.xmasq.core.base.web.converter;

import java.lang.reflect.Field;
import java.util.List;

import com.xmasq.core.base.entity.AbstractAttribute;

/**
 * 属性转换器
 *
 * @author guoyu.huang
 * @version 1.0.0
 */
public class AttributeConverter<TARGET, ATTR extends AbstractAttribute<?>> {

	/**
	 * 利用反射机制，设置属性值<br>
	 * 
	 * 注意：ATTR中的code要和TARGET中的属性名一模一样，否则赋值会失败
	 * 
	 * @param target
	 * @param attr
	 * @return
	 */
	public TARGET converter(TARGET target, ATTR attr) {
		Field[] fs = target.getClass().getDeclaredFields();
		for (Field field : fs) {
			// 要设置属性可达，不然会抛出IllegalAccessException异常
			field.setAccessible(true);
			if (field.getName().equals(attr.getCode())) {
				try {
					field.set(target, attr.getValue());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return target;
	}

	/**
	 * 批量设置属性值
	 * 
	 * @param target
	 * @param attrs
	 * 
	 * @see #converter(Object, AbstractAttribute)
	 * @return
	 */
	public TARGET converter(TARGET target, List<ATTR> attrs) {
		for (ATTR attr : attrs) {
			target = converter(target, attr);
		}
		return target;
	}

}