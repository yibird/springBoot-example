package com.fly.constant;

/**
 * redis key前缀常量类,在redis生产实践中通过使用 业务前缀 + key 的命名方式名key,
 * 这样能区分不同业务的key,当出现异常时有利于定位问题。
 */
public enum RedisKeyPrefixConstant {

    // id相关key前缀
    id("id:"),
    ip("ip:"),
    cache("cache:");

    private String prefix;

    RedisKeyPrefixConstant(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setKeyPrefix(String prefix) {
        this.prefix = prefix;
    }
}
