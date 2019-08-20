package com.xmasq.core.log;

import org.aspectj.lang.JoinPoint;

/**
 * 日志处理类接口
 *
 * @author guoyu.huang
 * @version 1.0.0
 */
public interface ILogHandle {

    /**
     * 前置通知
     */
    void handleBefore(JoinPoint joinPoint);

    /**
     * 后置通知
     */
    void handleAfter(JoinPoint joinPoint);

    /**
     * 结果通知
     * @param joinPoint
     * @param result
     */
    void handleAfterReturning(JoinPoint joinPoint, Object result);

    /**
     * 异常通知
     * @param joinPoint
     * @param e
     */
    void handleAfterThrowing(JoinPoint joinPoint, Exception e);
}
