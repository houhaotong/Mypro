package com.mypro.common.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置映射类
 * @author houhaotong
 */
@Component
@ConfigurationProperties(prefix = "mq.myrabbit")
public class MyRabbitProperties {

    /** 过期时间 */
    private Long expire;

    private final Email email=new Email();

    private final Dead dead=new Dead();

    /** 发送邮件的消息模型 */
    public static class Email{

        /** 发送邮件队列名 */
        private String queue;

        /** 发送邮件交换机名 */
        private String exchange;

        /** 发送邮件routingKey */
        private String routingKey;

        public String getQueue() {
            return queue;
        }

        public void setQueue(String queue) {
            this.queue = queue;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getRoutingKey() {
            return routingKey;
        }

        public void setRoutingKey(String routingKey) {
            this.routingKey = routingKey;
        }
    }

    /** 死信消息模型（订单超时未支付） */
    public static class Dead{

        private String normalQueue;

        /** 死信交换机DLX */
        private String exchange;

        /** 死信路由 */
        private String routingKey;

        /** 真实存储死信队列 */
        private String realDeadQueue;

        private String normalExchange;

        private String normalRoutingKey;

        public String getNormalQueue() {
            return normalQueue;
        }

        public void setNormalQueue(String normalQueue) {
            this.normalQueue = normalQueue;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getRoutingKey() {
            return routingKey;
        }

        public void setRoutingKey(String routingKey) {
            this.routingKey = routingKey;
        }

        public String getRealDeadQueue() {
            return realDeadQueue;
        }

        public void setRealDeadQueue(String realDeadQueue) {
            this.realDeadQueue = realDeadQueue;
        }

        public String getNormalExchange() {
            return normalExchange;
        }

        public void setNormalExchange(String normalExchange) {
            this.normalExchange = normalExchange;
        }

        public String getNormalRoutingKey() {
            return normalRoutingKey;
        }

        public void setNormalRoutingKey(String normalRoutingKey) {
            this.normalRoutingKey = normalRoutingKey;
        }
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public Email getEmail() {
        return email;
    }

    public Dead getDead() {
        return dead;
    }
}
