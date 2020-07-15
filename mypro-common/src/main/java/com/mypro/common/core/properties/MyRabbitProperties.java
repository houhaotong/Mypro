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

        private String queue;

        private String exchange;

        private String routingKey;

        /** 真实队列 */
        private String realQueue;


        private String prodExchange;

        private String prodRoutingKey;

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

        public String getRealQueue() {
            return realQueue;
        }

        public void setRealQueue(String realQueue) {
            this.realQueue = realQueue;
        }

        public String getProdExchange() {
            return prodExchange;
        }

        public void setProdExchange(String prodExchange) {
            this.prodExchange = prodExchange;
        }

        public String getProdRoutingKey() {
            return prodRoutingKey;
        }

        public void setProdRoutingKey(String prodRoutingKey) {
            this.prodRoutingKey = prodRoutingKey;
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
