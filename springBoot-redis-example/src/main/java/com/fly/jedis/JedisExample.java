package com.fly.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.params.SetParams;

import java.util.Set;

/**
 * Jedis操作Redis
 */
public class JedisExample {
    private static final String HOST = "192.168.159.128";
    private static final int PORT = 6379;

    /**
     * 创建Jedis连接池,用于复用连接,提升性能,且线程安全。
     * 多个线程操作Jedis实例是非线程安全的,而且会创建大量Jedis实例,
     * 因为这意味着大量的套接字和连接,这也会导致奇怪的错误。
     *
     * @return
     */
    public static JedisPool createJedisPool() {
        // 初始化Jedis连接配置类
        JedisPoolConfig config = new JedisPoolConfig();
        // 连接池最大空闲连接数,默认8
        config.setMaxIdle(10);
        // 连接池最小空闲连接数,默认0
        config.setMinIdle(0);
        // 连接池最大连接数,默认8
        config.setMaxTotal(10);
        // 连接池最大等待毫秒数
        config.setMaxWaitMillis(100);
        // 根据连接池配置、HOST、PORT初始化Jedis连接池
        JedisPool jedisPool = new JedisPool(config, HOST, PORT);
        return jedisPool;
    }

    public static void main(String[] args) {
        System.out.println("=============== 方式1:Jedis实例操作Redis =================");
        JedisPool pool = JedisExample.createJedisPool();
        // 通过Jedis连接池获取一个Jedis实例,也可以通过new Jedis(HOST,PORT)初始化Jedis
        Jedis jedis = pool.getResource();
        // 设置key-value
        String key01Result = jedis.set("key01", "value01");
        String key02Result = jedis.set("key02", "value02");
        System.out.println(key01Result); // OK
        System.out.println(key02Result); // OK
        /**
         * 设置key的value。SetParams用于set key时设置key的参数,例如:
         *  - 设置策略,NX表示仅当key不存在时,set才会生效,XX表示仅当key存在时,set才会生效。
         *    - nx():等同于设置NX参数。
         *    - xx():等同于设置XX参数。
         *  - 过期时间单位,EX表示过期时间单位为秒,PX表示过期时间单位为毫秒:
         *    - exAt(long milliseconds):以秒为单位设置key的过期时间。-1表示永不过期,默认-1。
         *    - pxAt(long milliseconds):以毫秒为单位设置key的过期时间。-1表示永不过期,默认-1。
         *  - keepttl():以秒为单位返回key的存活时间。
         */
        SetParams setParams = new SetParams().nx().pxAt(5000);
        String key03Result = jedis.set("key03", "value03", setParams);
        System.out.println(key03Result); // OK

        // 获取所有key
        Set<String> keys = jedis.keys("*");
        System.out.println("keys:" + keys); // keys:["key02","key01","k2"]

        // 刪除key,返回删除key的数量
        Long key02DelResult = jedis.del("key02");
        System.out.println("删除key的数量:" + key02DelResult); // 删除key的数量:1

        // 根据key获取value
        System.out.println(jedis.get("key01")); // value01
        System.out.println(jedis.get("key02")); // null
        System.out.println(jedis.get("key03")); // null
        System.out.println("=================================");

        System.out.println("=============== JedisPooled实例操作Redis =================");
        JedisPooled pooled = new JedisPooled(HOST, PORT);
        System.out.println(pooled.set("k1", "v1")); // OK
        System.out.println(pooled.set("k2", "v2")); // OK
        System.out.println("del key length:" + pooled.del("k1")); // del key length:1
        System.out.println("k1 value:" + pooled.get("k1")); // k1 value:null
        System.out.println("k2 value:" + pooled.get("k2")); // k1 value:v2
        System.out.println("k1 是否存在:" + pooled.exists("k1")); // k1 是否存在:false
        System.out.println("k2 type:" + pooled.type("k2")); // k2 type:string
        // 根据表达式返回匹配的key,*表示任意
        Set<String> setKeys = pooled.keys("*");
        System.out.println("k2 type:" + setKeys.size()); // 2
    }
}
