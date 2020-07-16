package com.mypro.common.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮件内容设置属性
 * @author houhaotong
 */
@Component
@ConfigurationProperties(prefix = "seckill.mail")
public class MyMail {

    /** 邮箱地址 */
    private String sendFrom;

    /** 邮件主题 */
    private String subject;

    /** 邮件内容 */
    private String content;

    /** 目标邮箱 */
    private String[] sendTo;

    public String[] getSendTo() {
        return sendTo;
    }

    public void setSendTo(String[] sendTo) {
        this.sendTo = sendTo;
    }

    public String getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
