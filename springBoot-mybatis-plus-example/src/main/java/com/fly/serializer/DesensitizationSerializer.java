package com.fly.serializer;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;
import com.fly.enums.SensitiveStrategy;
import com.fly.utils.DesensitizationUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 脱敏序列化器。根据
 * @Description XXSerializer
 * @Author zchengfeng
 * @Date 2023/3/16 15:36
 */
public class DesensitizationSerializer implements ObjectWriter {

    private final static String PASSWORD_KEYWORDS = "password";
    private final static String CHINESE_NAME_KEYWORDS = "name";
    private final static String EMAIL_KEYWORDS = "email";
    private final static String[] PHONE_KEYWORDS = new String[]{"phone", "mobile"};
    private final static String BANK_CARD_KEYWORDS = "bankCard";
    private final static String[] ID_CARD_KEYWORDS = new String[]{"idCard", "id_card"};
    private final static String ADDRESS_KEYWORDS = "address";

    Map<String, Function<String, String>> map = new HashMap<>();

    /**
     * 序列化方法
     * @param jsonWriter JSON写入器
     * @param value 字段值
     * @param fieldName 字段名
     * @param fieldType 字段类型
     * @param features JSON feature
     */
    @Override
    public void write(JSONWriter jsonWriter, Object value, Object fieldName, Type fieldType, long features) {
        // 仅针对字符串类型且值不为空进行脱敏处理
        if (fieldType == String.class && value != null) {
            jsonWriter.writeAny(doSensitive((String) fieldName, (String) value));
            return;
        }
        jsonWriter.writeAny(value);
    }

    /**
     * 脱敏方法
     * @param fieldName 字段名
     * @param value 字段值
     * @return 返回脱敏后字段值
     */
    public static String doSensitive(String fieldName, String value) {
        if (contains(fieldName, PASSWORD_KEYWORDS)) {
            return DesensitizationUtil.desensitization(String.valueOf(value), SensitiveStrategy.PASSWORD);
        }
        if (contains(fieldName, CHINESE_NAME_KEYWORDS)) {
            return DesensitizationUtil.desensitization(String.valueOf(value), SensitiveStrategy.CHINESE_NAME);
        }
        if (contains(fieldName, EMAIL_KEYWORDS)) {
            return DesensitizationUtil.desensitization(String.valueOf(value), SensitiveStrategy.EMAIL);
        }
        if (contains(fieldName, PHONE_KEYWORDS)) {
            return DesensitizationUtil.desensitization(String.valueOf(value), SensitiveStrategy.PHONE);
        }
        if (contains(fieldName, BANK_CARD_KEYWORDS)) {
            return DesensitizationUtil.desensitization(String.valueOf(value), SensitiveStrategy.BANK_CARD);
        }
        if (contains(fieldName, ID_CARD_KEYWORDS)) {
            return DesensitizationUtil.desensitization(String.valueOf(value), SensitiveStrategy.ID_CARD);
        }
        if (contains(fieldName, ADDRESS_KEYWORDS)) {
            return DesensitizationUtil.desensitization(String.valueOf(value), SensitiveStrategy.ADDRESS);
        }
        return value;
    }

    public static boolean contains(String s1, String s2) {
        return s1.toLowerCase().contains(s1);
    }

    public static boolean contains(String s1, String[] strings) {
        for (int i = strings.length - 1; i >= 0; i--) {
            if (strings[i].equals(s1)) return true;
        }
        return false;
    }
}
