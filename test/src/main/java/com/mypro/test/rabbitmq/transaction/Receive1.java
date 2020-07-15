package com.mypro.test.rabbitmq.transaction;

import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 事务机制 消费者
 *      使用txselect将频道开启事务，txcommit提交事务，txrollback回滚事务
 * @author houhaotong
 */
public class Receive1 {

    private static final String QUEUE_NAME="test_queue_transaction";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body, StandardCharsets.UTF_8);
                System.out.println("[RECV]msg:"+msg);
            }
        };
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
