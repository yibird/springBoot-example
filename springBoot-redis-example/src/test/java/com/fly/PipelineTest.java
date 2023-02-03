package com.fly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis Pipeline测试类
 */
@SpringBootTest(classes = RedisApplication.class)
public class PipelineTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private List<String> values = new ArrayList<>();


    /**
     * 测试前置执行方法,向values添加1w个元素。@BeforeEach用于表示在当前测试类中的每个使用@Test、
     * @RepeatedTest、@ParameterizedTest、@TestFactory和@TestTemplate注解的测试方法
     * 执行前执行的方法。
     */
    @BeforeEach
    public void addItems() {
        for (int i = 0; i < 10000; i++) {
            values.add("item-" + i);
        }
    }

    /**
     * 未使用pipeline向list批量添加数据
     */
    @Test
    public void listAddItems() {
        /**
         * Spring提供的简单秒表,允许对多个任务计时,显示每个指定任务的总运行时间和运行时间
         */
        StopWatch sw = new StopWatch();
        // 开始计时
        sw.start();
        for (String value : values) {
            // 向指定list列表的左端添加元素
            redisTemplate.opsForList().leftPush("list", "value-" + value);
        }
        // 结束计时
        sw.stop();
        System.out.println("批量向list添加10000个元素总耗时:" + sw.getTotalTimeMillis() + "ms");
    }

    /**
     * 使用pipeline向list批量添加数据,性能有百倍以上的提升,
     * 尤其是批量执行的命令越多,性能提升越加明显
     */
    @Test
    public void usePipelineAddItems() {
        StopWatch sw = new StopWatch();
        sw.start();

        /**
         * executePipelined(RedisCallback<?> action):执行pipeline,接收一个Redis回调。
         * 该回调需要重写doInRedis方法。
         */
        redisTemplate.executePipelined(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    for (String value : values) {
                        // 向指定list列表的左端添加元素
                        connection.lPush("list".getBytes(), ("value-" + value).getBytes());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        sw.stop();
        System.out.println("使用pipeline批量向list添加10000个元素总耗时:" + sw.getTotalTimeMillis() + "ms");
    }
}
