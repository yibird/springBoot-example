package com.fly.list;

import com.fly.RedisApplication;
import com.fly.redis.list.Queue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * list结构实现队列测试类
 */
@SpringBootTest(classes = RedisApplication.class)
public class QueueTest {

    @Autowired
    private Queue<String> queue;

    @Test
    public void test() {
        queue.setQueue("queue");
        // 为了方便测试每次都清除所有元素
        queue.clear();
        queue.add("item1");
        queue.add("item2");
        queue.add("item3");
        queue.add("item4");
        queue.add("item5");
        System.out.println("items:"+queue.getItems());
        // 出队
        System.out.println("poll item:"+queue.poll()); // item1
        System.out.println("pop item:"+queue.poll()); // item2
        System.out.println("items:"+queue.getItems());
    }
}
