package com.mypro.test.rabbitmq.workfair;

import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列（公平分发） 消费
 * @author houhaotong
 */
public class Receive1 {

    private static final String QUEUE_NAME ="test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //每次只发一个
        channel.basicQos(1);
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body, StandardCharsets.UTF_8);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //手动应答
                    channel.basicAck(envelope.getDeliveryTag(),false);
                    System.out.println("[1]msg:"+msg);
                }
            }
        };
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}
