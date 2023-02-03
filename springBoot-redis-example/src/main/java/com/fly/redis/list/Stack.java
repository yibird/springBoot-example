package com.fly.redis.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class Stack<E> {
    @Autowired
    private RedisTemplate<String, E> redisTemplate;

    private String stackName;

    public void setStack(String stackName) {
        this.stackName = stackName;
    }


    /**
     * 将元素推入到栈的顶部
     *
     * @param item 添加元素
     * @return item
     */
    public E push(E item) {
        checkStackName();
        redisTemplate.opsForList().leftPush(stackName, item);
        return item;
    }

    /**
     * 删除此堆栈顶部的元素,并将该对象作为此函数的值返回。
     *
     * @return 堆栈顶部的元素
     */
    public E pop() {
        checkStackName();
        E e = redisTemplate.opsForList().leftPop(stackName);
        return e;
    }

    /**
     * 获取栈中所有元素的数量
     *
     * @return
     */
    public long size() {
        checkStackName();
        return redisTemplate.opsForList().size(stackName);
    }

    /**
     * 判断栈中元素是否为空
     *
     * @return
     */
    public boolean empty() {
        return size() == 0;
    }

    /**
     * 获取队列中所有元素
     * @return 队列中所有元素
     */
    public List<E> getItems() {
        return redisTemplate.opsForList().range(stackName, 0, -1);
    }


    /**
     * 清除队列中所有元素。LTRIM key start stop用于修剪list中start至stop的元素,
     * 如果start大于stop就可以达到清除所有元素的效果
     */
    public void clear() {
        checkStackName();
        redisTemplate.opsForList().trim(stackName, 1, 0);
    }

    /**
     * 检查StackName是否为空
     */
    public void checkStackName() {
        if (!StringUtils.hasLength(stackName)) {
            throw new NullPointerException("stackName is empty");
        }
    }
}
