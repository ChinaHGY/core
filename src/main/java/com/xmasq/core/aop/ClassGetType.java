package com.xmasq.core.aop;

/**
 * 目前支持NEW和SPRING两种
 * @author guoyu.huang
 * @version 1.0.0
 */
public enum ClassGetType {

    /**
     * new一个实例出来，
     */
    NEW,
    /**
     * 从Spring BeanFactory获取
     */
    SPRING
}
