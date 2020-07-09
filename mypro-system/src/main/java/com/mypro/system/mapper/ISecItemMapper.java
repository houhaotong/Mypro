package com.mypro.system.mapper;

import com.mypro.system.domain.SecItem;

import java.util.List;

/**
 * 商品 持久层
 * @author houhaotong
 */
public interface ISecItemMapper {

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
     * 更新商品
     * @param item 商品
     * @return 结果
     */
    public int updateItem(SecItem item);
}
