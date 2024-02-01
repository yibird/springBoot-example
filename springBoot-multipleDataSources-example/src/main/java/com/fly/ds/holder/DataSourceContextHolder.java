package com.fly.ds.holder;

import org.springframework.core.NamedThreadLocal;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Description 数据源上下文持有者,由于解决下上文数据通讯问题,保证线程安全
 * @Author zchengfeng
 * @Date 2024/1/25 22:40:07
 */
public class DataSourceContextHolder {

    /**
     * 使用ThreadLocal存储数据源名称,保证线程安全
     */
    private static final ThreadLocal<Deque<String>> KEYS = new NamedThreadLocal<Deque<String>>("dynamic-datasource") {
        @Override
        protected Deque<String> initialValue() {
            return new ArrayDeque<>();
        }
    };

    /**
     * 入栈,设置当前线程数据源名称
     */
    public static void push(String dbKey) {
        KEYS.get().push(dbKey);
    }

    /**
     * 出栈,获得当前线程数据源名称
     *
     * @return 当前线程数据源名称
     */
    public static String peek() {
        return KEYS.get().peek();
    }

    /**
     * 清空当前线程数据源
     */
    public static void poll() {
        Deque<String> deque = KEYS.get();
        deque.poll();
        if (deque.isEmpty()) {
            KEYS.remove();
        }
    }

    /**
     * 强制清空本地线程
     */
    public static void clear() {
        KEYS.remove();
    }
}
