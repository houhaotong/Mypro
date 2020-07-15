package com.mypro.test.rabbitmq.publish;

import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订阅模式 发送
 * @author houhaotong
 */
public class Send {

    private static final String EXCHANGE_NAME ="test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机，分发的
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String msg="交换机分发";
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        System.out.println("send:"+msg);
        channel.close();
        connection.close();
    }
}
