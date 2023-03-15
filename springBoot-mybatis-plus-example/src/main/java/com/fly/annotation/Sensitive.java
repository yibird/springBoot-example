package com.fly.annotation;

import com.fly.enums.SensitiveStrategy;
import java.lang.annotation.*;

/**
 * @Description 数据脱敏注解
 * @Author zchengfeng
 * @Date 2023/3/15 11:42
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Sensitive {
    /**
     * @return 脱敏策略
     */
    SensitiveStrategy strategy();
    /**
     * @return 敏感信息在原字符串中的起始偏移量
     */
    int start() default 0;

    /**
     * @return 敏感信息在原字符串中的结束偏移量
     */
    int end() default 0;

    /**
     * @return 正则表达式匹配的敏感信息
     */
    String regex() default "";

    /**
     * @return 敏感信息的替换占位符
     */
    char placeholder() default '*';
}
