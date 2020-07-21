package com.mypro.system.service.Impl;

import com.mypro.common.core.properties.MyMail;
import com.mypro.common.core.properties.MyRabbitProperties;
import com.mypro.common.utils.MyproCostant;
import com.mypro.system.domain.SecItem;
import com.mypro.system.domain.SecOrder;
import com.mypro.system.domain.SecOrderAndUserInfo;
import com.mypro.system.mapper.ISecItemMapper;
import com.mypro.system.mapper.ISecOrderMapper;
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
    MyMail mail;

    @Autowired
    ISecOrderMapper orderMapper;

    @Autowired
    IMailService mailService;

    private static final Logger log= LoggerFactory.getLogger(EmailRabbitReceiveService.class);

    /**
     * 监听接收消息，发送邮件
     * @param orderAndUserInfo 收到的消息
     */
    @RabbitListener(queues = "${mq.myrabbit.email.queue}",containerFactory = "singleListenerContainer")
    public void receiveMsg(SecOrderAndUserInfo orderAndUserInfo){
        try {
            log.info("邮件队列接收到消息：{}",orderAndUserInfo);
            String content=String.format(mail.getContent(),orderAndUserInfo.getItemName(),orderAndUserInfo.getOrderId());
            mail.setContent(content);
            //真正发送邮件
            mailService.sendHtmlEmail();
        }catch (Exception e){
            log.error("邮件队列接收消息异常！");
            e.printStackTrace();
        }
    }

    /**
     * 监听死信队列
     * @param orderAndUserInfo 收到的消息
     */
    @RabbitListener(queues = "${mq.myrabbit.dead.real-dead-queue}",containerFactory = "singleListenerContainer")
    public void receiveDeadMsg(SecOrderAndUserInfo orderAndUserInfo){
        try {
            log.info("死信队列接收到消息：{}",orderAndUserInfo);
            if (orderAndUserInfo!=null){
                SecOrder order = orderMapper.selectOrderByOrderId(orderAndUserInfo.getOrderId());
                //订单存在，并且订单状态处于未处理状态
                if (order!=null&&orderAndUserInfo.getState()== MyproCostant.ORDER_NO_OPERATION){
                    //将订单状态设置为已过期
                    order.setState(MyproCostant.ORDER_EXPIRED);
                    orderMapper.updateOrder(order);
                }
            }
        }catch (Exception e){
            log.error("死信队列接收消息异常！");
            e.printStackTrace();
        }
    }
}
