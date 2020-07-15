package com.mypro.test.rabbitmq.publish;

import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 订阅模式 消费
 * @author houhaotong
 */
public class Receive1 {

    private static final String QUEUE_NAME ="test_queue_fanout_1";
    private static final String EXCHANGE_NAME ="test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //队列绑定交换器
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body, StandardCharsets.UTF_8);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1]msg:"+msg);
                }
            }
        };
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
