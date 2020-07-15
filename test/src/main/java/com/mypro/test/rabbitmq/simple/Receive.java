package com.mypro.test.rabbitmq.simple;

import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.*;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * 简单队列模式 接受消息
 * @author houhaotong
 */
public class Receive {

    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection=ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //定义队列消费者
        Consumer consumer=new DefaultConsumer(channel){
            /**
             * 收到消息时，自动调用此方法
             * @param consumerTag 用来标识.可以再监听队列时候设置
             * @param envelope 信封,通过envelope可以通过这个获取到很多东西
             * @param properties 额外的消息属性
             * @param body 消息体
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body, StandardCharsets.UTF_8);
                System.out.println();
            }
        };
        //监听队列
       channel.basicConsume(QUEUE_NAME, consumer);
    }
}
