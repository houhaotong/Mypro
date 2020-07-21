package com.mypro.system.service.Impl;

import com.mypro.common.core.properties.MyMail;
import com.mypro.system.service.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * 秒杀邮件发送 服务
 * @author houhaotong
 */
@Service
@EnableAsync
public class SecMailServiceImpl implements IMailService {

    private final static Logger log= LoggerFactory.getLogger(SecMailServiceImpl.class);

    @Autowired
    MyMail mail;

    @Autowired
    JavaMailSender mailSender;
    /**
     * 异步发送简单文本
     */
    @Override
    @Async
    public void sendSimpleEmail(){
        try {
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom(mail.getSendFrom());
            message.setSubject(mail.getSubject());
            message.setText(mail.getContent());
            message.setTo(mail.getSendTo());
            mailSender.send(message);

            log.info("简单文本的邮件发送成功！");
        }catch (Exception e){
            log.error("简单文本的邮件发送失败！",e.fillInStackTrace());
        }
    }

    @Override
    @Async
    public void sendHtmlEmail() {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper=new MimeMessageHelper(message, true,"utf-8");
            messageHelper.setFrom(mail.getSendFrom());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getContent(),true);
            messageHelper.setTo(mail.getSendTo());
            mailSender.send(message);
            log.info("HTML邮件发送成功！");
        }catch (Exception e){
            log.error("HTML邮件发送失败！",e.fillInStackTrace());
        }
    }
}
