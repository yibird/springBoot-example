package com.fly.interceptor;

import com.fly.annotation.Sensitive;
import com.fly.domain.model.User;
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
import java.util.function.Function;
import java.util.function.Supplier;
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

    /**
     * 拦截器拦截方法,用于拦截query请求。
     * @param invocation 表示调用对象
     * @return 返回query后的结果
     * @throws Throwable 异常对象
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        /**
         * Invocation内部封装了拦截相关的调用信息,Invocation对象支持方法:
         * - proceed():用于获取处理结果。
         * - getMethod():用于获取拦截方法。该拦截方法与@Signature注解上指定的method一致
         * - getArgs():用于拦截方法的参数。该拦截方法参数与@Signature注解上指定的args列表一致。
         * - getTarget():获取目标拦截对象。
         */
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
        /**
         * MetaObject对象(元对象)是Mybatis框架用于访问对象属性的工具类,底层实现为java的反射基础,
         * 支持JavaBean、Collection、Map三种类型对象访问,也可以自定义其他类型。
         * metaObject常用方法:
         * - getValue(String name):根据对象的字段名称获取字段值。
         * - setValue(String name,Object value):根据字段名称设置对象的字段值。
         * - getGetterType(String name):根据字段获取字段返回值类型。
         * - getGetterNames():获取对象中getter字段的字段名数组。
         * - getOriginalObject():获取源对象。
         * - findProperty(String propName, boolean useCamelCaseMapping):从对象中查找字段,
         * useCamelCaseMapping表示是否使用驼峰命名法映射。
         * - hasGetter(String name):判断字段名是否为getter字段。
         * - hasSetter(String name):判断字段是否为setter字段。
         * - getObjectFactory():获取对象工厂。负责创建对象,对象可能层层嵌套,都对象为null时,需要通过对象工厂创建。
         * - getObjectWrapper():获取对象包装对象,包装对象用于增强原始对象功能。
         * - getObjectWrapperFactory():获取对象包装工厂。
         * - getReflectorFactory():获取反射工厂,用于Class元信息的封装。
         *
         * 通过SystemMetaObject.forObject根据source对象转换为MetaObject。
         */
        MetaObject metaObject = SystemMetaObject.forObject(source);
        /**
         * (1).sourceClass.getDeclaredFields()用于获取sourceClass对象所有字段,
         * getDeclaredFields()只能获取当前的对象Field集合,但是无法获取该对象继承父类的对象,
         * 通过sourceClass.getSuperclass().getDeclaredFields()可以获取父类的Field集合。
         * (2).filter(field -> field.isAnnotationPresent(Sensitive.class))用于过滤字段的注解是
         * Sensitive.class注解的字段。
         * (3).forEach(field -> this.doSensitive(metaObject, field))用于元对象(metaObject)和
         * 字段对象处理脱敏。
         */
        Supplier<Field[]> supplier= sourceClass::getDeclaredFields;
        Stream.of(supplier.get())
                .filter(field -> field.isAnnotationPresent(Sensitive.class))
                .forEach(field -> this.doSensitive(metaObject, field));
    }

    /**
     * 脱敏方法
     * @param metaObject
     * @param field
     */
    public void doSensitive(MetaObject metaObject, Field field) {
        // 获取字段名
        String fieldName = field.getName();
        // 获取字段对应的字段值
        Object value = metaObject.getValue(fieldName);
        // 获取字段的返回值类型
        Class<?> fieldType = metaObject.getGetterType(fieldName);
        // 仅针对String类型且其值不为null的字段脱敏
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
