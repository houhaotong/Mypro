package com.mypro.system.service.Impl;

import com.alibaba.fastjson.JSON;
import com.mypro.common.utils.MyproCostant;
import com.mypro.common.utils.RedisUtils;
import com.mypro.common.utils.SecKillUtils;
import com.mypro.system.domain.SecItem;
import com.mypro.system.domain.SecOrder;
import com.mypro.system.domain.SecOrderAndUserInfo;
import com.mypro.system.domain.SysUser;
import com.mypro.system.mapper.ISecItemMapper;
import com.mypro.system.mapper.ISecOrderMapper;
import com.mypro.system.mapper.ISysUserMapper;
import com.mypro.system.service.IRabbitSendService;
import com.mypro.system.service.ISecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 秒杀 业务层
 *
 * @author houhaotong
 */
@Service
@Transactional
public class SecServiceImpl implements ISecService {

    private final Logger log = LoggerFactory.getLogger(SecServiceImpl.class);
    @Autowired
    ISecItemMapper itemMapper;

    @Autowired
    ISysUserMapper userMapper;

    @Autowired
    ISecOrderMapper orderMapper;

    @Autowired
    IRabbitSendService sendService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * redis工具类
     */
    @Autowired
    RedisUtils redisUtils;

    private Lock lock = new ReentrantLock();

