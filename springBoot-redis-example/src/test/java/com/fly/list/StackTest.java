package com.fly.list;


import com.fly.RedisApplication;
import com.fly.redis.list.Stack;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RedisApplication.class)
public class StackTest {
    @Autowired
    Stack<String> stack;

    @Test
    public void test() {
        stack.setStack("stack");
        stack.push("item1");
        stack.push("item2");
        stack.push("item3");
        stack.push("item4");
        stack.push("item5");
        System.out.println("items:" + stack.getItems());
        //  移除元素
        System.out.println("pop item:" + stack.pop());
        System.out.println("pop item:" + stack.pop());
        System.out.println("items:" + stack.getItems());
    }
}
