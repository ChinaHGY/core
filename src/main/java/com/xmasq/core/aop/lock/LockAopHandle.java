package com.xmasq.core.aop.lock;

import com.xmasq.core.aop.IAopHandle;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;

/**
 * 结合@AOP时，需要将getType设置为SPRING
 * @author guoyu.huang
 * @version 2.0.0
 */
@Component
@Slf4j
public class LockAopHandle implements IAopHandle {

    private Semaphore semaphore = new Semaphore(1, true);

    @Override
    public void handleBefore(JoinPoint joinPoint) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            log.warn("获取锁失败，原因：" + e.getMessage());
            semaphore.release();
        }
    }

    @Override
    public void handleAfter(JoinPoint joinPoint) {
        semaphore.release();
    }

    @Override
    public void handleAfterReturning(JoinPoint joinPoint, Object result) {

    }

    @Override
    public void handleAfterThrowing(JoinPoint joinPoint, Exception e) {

    }
}
