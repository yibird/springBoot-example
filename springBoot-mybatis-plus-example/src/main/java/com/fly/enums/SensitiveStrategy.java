package com.fly.enums;

import com.fly.functions.Desensitize;
import org.assertj.core.util.Strings;

/**
 * @Description 数据脱敏枚举类
 * @Author zchengfeng
 * @Date 2023/3/15 11:43
 */
public enum SensitiveStrategy {

    /**
     * 密码脱敏策略
     */
    PASSWORD("(\\S)", s -> s),
    /**
     * 中文名称脱敏策略
     */
    CHINESE_NAME("([\\u4E00-\\u9FFF])\\S([\\u4E00-\\u9FFF]*)", s -> Strings.concat("$1", s, "$2")),
    /**
     * 邮箱脱敏策略
     */
    EMAIL("(^\\w)[^@]*(@.*$)", s -> Strings.concat("$1", repeat(s, 4), "$2")),
    /**
     * 手机号脱敏策略
     */
    PHONE("(\\d{3})\\d{4}(\\d{4})", s -> Strings.concat("$1", repeat(s, 4), "$2")),
    /**
     * 银行卡脱敏策略
     */
    BANK_CARD("(\\d{4})\\d*(\\d{4})$", s -> Strings.concat("$1", repeat(s, 6), "$2")),
    /**
     * 身份证脱敏策略
     */
    ID_CARD("(\\d{4})\\d{10}(\\w{4})", s -> Strings.concat("$1", repeat(s, 4), "$2")),
    /**
     * 地址脱敏策略
     */
    ADDRESS("(\\S{4})\\S{4}(\\S*)\\S{4}", s -> Strings.concat("$1", repeat(s, 4), "$2", repeat(s, 4)));
    private String regex;
    private Desensitize desensitize;

    /**
     * @param regex       正则
     * @param desensitize 占位符函数
     */
    SensitiveStrategy(String regex, Desensitize desensitize) {
        this.regex = regex;
        this.desensitize = desensitize;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Desensitize getDesensitize() {
        return desensitize;
    }

    public void setDesensitize(Desensitize desensitize) {
        this.desensitize = desensitize;
    }

    /**
     * @param s 字符串
     * @param n 重复次数
     * @return 返回重复指定数次的字符串
     */
    public static String repeat(String s, int n) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
