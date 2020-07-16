package com.mypro.system.service.Impl;

import com.mypro.common.core.properties.MyRabbitProperties;
import com.mypro.system.domain.SecOrderAndUserInfo;
import com.mypro.system.mapper.ISecOrderMapper;
import com.mypro.system.service.IRabbitSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 邮件发送服务
 * @author houhaotong
 */
@Service
public class EmailRabbitSendServiceImpl implements IRabbitSendService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MyRabbitProperties myRabbitProperties;

    @Autowired
    ISecOrderMapper orderMapper;

    private static final Logger log = LoggerFactory.getLogger(EmailRabbitSendServiceImpl.class);

    @Override
    public void sendMsg(String orderId) {
        log.info("秒杀成功--开始发送邮件消息，{}",orderId);
        try {
            if (orderId!=null){
                SecOrderAndUserInfo orderAndUserInfo = orderMapper.selectOrderAndUserByOrderId(orderId);
                if (orderAndUserInfo!=null){
                    rabbitTemplate.convertAndSend(myRabbitProperties.getEmail().getExchange(), myRabbitProperties.getEmail().getRoutingKey(), orderAndUserInfo, new MessagePostProcessor() {
                        @Override
                        public Message postProcessMessage(Message message) throws AmqpException {
                            MessageProperties messageProperties = message.getMessageProperties();
                            //设置消息的持久化
                            messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                            return message;
                        }
                    });
                }
            }
        }catch (Exception e){
           log.error("秒杀成功--发送邮件消息异常，消息为：{}",orderId);
        }
    }
}
