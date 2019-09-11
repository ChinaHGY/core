package com.xmasq.core.aop;

import java.lang.annotation.*;

/**
 * 日志注解。<br>
 * 注意事项：该注解允许使用在方法上，也允许使用在类上。如果方法和类上都有这个注解，会获取所有注解的实例，方法上的优先执行。
 *
 * @author guoyu.huang
 * @version 1.0.0
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Aop {

    /**
     * 处理日志的类名，这个类必须实现{@code IAopHandle}接口
     *
     * @return
     * @see IAopHandle
     */
    Class handleClass();

    /**
     * handleClass获取方式，支持从Spring BeanFactory获取和new一个实例出来，两种方式。默认为new一个实例出来
     * @return
     */
    ClassGetType getType() default ClassGetType.NEW;
}
