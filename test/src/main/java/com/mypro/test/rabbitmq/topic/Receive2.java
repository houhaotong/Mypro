package com.mypro.test.rabbitmq.topic;

import com.mypro.test.rabbitmq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式 消费者
 *  可以将routingKey设置为xxx.#或者#.xxx，符号#实现一个通配的效果，
 *      例如item.#,交换机不管是是item.add，item.delete,还是别的item.开头的，该消费者都能接收到
 *      *  (星号) 用来表示一个单词 (必须出现的)
 *      #  (井号) 用来表示任意数量（零个或多个）单词
 *      通配的绑定键是跟队列进行绑定的，举个小例子
 *      队列Q1 绑定键为 *.TT.*          队列Q2绑定键为  TT.#
 *      如果一条消息携带的路由键为 A.TT.B，那么队列Q1将会收到；
 *      如果一条消息携带的路由键为TT.AA.BB，那么队列Q2将会收到；
 * @author houhaotong
 */
public class Receive2 {

    private static final String QUEUE_NAME ="test_queue_topic_2";

    private static final String EXCHANGE_NAME ="test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //队列绑定交换器,并指定routingKey
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"item.#");
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body, StandardCharsets.UTF_8);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2]msg:"+msg);
                }
            }
        };
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
