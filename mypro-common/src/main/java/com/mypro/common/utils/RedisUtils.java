package com.mypro.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * redis 工具类
 *
 * @author houhaotong
 */
@Component
public final class RedisUtils {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取缓存数据
     *
     * @param key 键
     * @return 数据
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     * @param key 键
     * @return true成功，false失败
     */
    public boolean del(String key) {
        return key==null?false:redisTemplate.delete(key);
    }

    /**
     * 设置缓存数据
     *
     * @param key   键
     * @param value 值
     * @return true成功，false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加进set集合
     * @param key 集合的key
     * @param object 元素
     */
    public Long sAdd(String key, Object... object) {
        try {
            return redisTemplate.opsForSet().add(key,object);
        }catch (Exception e){
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 删除集合元素
     * @param key 集合的key
     * @param objects 要删除的元素
     */
    public Long sRem(String key,Object... objects) {
        try {
            return redisTemplate.opsForSet().remove(key,objects);
        }catch (Exception e){
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取集合中元素数量
     * @param key 集合的key
     * @return 元素数量
     */
    public int sCard(String key) {
        return redisTemplate.opsForSet().size(key).intValue();
    }

    /**
     * 获取集合中的元素
     * @param key 集合的key
     * @return 元素集合
     */
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * ZSet添加元素
     * @param key 键
     * @param score score
     * @param values 值
     */
    public Boolean zAdd(String key,double score,Object values){
        return redisTemplate.opsForZSet().add(key,values,score);
    }

    /**
     * 删除zSet集合元素
     * @param key 集合的key
     * @param objects 要删除的元素
     */
    public Long zRem(String key,Object... objects) {
        try {
            return redisTemplate.opsForZSet().remove(key,objects);
        }catch (Exception e){
            e.printStackTrace();
            return 0L;
        }
    }
    /**
     * 获取ZSet中元素数量
     * @param key 集合的key
     * @return 元素数量
     */
    public int zCard(String key) {
        return redisTemplate.opsForZSet().size(key).intValue();
    }

    /**
     * 通过score范围移除元素
     * @param key 键
     * @param min 范围的最小值
     * @param max 范围的最大值
     */
    public Long zRemByScore(String key,double min,double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key,min,max);
    }

    /**
     * ZSet集合通过索引区间获取元素
     * @param key 键
     * @param start 区间最小
     * @param end 区间最大 （-1为最大）
     */
    public Set<Object> zRange(String key,long start,long end) {
        return redisTemplate.opsForZSet().range(key,start,end);
    }

    /**
     * 自增1
     * @param key 键
     */
    public int incr(String key) {
        return redisTemplate.opsForValue().increment(key).intValue();
    }
}