    @Override
    public List<SecItem> selectAll() {
        List<SecItem> items = (List<SecItem>) redisTemplate.opsForValue().get(MyproCostant.REDIS_ITEM_KEY);
        if (items == null) {
            lock.lock();
            try {
                //双重检测锁
                items = (List<SecItem>) redisTemplate.opsForValue().get(MyproCostant.REDIS_ITEM_KEY);
                if (items == null) {
                    items = itemMapper.selectAll();
                    for (SecItem item : items) {
                        isOk(item);
                    }
                    redisTemplate.opsForValue().set(MyproCostant.REDIS_ITEM_KEY, items);
                    return items;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        List<SecItem> secItems = JSON.parseArray(JSON.toJSONString(items), SecItem.class);
        initItemStock(secItems);
        return secItems;
    }

    /**
     * 初始化redis库存缓存
     *
     * @param items 商品集合
     */
    public void initItemStock(List<SecItem> items) {
        for (SecItem item : items) {
            redisTemplate.opsForValue().setIfAbsent(MyproCostant.REDIS_STOCK_KEY + item.getItemId(), item.getItemStock());
        }
    }

    @Override
    public SecItem selectItemByItemId(Long itemId) {
        SecItem item = itemMapper.selectItemByItemId(itemId);
        Long stock = redisUtils.getLong(MyproCostant.REDIS_STOCK_KEY + itemId);
        item.setItemStock(stock==null?0:stock);
        isOk(item);
        return item;
    }

    @Override
    public boolean kill(Long itemId, String loginName) {
        SysUser user = userMapper.selectUserByLoginName(loginName);
        SecItem item = selectItemByItemId(itemId);
        if (user.getUserId() != null && item != null) {
            if (!boughtOrNot(user.getUserId(), itemId)) {
                if (item.getIsok() == '1') {
                    return generateOrder(item, user.getUserId());
                }
            }
        }
        return false;
    }

    @Override
    public SecOrderAndUserInfo selectOrderByOrderId(String orderId) {
        return orderMapper.selectOrderAndUserByOrderId(orderId);
    }

    @Override
    public boolean orderPay(String orderId) {
        SecOrder order = orderMapper.selectOrderByOrderId(orderId);
        if (order.getState() == '0') {
            if (order != null) {
                SysUser user = userMapper.selectUserByUserId(order.getUserId());
                if (user != null) {
                    user.setMoney(user.getMoney() - order.getPrice());
                    order.setState('1');
                    orderMapper.updateOrder(order);
                    userMapper.updateByUser(user);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<SecOrderAndUserInfo> selectOrdersByUserId(Long userId) {
        return orderMapper.selectOrdersAndUserByUserId(userId);
    }

    /**
     * 测试用下单v2
     *
     * @param itemId 商品id
     * @param userId 用户id
     * @return true false
     */
    @Override
    public boolean testKillV2(Long itemId, Long userId) {
        SecItem item = selectItemByItemId(itemId);
        if (userId != null && item != null) {
            if (!boughtOrNot(userId, itemId)) {
                if (item.getIsok() == '1') {
                    return generateOrder(item, userId);
                }
            }
        }
        return false;
    }

    /**
     * 判断是否买过同一个商品
     *
     * @param userId 用户id
     * @param itemId 商品id
     * @return true false
     */
    private boolean boughtOrNot(Long userId, Long itemId) {
        //从redis检测是否有购买过的标识
        if (redisTemplate.hasKey("order:" + userId + ":" + itemId)) {
            return true;
        }
        List<SecOrder> orders = orderMapper.selectOrderByUserId(userId);
        if (orders != null && orders.size() != 0) {
            for (SecOrder order : orders) {
                if (order.getItemId().longValue() == itemId) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * 产生订单
     *
     * @param item   商品信息
     * @param userId 用户id
     * @return 结果
     */
    public boolean generateOrder(SecItem item, Long userId) {
        SecOrder order = new SecOrder();
        order.setOrderId(SecKillUtils.getOrdeIdBySnow());
        order.setState('0');
        order.setCreateTime(new Date());
        order.setItemId(item.getItemId());
        order.setUserId(userId);
        order.setPrice(item.getPrice());
        if (updateStock(item.getItemId(), userId, 1)) {
            orderMapper.insertOrder(order);
            //发送邮件消息
            //sendService.sendMsg(order.getOrderId());
            //发送订单消息到死信队列
            //sendService.sendDeadMsg(order.getOrderId());
            return true;
        }
        return false;
    }

    /**
     * 更新库存
     *
     * @param itemId 商品id
     * @param i      改变库存的数量
     * @param userId 用户id
     * @return 是否修改成功
     */
    private boolean updateStock(Long itemId, Long userId, int i) {
        String stockKey = MyproCostant.REDIS_STOCK_KEY + itemId;
        if (itemId != null) {
            //判断是否有库存数据
            if (redisTemplate.hasKey(stockKey)) {
                Long stock = redisUtils.getLong(stockKey);
                if (stock != null && stock > 0) {
                    //redis事务
                    List<Object> results = redisTemplate.execute(new SessionCallback<List<Object>>() {
                        @Override
                        public <K, V> List<Object> execute(RedisOperations<K, V> operations) throws DataAccessException {
                            redisTemplate.watch(stockKey);
                            operations.multi();
                            redisTemplate.opsForValue().decrement(stockKey, i);
                            return operations.exec();
                        }
                    });
                    //事务提交成功则结果不为空
                    if (results != null && !results.isEmpty()) {
                        Long decr_stock = Long.parseLong(String.valueOf(redisTemplate.opsForValue().get(stockKey)));
                        if (decr_stock >= 0) {
                            //插入购买标识,如果已存在
                            if (redisTemplate.opsForValue().setIfAbsent("order:" + userId + ":" + itemId, 1, Duration.ofMinutes(30))) {
                                //库存为空时更新数据库
                                if (decr_stock == 0) {
                                    SecItem item = itemMapper.selectItemByItemId(itemId);
                                    item.setItemStock(0L);
                                    int result = itemMapper.updateItem(item);
                                    if (result != 1) {
                                        log.warn("库存更新失败");
                                    } else {
                                        log.info("库存已经为0");
                                        //删除缓存的库存数据
                                        redisTemplate.delete(stockKey);
                                        //删除缓存的商品信息
                                        redisTemplate.delete(MyproCostant.REDIS_ITEM_KEY);
                                    }
                                }
                                return true;
                            } else {
                                //如果购买标识已存在，则库存回退
                                redisTemplate.opsForValue().increment(stockKey, i);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断是否有效然后设置isok属性
     */
    public void isOk(SecItem item) {
        if (item != null) {
            Date now = new Date();
            if (now.compareTo(item.getStartTime()) > 0 && now.compareTo(item.getEndTime()) < 0 && item.getItemStock() > 0) {
                item.setIsok('1');
            } else {
                item.setIsok('0');
            }
        }
    }
}
