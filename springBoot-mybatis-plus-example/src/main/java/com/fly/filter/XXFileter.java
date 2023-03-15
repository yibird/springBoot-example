package com.fly.filter;

import com.alibaba.fastjson2.filter.ValueFilter;
import com.fly.annotation.Sensitive;
import com.fly.utils.DesensitizationUtil;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Description XXFileter
 * @Author zchengfeng
 * @Date 2023/3/15 17:06
 */
public class XXFileter implements ValueFilter {
    // 缓存容器,用于提升反射性能,key以className + fieldName,value为对应字段
    private final Map<String, Field> fieldMap = new HashMap();

    @Override
    public Object apply(Object object, String name, Object value) {
        Class<?> objectClass = object.getClass();
        String objectName = objectClass.getName();
        // 缓存key
        String key = objectName + "." + name;
        if (fieldMap.containsKey(key)) {
            return doSensitive(fieldMap.get(key), value);
        }
        try {
            Field field = objectClass.getDeclaredField(name);
            fieldMap.put(key, field);
            return doSensitive(field, value);
        } catch (NoSuchFieldException e) {
            return value;
        }
    }

    /**
     * 处理脱敏
     *
     * @param field 字段对象
     * @param value 字段值
     * @return 返回脱敏后的字段值
     */
    public static Object doSensitive(Field field, Object value) {
        if (field.isAnnotationPresent(Sensitive.class)) {
            return DesensitizationUtil.desensitization((String) value, field.getAnnotation(Sensitive.class));
        }
        return value;
    }
}
