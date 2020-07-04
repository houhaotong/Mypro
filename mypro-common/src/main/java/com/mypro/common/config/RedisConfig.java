package com.mypro.common.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> template=new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //json序列化器
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer=new FastJsonRedisSerializer<Object>(Object.class);
        //设置key序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        //设置hash的key序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        //设置value的序列化方式
        template.setValueSerializer(fastJsonRedisSerializer);
        //设置hash的value的序列化方式
        template.setHashValueSerializer(fastJsonRedisSerializer);
        return template;
    }

    @Bean("objectRedisTemplate")
    public RedisTemplate<Object,Object> objectRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
