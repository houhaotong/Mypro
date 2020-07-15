package com.mypro.system.service.Impl;

import com.mypro.common.core.properties.MyRabbitProperties;
import com.mypro.system.service.IRabbitSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(EmailRabbitSendServiceImpl.class);

    @Override
    public void sendMsg(String orderNo) {
        log.info("秒杀成功--开始发送邮件消息，{}",orderNo);
        try {
            rabbitTemplate.convertAndSend(myRabbitProperties.getEmail().getExchange(),myRabbitProperties.getEmail().getRoutingKey(),orderNo);
        }catch (Exception e){
           log.error("秒杀成功--发送邮件消息异常，消息为：{}",orderNo);
        }
    }
}
