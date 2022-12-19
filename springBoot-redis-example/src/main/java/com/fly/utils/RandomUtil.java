package com.fly.utils;

import java.security.SecureRandom;

/**
 * 随机数工具类
 */
public class RandomUtil {
    private static SecureRandom random = new SecureRandom();

    /**
     * 生成随机数
     *
     * @return
     */
    public static Long nextLong() {
        String start = String.valueOf(random.nextInt(1000000000));
        String end = String.valueOf(random.nextInt(1000000000));
        return Long.valueOf(start + end);
    }
}
