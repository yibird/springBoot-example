package com.fly.interceptor;

import com.fly.annotation.Sensitive;
import com.fly.utils.DesensitizationUtil;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

/**
 * @Description 数据脱敏拦截器
 * @Author zchengfeng
 * @Date 2023/3/14 14:29
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class DesensitizationInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        List<Object> records = (List<Object>) invocation.proceed();
        records.forEach(this::handleSensitive);
        return records;
    }

    /**
     * 处理数据脱敏
     *
     * @param source 查询结果对象
     */
    public void handleSensitive(Object source) {
        // 获取返回值类型
        Class<?> sourceClass = source.getClass();
        // 通过SystemMetaObject.forObject根据source对象转换为MetaObject
        MetaObject metaObject = SystemMetaObject.forObject(source);

        Stream.of(sourceClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Sensitive.class))
                .forEach(field -> this.doSensitive(metaObject, field));
    }

    public void doSensitive(MetaObject metaObject, Field field) {
        String fieldName = field.getName();
        // 获取属性值
        Object value = metaObject.getValue(fieldName);
        // 获取属性值类型
        Class<?> fieldType = metaObject.getGetterType(fieldName);
        // 仅针对String类型且其值不为null字段脱敏
        if (fieldType == String.class && value != null) {
            metaObject.setValue(fieldName, DesensitizationUtil.desensitization((String) value, field.getAnnotation(Sensitive.class)));
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
