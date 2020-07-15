package com.mypro.test.rabbitmq.confirm;

import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 生产者端confirm确认机制 消费
 *      confirm模式是异步的
 *      confirmSelect可以开启频道的confirm模式
 *      编程模式:
 *          waitForConfirms：发送一条消息
 *          waitForConfirms：发送多条消息
 *          异步confirm模式：提供一个回调方法
 * @author houhaotong
 */
public class Receive1 {

    private static final String QUEUE_NAME="test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body, StandardCharsets.UTF_8);
                System.out.println("[confirm]msg:"+msg);
            }
        };
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
