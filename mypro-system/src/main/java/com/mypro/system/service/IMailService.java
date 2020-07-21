package com.mypro.system.service;

import org.springframework.scheduling.annotation.Async;

/**
 * 邮件发送接口
 * @author houhaotong
 */
public interface IMailService {

    /**
     * 异步发送简单文本
     */
    public void sendSimpleEmail();

    /**
     * 异步发送html格式的邮件
     */
    public void sendHtmlEmail();
}
