package com.mypro.common.config;

import com.mypro.common.core.properties.MyRabbitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitMQ配置类
 * @author houhaotong
 */
@Configuration
public class RabbitConfig {

    private static final Logger log= LoggerFactory.getLogger(RabbitConfig.class);

    /** 自己的rabbit配置映射类，读取自yml配置文件 */
    @Autowired
    MyRabbitProperties myRabbitProperties;

    /** 更换默认的消息序列器 */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    /** 单一消费者监听器 */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory singleListenerContainer(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory=new SimpleRabbitListenerContainerFactory();
        //设置消息序列化器
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConnectionFactory(connectionFactory);
        //设置最小消费者数为1
        factory.setConcurrentConsumers(1);
        //设置最大消费者数为1
        factory.setMaxConcurrentConsumers(1);
        //每个消费者未完成的最大未确认消息数为1
        factory.setPrefetchCount(1);
        //设置批量处理的数量为1
        factory.setBatchSize(1);
        return factory;
    }

    /** 自定义rabbitTemplate */
    @Bean
    public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer, ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate();
        configurer.configure(template, connectionFactory);
        //设置confirmCallback，只要到broker就会触发，找不到交换机则返回false
        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (!ack){
                    log.warn("消息发送失败，触发confirmCallback！");
                }else {
                    log.info("消息发送成功！correlationDate:({}),cause:({})",correlationData,cause);
                }
            }
        });
        //设置ReturnCallback,只有在发送到队列时出错，才会触发此回调
        template.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.warn("消息已丢失！message:({}),replyCode:({}),replyText:({}),exchange:({}),routingKey:({})",message.toString(),replyCode,replyText,exchange,routingKey);
            }
        });
        return template;
    }

    /** 创建发送邮件队列 */
    @Bean
    public Queue emailQueue(){
        return new Queue(myRabbitProperties.getEmail().getQueue(),true);
    }

    /** 创建发送邮件topic主题模式的交换机，设置持久化和不自动删除 */
    @Bean
    public Exchange emailExchange(){
        return new TopicExchange(myRabbitProperties.getEmail().getExchange(),true,false);
    }

    /** 绑定队列和交换机,并设置routingKey */
    @Bean
    public Binding emailBinding(){
        return BindingBuilder.bind(emailQueue()).to(emailExchange()).with(myRabbitProperties.getEmail().getRoutingKey()).noargs();
    }

    /** 普通的队列，但是指定了死信交换机和路由，在这个队列中的消息过期后会由死信交换机分发到真正的死信队列 */
    @Bean
    public Queue normalQueue(){
        Map<String, Object> args = new HashMap<>();
        //设置死信参数
        //指定死信交换机
        args.put("x-dead-letter-exchange",myRabbitProperties.getDead().getExchange());
        //指定死信路由
        args.put("x-dead-letter-routing-key",myRabbitProperties.getDead().getRoutingKey());
        return new Queue(myRabbitProperties.getDead().getNormalQueue(),true,false,false,args);
    }

    /** 创建普通交换机 */
    @Bean
    public Exchange normalExchange(){
        return new TopicExchange(myRabbitProperties.getDead().getNormalExchange(),true,false);
    }

    /** 绑定普通队列和普通交换机 */
    @Bean
    public Binding normalBind(){
        return BindingBuilder.bind(normalQueue()).to(normalExchange()).with(myRabbitProperties.getDead().getNormalRoutingKey()).noargs();
    }

    /** 创建死信交换机 */
    @Bean
    public Exchange deadExchange(){
        return new TopicExchange(myRabbitProperties.getDead().getExchange(),true,false);
    }

    /** 创建真实存储死信的队列，当死信队列中消息过期后，转发到此队列，真实存储死信队列需要绑定死信交换机和路由 */
    @Bean
    public Queue realDeadQueue(){
        return new Queue(myRabbitProperties.getDead().getRealDeadQueue(),true,false,false);
    }

    /** 绑定真实存储死信的队列与死信交换机 */
    @Bean
    public Binding realBindDead(){
        return BindingBuilder.bind(realDeadQueue()).to(deadExchange()).with(myRabbitProperties.getDead().getRoutingKey()).noargs();
    }
}
