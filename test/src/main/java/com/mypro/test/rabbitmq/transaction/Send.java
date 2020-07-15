package com.mypro.test.rabbitmq.transaction;


import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 事务机制 发送
 * @author houhaotong
 */
public class Send {

    private static final String QUEUE_NAME="test_queue_transaction";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        try {
            //开启事务
            channel.txSelect();
            String msg="事务机制发送";
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            int i=1/0;
            System.out.println("发送了："+msg);
            //事务提交
            channel.txCommit();
        }catch (Exception e){
            //事务回滚
            channel.txRollback();
            e.printStackTrace();
        }finally {
            channel.close();
            connection.close();
        }
    }
}
