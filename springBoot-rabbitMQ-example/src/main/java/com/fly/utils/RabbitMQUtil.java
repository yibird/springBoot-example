package com.fly.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ工具类
 */
public class RabbitMQUtil {
    // RabbitMQ虚拟主机
    private final static String VIRTUAL_HOST = "VHOST";
    // RabbitMQ连接主机地址
    private final static String HOST = "192.168.98.128";
    // RabbitMQ端口
    private final static int PORT = 5672;
    // RabbitMQ用户名
    private final static String USERNAME = "test";
    // RabbitMQ密码
    private final static String PASSWORD = "test";
    // 连接对象
    private static Connection conn;

    /**
     * 获取一个信道
     * @return Channel
     * @throws IOException io异常
     * @throws TimeoutException 超时异常
     */
    public static Channel getChannel() throws IOException, TimeoutException {
        return RabbitMQUtil.getChannel(VIRTUAL_HOST, HOST, PORT, USERNAME, PASSWORD);
    }

    /**
     * 获取一个信道
     * @param virtualHost 虚拟主机
     * @param host 主机地址
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return Channel
     * @throws IOException io异常
     * @throws TimeoutException 超时异常
     */
    public static Channel getChannel(String virtualHost, String host, int port, String username, String password) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost(virtualHost);
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        // 获取连接对象
        conn = factory.newConnection();
        // 创建一个channel(通道)
        return conn.createChannel();
    }

    /**
     * 关闭连接
     * @throws IOException io异常
     */
    public static void closeConn() throws IOException {
        if (conn != null) {
            conn.close();
        }
    }
}
