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
}
