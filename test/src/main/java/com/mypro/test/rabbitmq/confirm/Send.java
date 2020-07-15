package com.mypro.test.rabbitmq.confirm;


import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者端confirm确认机制 发送
 *      普通模式,发送单条消息
 * @author houhaotong
 */
public class Send {

    private static final String QUEUE_NAME = "test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //开启confirm模式
        channel.confirmSelect();
        String msg = "confirm机制发送";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        if (!channel.waitForConfirms()){
            //没有确认的情况
            System.out.println("msg send fail");
        }else {
            System.out.println("msg send ok");
        }
        channel.close();
        connection.close();
    }
}
