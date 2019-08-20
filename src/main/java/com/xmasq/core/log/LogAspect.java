package com.xmasq.core.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guoyu.huang
 * @version 1.0.0
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    // 因为@Log可以用于类或者方法，所以增加两中切面
    @Pointcut("@annotation(com.xmasq.core.log.Log) || @within(com.xmasq.core.log.Log)")
    public void aspect() {

    }

    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before("aspect()")
    public void doBefore(JoinPoint joinPoint) {
        getAnnotation(joinPoint).forEach(iLogHandle -> {
            iLogHandle.handleBefore(joinPoint);
        });
    }

    /**
     * 后置通知
     */
    @After("aspect()")
    public void doAfter(JoinPoint joinPoint) {
        getAnnotation(joinPoint).forEach(iLogHandle -> {
            iLogHandle.handleAfter(joinPoint);
        });
    }

    /**
     * 结果通知
     *
     * @param joinPoint
     * @param result
     */
    @AfterReturning(pointcut = "aspect()", returning = "result")
    public Object doAfterReturning(JoinPoint joinPoint, Object result) {
        getAnnotation(joinPoint).forEach(iLogHandle -> {
            iLogHandle.handleAfterReturning(joinPoint, result);
        });
        return result;
    }

    /**
     * 异常通知
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "aspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        getAnnotation(joinPoint).forEach(iLogHandle -> {
            iLogHandle.handleAfterThrowing(joinPoint, e);
        });
    }

    /**
     * 获取注解，因为注解可以配置在方法上也可以配置在类上，返回所有的注解，优先执行方法上的
     *
     * @param joinPoint
     * @return
     */
    private List<ILogHandle> getAnnotation(JoinPoint joinPoint) {
        List<ILogHandle> result = new ArrayList<>(2);
        // 识别方法上的注解
        String methodName = joinPoint.getSignature().getName();
        //拦截的放参数类型
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        try {
            Method method = joinPoint.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            Log methodAnnotation = method.getAnnotation(Log.class);
            if (methodAnnotation != null) {
                Class[] methodHandleClass = methodAnnotation.handleClass();
                result.addAll(getILogHandleInstance(methodHandleClass));
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 识别类上的注解
        Log classAnnotation = joinPoint.getTarget().getClass().getAnnotation(Log.class);
        if (classAnnotation != null) {
            Class[] classHandleClass = classAnnotation.handleClass();
            result.addAll(getILogHandleInstance(classHandleClass));
        }

        return result;
    }

    /**
     * 获取接口实例
     *
     * @param handleClass
     * @return
     */
    private List<ILogHandle> getILogHandleInstance(Class[] handleClass) {
        List<ILogHandle> result = new ArrayList<>(handleClass.length);
        for (int i = 0; i < handleClass.length; i++) {
            try {
                Object object = handleClass[i].newInstance();
                if (object instanceof ILogHandle) {
                    ILogHandle logHandle = (ILogHandle) object;
                    result.add(logHandle);
                } else {
                    log.warn("实例：[{}]非ILogHandle实现类，忽略", handleClass[i].getName());
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return result;
    }
}
