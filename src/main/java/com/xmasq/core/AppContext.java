package com.xmasq.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 如果是非spring注解的类，可以使用这个类去获取spring注解的类。<br>
 * 推荐用法：
 * 
 * <pre>
 * AppContext.getBeanByType(JavaBean.class).method();
 * 
 * <pre>
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
@Slf4j
@Component
public class AppContext implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 从Spring容器中获取Bean，其类型为clazz。<br>
	 * 特别注意：Spring容器中必须包含且只包含一个该类型的Bean， 否则返回null
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T getBeanByType(Class<T> clazz) {
		Map<String, T> map = applicationContext.getBeansOfType(clazz);
		T obj = null;
		if (map.size() == 1) {
			for (Object o : map.keySet()) {
				obj = map.get(o);
			}
		} else if (map.size() == 0) {
			log.warn("在Spring容器中没有类型为" + clazz.getName() + "的Bean");
		} else {
			log.warn("在Spring容器中有多个类型为" + clazz.getName() + "的Bean");
		}
		return obj;
	}

	/**
	 * 从Spring容器中获取Bean，其类型为clazz。<br>
	 * 特别注意：Spring容器中必须至少包含一个该类型的Bean， 否则返回null
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getBeansByType(Class<T> clazz) {
		List<T> objs = null;
		Map<String, T> map = applicationContext.getBeansOfType(clazz);
		if (map.size() >= 1) {
			Collection<T> c = map.values();
			objs = new ArrayList<T>(map.size());
			for (Object object : c.toArray()) {
				objs.add((T) object);
			}
		} else if (map.size() == 0) {
			log.warn("在Spring容器中没有类型为" + clazz.getName() + "的Bean");
		}
		return objs;
	}

	public static Object getBeanByName(String beanName) {
		return applicationContext.getBean(beanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		AppContext.applicationContext = applicationContext;
	}

}
