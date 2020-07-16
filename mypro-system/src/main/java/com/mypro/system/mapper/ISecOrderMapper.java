package com.mypro.system.mapper;

import com.mypro.system.domain.SecOrder;
import com.mypro.system.domain.SecOrderAndUserInfo;

import java.util.List;

/**
 * 订单 持久层
 * @author houhaotong
 */
public interface ISecOrderMapper {

    /**
     * 插入订单
     * @param order 订单
     */
    public void insertOrder(SecOrder order);

    /**
     * 通过订单id查找
     * @param orderId 订单id
     * @return 结果
     */
    public SecOrder selectOrderByOrderId(String orderId);

    /**
     * 通过订单id查找包括用户信息
     * @param orderId 订单id
     * @return 结果
     */
    public SecOrderAndUserInfo selectOrderAndUserByOrderId(String orderId);

    /**
     * 通过用户id查找订单
     * @param userId 用户id
     * @return 订单信息
     */
    public List<SecOrder> selectOrderByUserId(Long userId);
}
