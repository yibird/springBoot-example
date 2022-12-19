package com.fly.example;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.api.sync.RedisStringCommands;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Lettuce操作Redis
 */
public class LettuceExample {

    private static final String HOST = "192.168.159.128";
    private static final int PORT = 6379;

    static RedisClient client;
    static StatefulRedisConnection<String, String> connection;

    /**
     * 创建连接
     *
     * @return
     */
    public static void createConnection() {
        // 创建Redis客户端实例
        client = RedisClient.create(RedisURI.create(HOST, PORT));
        // 设置默认超时时间
        client.setDefaultTimeout(20, TimeUnit.SECONDS);
        // 获取Redis状态连接
        connection = client.connect();
    }

    /**
     * 关闭连接和客户端
     */
    public static void closeConnection() {
        if (connection != null) {
            connection.close();
        }
        if (client != null) {
            client.shutdown();
        }
    }

    public static void main(String[] args) {
        LettuceExample.createConnection();
        if (connection == null) return;
        /**
         * 同步接口,connection.sync()返回一个RedisCommands对象,RedisCommands对象是一个组合接口,继承子如下接口:
         * - BaseRedisCommands:Redis基本操作命令对象。提供了ping()、reset()、检查连接状态、订阅等方法。
         * - RedisAclCommands:Redis Acl操作命令对象。Acl用于提高Redis操作安全性。
         * - RedisClusterCommands:Redis集群操作命名对象。
         * - RedisServerCommands:Redis服务器操作命令对象,用于Redis服务器相关操作。
         * - RedisKeyCommands:Redis key操作命令对象。提供了key操作方法,例如key()、keys()...
         * - RedisListCommands:Redis List类型操作命令对象,用于操作List。
         * - RedisScriptingCommands:Redis脚本操作命令对象,用于Lua脚本操作。
         * - RedisSetCommands:Redis Set类型操作命令对象。
         * - RedisSortedSetCommands:Redis ZSet类型操作命令对象。
         * - RedisStringCommands:表示Redis String类型操作命令对象。
         * - RedisGeoCommands:Redis Geo(地理位置)类型操作命令。
         * - RedisHashCommands:Redis Hashmap类型操作命令对象。
         * - RedisStreamCommands:Redis Stream类型操作命令对象。
         * - RedisTransactionalCommands:Redis事务操作命名对象。
         * - RedisHLLCommands:Redis HyperLogLog(超日志)类型操作命令对象。
         */
        RedisCommands<String, String> commands = connection.sync();
        RedisStringCommands sync = connection.sync();
        // 设置key-value
        System.out.println(sync.set("key_01", "value_01")); // OK
        /**
         * 设置key-value并指定参数:
         * - nx():等同于set key时设置NX参数。NX表示仅当key不存在时,set才会生效。
         * - xx():等同于set key时设置XX参数。XX表示仅当key存在时,set才会生效。
         * - exAt(long timestamp):以秒为单位设置key的过期时间。-1表示永不过期,默认-1。
         * - pxAt(long timestamp):以毫秒为单位设置key的过期时间。-1表示永不过期,默认-1。
         * - keepttl():以秒为单位返回key的存活时间。
         */
        SetArgs setArgs = SetArgs.Builder.nx().pxAt(5000);
        System.out.println(sync.set("key_02", "value_02",setArgs)); // OK
        System.out.println(sync.get("key_01")); // value_01
        // 获取key的类型
        System.out.println(commands.type("key_01")); // string
        // 判断key是否存在,返回一个long类型,1表示存在,0表示不存在
        System.out.println(commands.exists("key_01111")); // 1
        // 根据表达式返回匹配的key,*表示任意
        List<String> keys = commands.keys("*");
        System.out.println("all key length:"+keys.size()); // all key length:2
        LettuceExample.closeConnection();
    }
}
