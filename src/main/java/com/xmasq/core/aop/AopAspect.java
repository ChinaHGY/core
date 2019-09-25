package com.xmasq.core.aop;

import com.xmasq.core.AppContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author guoyu.huang
 * @version 2.0.0
 */
@Aspect
@Component
@Slf4j
public class AopAspect {

    // 因为@Aop可以用于类或者方法，所以增加两种切面
    @Pointcut("@annotation(com.xmasq.core.aop.Aop) || @within(com.xmasq.core.aop.Aop)")
    public void aspect() {

    }

    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before("aspect()")
    public void doBefore(JoinPoint joinPoint) {
        getAnnotation(joinPoint).forEach(iAopHandle -> {
            iAopHandle.handleBefore(joinPoint);
        });
    }

    /**
     * 后置通知
     */
    @After("aspect()")
    public void doAfter(JoinPoint joinPoint) {
        getAnnotation(joinPoint).forEach(iAopHandle -> {
            iAopHandle.handleAfter(joinPoint);
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
        getAnnotation(joinPoint).forEach(iAopHandle -> {
            iAopHandle.handleAfterReturning(joinPoint, result);
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
        getAnnotation(joinPoint).forEach(iAopHandle -> {
            iAopHandle.handleAfterThrowing(joinPoint, e);
        });
    }

    /**
     * 获取注解，因为注解可以配置在方法上也可以配置在类上，返回所有的注解，优先执行方法上的
     *
     * @param joinPoint
     * @return
     */
    private List<IAopHandle> getAnnotation(JoinPoint joinPoint) {
        List<IAopHandle> result = new ArrayList<>();
        // 识别方法上的注解
        String methodName = joinPoint.getSignature().getName();
        //拦截的放参数类型
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        try {
            Method method = joinPoint.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            Aop methodAnnotation = method.getAnnotation(Aop.class);
            if (methodAnnotation != null) {
                Class methodHandleClass = methodAnnotation.handleClass();
                ClassGetType getType = methodAnnotation.getType();
                IAopHandle iAopHandle = getILogHandleInstance(methodHandleClass, getType);
                if(iAopHandle != null){
                    result.add(iAopHandle);
                }
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 识别类上的注解
        Aop classAnnotation = joinPoint.getTarget().getClass().getAnnotation(Aop.class);
        if (classAnnotation != null) {
            Class classHandleClass = classAnnotation.handleClass();
            ClassGetType getType = classAnnotation.getType();
            IAopHandle iAopHandle = getILogHandleInstance(classHandleClass, getType);
            if(iAopHandle != null){
                result.add(iAopHandle);
            }
        }

        return result;
    }

    /**
     * 获取接口实例
     *
     * @param handleClass
     * @param getType
     * @return
     */
    private IAopHandle getILogHandleInstance(Class handleClass, ClassGetType getType) {
        try {
            Object object = null;
            if (ClassGetType.SPRING.equals(getType)) {
                object = AppContext.getBeanByType(handleClass);
                if (object == null) {
                    log.warn("实例：[{}]没有加入Spring BeanFactory，无法获取bean实例", handleClass.getName());
                }
            } else {
                object = handleClass.newInstance();
            }
            if (object instanceof IAopHandle) {
                IAopHandle logHandle = (IAopHandle) object;
                return logHandle;
            } else {
                log.warn("实例：[{}]非IAopHandle实现类，忽略", handleClass.getName());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
