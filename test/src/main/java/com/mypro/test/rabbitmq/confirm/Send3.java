package com.mypro.test.rabbitmq.confirm;


import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * 生产者端confirm确认机制 发送
 *      异步模式,需要添加一个回调：confirmListener，然后重写handleAck处理有应答的方法，handleNack处理无应答的方法
 *              而且需要定义一个用于存储未确认的消息的集合,推荐使用sortedSet或sortedMap,推荐JUC中的实现类
 * @author houhaotong
 */
public class Send3 {

    private static final String QUEUE_NAME = "test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //使用sortedMap的拓展接口NavigableMap存储未确认的消息，K-消息标识（deliveryTag）,V-消息体
        NavigableMap<Long,String> unconfirm=new ConcurrentSkipListMap<Long, String>();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //开启confirm模式
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                //多条消息时
                if(multiple){
                    System.out.println("====Ack====multiple");
                    //使用headMap移除deliveryTag和之后的所有消息
                    NavigableMap<Long, String> map = unconfirm.headMap(deliveryTag, true);
                    map.clear();
                }else{
                    System.out.println("====Ack====");
                    //单条消息时，移除集合中对应消息
                    unconfirm.remove(deliveryTag);
                }
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("无应答消息："+deliveryTag+",是否多条:"+multiple);
                //多条消息时
                if(multiple){
                    System.out.println("====Nack====multiple");
                    //使用headMap移除deliveryTag和之后的所有消息
                    NavigableMap<Long, String> map = unconfirm.headMap(deliveryTag, true);
                    map.clear();
                }else{
                    System.out.println("====Nack====");
                    //单条消息时，移除集合中对应消息
                    unconfirm.remove(deliveryTag);
                }
            }
        });
        String msg = "confirm机制异步模式发送";
        for (int i = 0; i <100 ; i++) {
            //要发送的消息标识
            Long seqNo=channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            //向集合中添加
            unconfirm.put(seqNo,msg);
            System.out.println("异步发送："+msg);
        }
        channel.close();
        connection.close();
    }
}
