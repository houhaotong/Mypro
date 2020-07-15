package com.mypro.test.rabbitmq.rout;


import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式 发送
 * @author houhaotong
 */
public class Send {

    private static final String EXCHANGE_NAME ="test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明转发类型的交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        String msg="路由模式发送";
        //指定routingKey可以推送给指定消费者
        String routingKey="warning";
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
        System.out.println("发送了："+msg+",routingKey:"+routingKey);
        channel.close();
        connection.close();
    }
}
