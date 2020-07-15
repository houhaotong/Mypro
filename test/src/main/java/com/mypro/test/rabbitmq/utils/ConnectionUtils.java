package com.mypro.test.rabbitmq.utils;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {

    public static Connection getConnection() throws IOException, TimeoutException {

        //连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //本地地址
        factory.setHost("127.0.0.1");
        //amqp端口
        factory.setPort(5672);
        //账号密码
        factory.setUsername("user");
        factory.setPassword("123456");
        //设置vhost
        factory.setVirtualHost("/vhost_test");

        return factory.newConnection();
    }
}
