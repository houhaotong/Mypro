package com.mypro.system.service;

import com.mypro.system.domain.SecItem;
import com.mypro.system.domain.SecOrderAndUserInfo;

import java.util.List;

/**
 * 商品 业务层接口
 * @author houhaotong
 */
public interface ISecService {

    /**
     * 获取所有商品
     * @return list
     */
    public List<SecItem> selectAll();

    /**
     * 获取商品详情
     * @param itemId 商品id
     * @return 信息
     */
    public SecItem selectItemByItemId(Long itemId);

    /**
     * 用户抢购
     * @param itemId 商品id
     * @param loginName 登录名
     * @return 秒杀是否成功
     */
    public boolean kill(Long itemId, String loginName);

    /**
     * 通过订单id获取订单详情
     * @param orderId 订单id
     * @return 结果
     */
    public SecOrderAndUserInfo selectOrderByOrderId(String orderId);

    /**
     * 订单支付
     * @param orderId 订单id
     * @return true成功
     */
    public boolean orderPay(String orderId);

    /**
     * 获取用户订单
     * @param userId 用户id
     * @return 订单集合
     */
    public List<SecOrderAndUserInfo> selectOrdersByUserId(Long userId);
}
