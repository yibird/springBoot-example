package com.fly.redis.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class Queue<E> {

    /**
     * 以注入方式初始化RedisTemplate,这种方式需要将当前类注入IOC容器,能充分利用SpringBoot
     * 自动装配,使用简单。
     * 也可以使用非注入方式初始化RedisTemplate,需要设置RedisTemplate的连接工厂(例如
     * Jedis连接工厂或Lettuce连接工厂等等)、序列化器。
     */
    @Autowired
    private RedisTemplate<String, E> redisTemplate;

    /**
     * 队列名称
     */
    private String queueName;

    public void setQueue(String queueName) {
        this.queueName = queueName;
    }

    /**
     * 返回队列的长度
     *
     * @return 队列的长度
     */
    public Long size() {
        checkQueueName();
        return redisTemplate.opsForList().size(queueName);
    }

    /**
     * 添加元素,添加成功返回true,否则返回false
     *
     * @param e 被添加元素
     * @return 添加结果
     */
    public boolean add(E e) {
        checkQueueName();
        try {
            redisTemplate.opsForList().leftPush(queueName, e);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 检索并删除此队列的标头,remove与poll()的区别在于,
     * 如果此队列为空,则抛出异常。
     *
     * @return 被删除的元素
     */
    public E remove() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return redisTemplate.opsForList().rightPop(queueName);
    }

    /**
     * 检索并删除此队列的标头,如果此队列为空,则返回null。
     *
     * @return 被删除的元素
     */
    public E poll() {
        if (size() == 0) return null;
        return redisTemplate.opsForList().rightPop(queueName);
    }


    /**
     * 检索但不删除此队列的头部,element()与peek()在于,
     * 如果此队列为空,则抛出异常。
     *
     * @return 被删除的元素
     */
    public E element() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return redisTemplate.opsForList().index(queueName, -1);
    }

    /**
     * 检索但不删除此队列的头部,如果此队列为空,则返回null。
     *
     * @return 返回队列的头部元素
     */
    public E peek() {
        if (size() == 0) return null;
        return redisTemplate.opsForList().index(queueName, -1);
    }

    /**
     * 获取队列中所有元素
     *
     * @return 队列中所有元素
     */
    public List<E> getItems() {
        checkQueueName();
        return redisTemplate.opsForList().range(queueName, 0, -1);
    }

    /**
     * 清除队列中所有元素。LTRIM key start stop用于修剪list中start至stop的元素,
     * 如果start大于stop就可以达到清除所有元素的效果
     */
    public void clear() {
        checkQueueName();
        redisTemplate.opsForList().trim(queueName, 1, 0);
    }

    /**
     * 检查StackName是否为空
     */
    public void checkQueueName() {
        if (!StringUtils.hasLength(queueName)) {
            throw new NullPointerException("queueName is empty");
        }
    }
}
