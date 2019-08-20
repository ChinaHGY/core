package com.xmasq.core.log;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础日志（控制层）
 *
 * @author guoyu.huang
 * @version 1.0.0
 */
@Aspect
@Component
@Slf4j
public class BaseLogAspect {

    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void pointcut() {
        // NOTHING
    }

    @Around("pointcut()")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String url = request.getRequestURL().toString();
        String reqParam = getRequestParam(joinPoint);
        String method = request.getMethod();

        // 前置通知 - @Before
        log.debug("Request URL:[{}], Method:[{}]", url, method);
        log.debug("Request Param:[{}]", reqParam);
        // 执行方法
        Object result = joinPoint.proceed();
        // 后置通知
        String respParam = postHandle(result);
        log.debug("Response Content:[{}]", respParam);
        return result;
    }


    /**
     * 通过AOP获取入参数据
     *
     * @param joinPoint
     * @return
     */
    private String getRequestParam(ProceedingJoinPoint joinPoint) {

        // joinPoint获取参数名
        String[] params = ((CodeSignature) joinPoint.getStaticPart().getSignature()).getParameterNames();
        // joinPoint获取参数值
        Object[] args = joinPoint.getArgs();

        Map<String, Object> param = new HashMap<>();

        int i = -1;
        for (Object arg : args) {
            i++;
            if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                continue;
            }
            param.put(params[i], arg);
        }

        return new Gson().toJson(param);
    }

    /**
     * 返回数据
     *
     * @param retVal
     * @return
     */
    private String postHandle(Object retVal) {
        if(null == retVal){
            return "";
        }
        return new Gson().toJson(retVal);
    }

}
