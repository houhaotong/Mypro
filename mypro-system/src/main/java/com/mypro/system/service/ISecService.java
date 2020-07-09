package com.mypro.system.service;

import com.mypro.system.domain.SecItem;

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
}
