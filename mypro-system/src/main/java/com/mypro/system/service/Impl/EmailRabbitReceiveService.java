package com.mypro.system.service.Impl;

import com.mypro.common.core.properties.MyRabbitProperties;
import com.mypro.system.domain.SecOrderAndUserInfo;
import com.mypro.system.service.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 邮件消息接收 消费者
 * @author houhaotong
 */
@Service
public class EmailRabbitReceiveService {

    @Autowired
    MyRabbitProperties myRabbitProperties;
    @Autowired
    IMailService mailService;

    private static final Logger log= LoggerFactory.getLogger(EmailRabbitReceiveService.class);

    @RabbitListener(queues = "${mq.myrabbit.email.queue}",containerFactory = "singleListenerContainer")
    public void receiveMsg(SecOrderAndUserInfo orderAndUserInfo){
        try {
            log.info("邮件队列接收到消息：{}",orderAndUserInfo);
            //真正发送邮件
            mailService.sendSimpleEmail();
        }catch (Exception e){
            log.error("邮件队列接收消息异常！");
            e.printStackTrace();
        }
    }
}
