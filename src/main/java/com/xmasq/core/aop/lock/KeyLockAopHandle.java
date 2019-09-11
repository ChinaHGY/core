package com.xmasq.core.aop.lock;

import com.xmasq.core.aop.IAopHandle;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * <p>
 * 允许多个key同时存在，结合@AOP时，需要将getType设置为SPRING。
 * </p>
 * 默认会将方法中的参数名为key的当作锁的key
 *
 * @author guoyu.huang
 * @version 1.0.0
 */
@Slf4j
@Component
public class KeyLockAopHandle implements IAopHandle {

    private Map<String, Semaphore> map = new HashMap<>();
    // key的参数名
    private final String KEY_PARAM_NAME = "key";

    @Override
    public void handleBefore(JoinPoint joinPoint) {
        String key = getKey(joinPoint);
        if(key == null){
            log.warn("key is null");
        }else{
            try{
                if(map.containsKey(key)){
                    map.get(key).acquire();
                }else{
                    Semaphore semaphore = new Semaphore(1,true);
                    semaphore.acquire();
                    map.put(key, semaphore);
                }
            } catch (InterruptedException e) {
                log.warn("获取锁失败，原因：" + e.getMessage());
                map.get(key).release();
            }
        }
    }

    @Override
    public void handleAfter(JoinPoint joinPoint) {
        String key = getKey(joinPoint);
        map.get(key).release();
    }

    @Override
    public void handleAfterReturning(JoinPoint joinPoint, Object result) {
        // NOTHING
    }

    @Override
    public void handleAfterThrowing(JoinPoint joinPoint, Exception e) {
        // NOTHING
    }

    /**
     * 获取锁的key
     *
     * @param joinPoint
     * @return
     */
    private String getKey(JoinPoint joinPoint){
        String[] params = ((CodeSignature) joinPoint.getStaticPart().getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        for(int i = 0; i < params.length; i++){
            if(KEY_PARAM_NAME.equals(params[i])){
                return String.valueOf(args[i]);
            }
        }
        return null;
    }
}
