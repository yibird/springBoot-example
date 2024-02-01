package com.fly.annotation;

import java.lang.annotation.*;
import java.time.Duration;

/**
 * @Description 限流注解
 * @Author zchengfeng
 * @Date 2024/1/26 13:24:35
 */
@Inherited
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    // 默认限制的请求次数
    int value() default 10;

    // 默认限制的时间窗口,单位秒
    long duration() default 60;
}
