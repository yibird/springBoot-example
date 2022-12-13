package com.fly.enums.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应枚举类
 */
public enum ResponseStatus {

    // 响应成功
    SUCCESS(200, "响应成功"),
    // 响应失败
    ERROR(500, "响应错误"),
    // 访问令牌非法
    INVALID_TOKEN(2001, "Illegal access token");
    private final int code;
    private final String message;

    private static Map<String,ResponseStatus> statusMap = new HashMap<>();
    static {
        Arrays.stream(ResponseStatus.values()).forEach(item->{
            statusMap.put(String.valueOf(item.getCode()),item);
        });
    }
    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
    public static String getMessageByCode(int code){
        return statusMap.get(String.valueOf(code)).getMessage();
    }
}
