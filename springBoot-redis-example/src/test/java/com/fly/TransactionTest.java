package com.fly;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;

/**
 * Redis事务测试类
 */
@SpringBootTest(classes = RedisApplication.class)
public class TransactionTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 命令式事务
     */
    @Test
    public void imperativeTransactionTest() {
        /**
         * 执行Redis Session。允许在同一会话中执行多个操作,
         * 通过 multi() 和 watch(Collection)操作实现事务功能。
         */
        List<Object> execute = redisTemplate.execute(new SessionCallback<List<Object>>() {
            /**
             * 在同一会话内执行所有给定操作
             * @param operations RedisOperations提供了操作Redis的方法
             * @return
             * @throws DataAccessException
             */
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                /**
                 * 监视指定key,如果有至少一个被监视的键在EXEC执行之前被修改了,那么整个事务都会被取消
                 */
                operations.watch("key_str");
                // 在执行EXEC命令之前修改watch中的key,整个事务被放弃,execute的结果为:execute:[]
                // operations.opsForValue().set("key_str", "value");
                try {
                    // 开启事务
                    operations.multi();
                    // 设置key-value
                    operations.opsForValue().set("key_str", "value_str");
                    operations.opsForValue().set("key_num", "value_num");
                } catch (Exception e) {
                    // 放弃事务
                    operations.discard();
                }
                // 执行事务,返回一个List,该List包含执行命令后的结果
                return operations.exec();
            }
        });
        System.out.println("execute:" + execute);
    }

    /**
     * 使用pipeline包裹事务以降低开销
     */
    @Test
    public void pipelineAndTransaction() {
        // 执行流水线并返回执行结果
        List<Object> execute = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            try {
                // 开启事务
                connection.multi();
                connection.set("k01".getBytes(), "v01".getBytes());
                connection.set("k02".getBytes(), "v02".getBytes());
            } catch (Exception e) {
                // 放弃事务
                connection.discard();
                e.printStackTrace();
            }
            // 执行事务
            return connection.exec();
        });
        System.out.println("execute:"+execute); // execute:[[true, true]]
    }
}
