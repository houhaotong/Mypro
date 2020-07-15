package com.mypro.test.rabbitmq.topic;


import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式 发送
 * @author houhaotong
 */
public class Send {

    private static final String EXCHANGE_NAME ="test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明转发类型的交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        //指定routingKey可以推送给指定消费者
        String routingKey="item.aaa";

        String msg="topic send:"+routingKey;
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
        System.out.println("发送了："+msg+",routingKey:"+routingKey);
        channel.close();
        connection.close();
    }
}
