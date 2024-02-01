package com.fly.ds.annotation;

import java.lang.annotation.*;

/**
 * @Description 数据源切换注解
 * @Author zchengfeng
 * @Date 2024/1/25 22:50:37
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DS {
    /**
     * @return 数据源名称
     */
    String value();
}
