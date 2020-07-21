package com.mypro.system.service;

/**
 * RabbitMq 发送服务
 * @author houhaotong
 */
public interface IRabbitSendService {

    /**
     * 发送消息
     * @param msg 消息体
     */
    public void sendMsg(String msg);

    /**
     * 发送死信
     * @param msg 消息体
     */
    public void sendDeadMsg(String msg);
}
