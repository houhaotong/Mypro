package com.mypro.test.rabbitmq.simple;

import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 简单队列模式 发送
 * @author houhaotong
 */
public class Send {

    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection= ConnectionUtils.getConnection();
        //连接创建一个通道
        Channel channel=connection.createChannel();
        /*
         * 声明队列
         *      属性：durable:声明一个持久化队列
         *           exclusive:声明独占队列（仅限于此连接）
         *           autoDelete:声明一个autoDelete队列（服务器将在不再使用时删除它）
         *           arguments:队列的其他属性（构造参数）
         */
        channel.queueDeclare("test_simple_queue",false,false,false,null);

        String msg="test simple model";
        //推送消息
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("send MSG:"+msg);
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
