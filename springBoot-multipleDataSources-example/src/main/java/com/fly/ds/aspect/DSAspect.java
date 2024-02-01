package com.fly.ds.aspect;

import com.fly.ds.annotation.DS;
import com.fly.ds.config.DataSourceConfig;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description
 * @Author zchengfeng
 * @Date 2024/1/25 22:53:05
 */
@Aspect
//@Component
public class DSAspect {

    @Pointcut("@annotation(com.fly.ds.annotation.DS)")
    private void dsPointcut() {
    }

    @Before("dsPointcut()")
    public void routingDataSource(JoinPoint joinPoint, DataSourceConfig config) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DS ds = method.getAnnotation(DS.class);
        System.out.println("ds:" + ds);
        System.out.println("config:" + config);
    }
}
