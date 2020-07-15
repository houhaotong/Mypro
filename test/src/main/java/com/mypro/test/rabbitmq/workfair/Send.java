package com.mypro.test.rabbitmq.workfair;

import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列 发送消息
 * @author houhaotong
 */
public class Send {

    private static final String QUEUE_NAME ="test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        for (int i = 0; i < 50; i++) {
            String msg="msg"+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("发送了:"+msg);
            Thread.sleep(i*10);
        }
        channel.close();
        connection.close();
    }
}
