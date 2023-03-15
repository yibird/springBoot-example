package com.fly.utils;

import com.fly.annotation.Sensitive;

/**
 * @Description 脱敏工具类
 * @Author zchengfeng
 * @Date 2023/3/14 15:08
 */
public class DesensitizationUtil {

    /**
     * || 运算方法,用于设置默认值,当s1为空时将返回s2,否则返回s1
     *
     * @param s1 原始值
     * @param s2 默认值
     * @return
     */
    public static String or(String s1, String s2) {
        return s1 == null || s1.length() == 0 ? s2 : s1;
    }

    /**
     * 根据偏移量返回正确范围的偏移量
     *
     * @param offset    偏移量
     * @param minOffset 最小偏移量
     * @param maxOffset 最大偏移量
     * @return 返回正确范围的偏移量
     */
    public static int getOffset(int offset, int minOffset, int maxOffset) {
        if (offset < minOffset) return minOffset;
        if (offset > maxOffset) return maxOffset;
        return offset;
    }

    /**
     * 根据正则表达式替换target中所有字符为占位符
     *
     * @param target      目标字符串
     * @param regex       正则表达式
     * @param placeholder 占位符
     * @return 返回替换后的字符串
     */
    public static String replace(String target, String regex, String placeholder) {
        return target.replaceAll(regex, placeholder);
    }

    /**
     * 数据脱敏方法
     *
     * @param target    脱敏字符串
     * @param sensitive Sensitive注解
     * @return 返回脱敏后的字符串
     */
    public static String desensitization(String target, Sensitive sensitive) {
        if (target == null) return null;
        int len = target.length();
        int start = getOffset(sensitive.start(), 0, len);
        int end = getOffset(sensitive.end() == 0 ? len : sensitive.end(), start + 1, len);
        // 获取正则
        String regex = or(sensitive.regex(), sensitive.strategy().getRegex());
        // 获取占位符
        String placeholder = sensitive.strategy().getDesensitize().apply(String.valueOf(sensitive.placeholder()));
        if (start == 0 && end == len) return replace(target, regex, placeholder);
        String prefix = target.substring(0, start);
        String suffix = target.substring(end);
        return prefix + replace(target.substring(start, end), regex, placeholder) + suffix;
    }
}
