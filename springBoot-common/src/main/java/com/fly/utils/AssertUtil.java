package com.fly.utils;

import com.fly.constant.ApiConstant;
import com.fly.exception.ParameterException;
import org.springframework.util.StringUtils;

/**
 * 断言工具类
 */
public class AssertUtil {

    /**
     * 判断字符串非空
     *
     * @param str      判断字符串
     * @param messages 断言信息
     */
    public static void isNotEmpty(String str, String... messages) {
        if (StringUtils.hasLength(str)) {
            execute(messages);
        }
    }

    /**
     * 判断字符串为空
     *
     * @param str
     * @param messages
     */
    public static void isEmpty(String str, String... messages) {
        if (!StringUtils.hasLength(str)) {
            execute(messages);
        }
    }

    /**
     * 判断对象非空
     *
     * @param target   判断对象
     * @param messages 断言信息
     */
    public static void isNotNull(Object target, String... messages) {
        if (null == target) {
            execute(messages);
        }
    }


    /**
     * 断言布尔值(isTrue)是否为true
     *
     * @param isTrue   判断布尔值
     * @param messages 断言信息
     */
    public static void isTrue(boolean isTrue, String... messages) {
        if (isTrue) {
            execute(messages);
        }
    }

    /**
     * 断言布尔值(isTrue)是否为false
     *
     * @param isFalse  判断布尔值
     * @param messages 断言信息
     */
    public static void isFalse(boolean isFalse, String... messages) {
        if (!isFalse) {
            execute(messages);
        }
    }

    /**
     * 断言执行方法
     *
     * @param messages 断言信息
     */
    private static void execute(String... messages) {
        String message = messages != null && messages.length > 0 ? messages[0] : ApiConstant.PARAMETER_MESSAGE;
        throw new ParameterException(message);
    }
}
