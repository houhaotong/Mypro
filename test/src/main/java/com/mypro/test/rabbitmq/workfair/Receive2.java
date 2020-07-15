package com.mypro.test.rabbitmq.workfair;

import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列（公平分发） 消费
 *  公平分发不能自动应答，需要手动应答
 *  公平分发跟轮询分发不一样的是：
 *      轮训分发是多个消费者接受的一样多
 *      公平分发则是相当于能者多劳，比如这个消费者1中间休眠1秒，消费者2中间休眠0.5秒，总共分发了50条，消费者1接受了17个，消费者2接受了33个
 * @author houhaotong
 */
public class Receive2 {

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
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //手动应答
                    channel.basicAck(envelope.getDeliveryTag(),false);
                    System.out.println("[2]msg:"+msg);
                }
            }
        };
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}
