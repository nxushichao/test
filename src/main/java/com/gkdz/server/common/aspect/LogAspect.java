package com.gkdz.server.common.aspect;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author wu
 */
@Aspect
@Configuration
@Slf4j
public class LogAspect {

    public LogAspect() {
    }

    @Pointcut("execution(public * com.gkdz.server.modules.dev.controller.*.*(..))||execution(public * com.gkdz.server.modules.sys.controller.*.*(..))")
    public void pointCutMethod() {
    }

    /**
     * 环绕
     */
    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        //获取类的字节码对象，通过字节码对象获取方法信息
        Class<?> targetCls = pjp.getTarget().getClass();
        //获取方法签名(通过此签名获取目标方法信息)
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        String controller = pjp.getTarget().getClass().getSimpleName();
        String method = pjp.getSignature().getName();
        String args = Arrays.toString(pjp.getArgs());

        //获取注解
        Method targetMethod = targetCls.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        ApiOperation requiredLog = targetMethod.getAnnotation(ApiOperation.class);
        String operation = "";
        if (requiredLog != null) {
            operation = requiredLog.value();
        }
        log.info("<{},{}.{}>,请求参数:{}", operation, controller, method, args);
        Object ret = pjp.proceed();
        log.info("{}响应数据:{}", operation, JSONUtil.toJsonStr(ret));
        return ret;
    }
}