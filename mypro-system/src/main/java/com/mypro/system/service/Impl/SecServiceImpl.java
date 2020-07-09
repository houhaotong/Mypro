package com.mypro.system.service.Impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.mypro.common.utils.SecKillUtils;
import com.mypro.system.domain.SecItem;
import com.mypro.system.domain.SecOrder;
import com.mypro.system.domain.SysUser;
import com.mypro.system.mapper.ISecItemMapper;
import com.mypro.system.mapper.ISecOrderMapper;
import com.mypro.system.mapper.ISysUserMapper;
import com.mypro.system.service.ISecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 秒杀 业务层
 * @author houhaotong
 */
@Service
@Transactional
public class SecServiceImpl implements ISecService {

    @Autowired
    ISecItemMapper itemMapper;

    @Autowired
    ISysUserMapper userMapper;

    @Autowired
    ISecOrderMapper orderMapper;

    @Override
    public List<SecItem> selectAll() {
        List<SecItem> items = itemMapper.selectAll();
        for (SecItem item : items) {
            isOk(item);
        }
        return items;
    }

    @Override
    public SecItem selectItemByItemId(Long itemId) {
        SecItem item = itemMapper.selectItemByItemId(itemId);
        isOk(item);
        return item;
    }

    @Override
    public boolean kill(Long itemId, String loginName) {
        SysUser user = userMapper.selectUserByLoginName(loginName);
        SecItem item = selectItemByItemId(itemId);
        if(!boughtOrNot(user.getUserId(),itemId)) {
            if (user != null && item != null) {
                if (item.getIsok() == '1') {
                    return generateOrder(item, user.getUserId());
                }
            }
        }
        return false;
    }

    /**
     * 判断是否买过同一个商品
     * @param userId 用户id
     * @param itemId 商品id
     * @return true false
     */
    private boolean boughtOrNot(Long userId, Long itemId) {
        List<SecOrder> orders = orderMapper.selectOrderByUserId(userId);
        if (orders!=null) {
            for (SecOrder order : orders) {
                if (order.getItemId().longValue() == itemId) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 产生订单
     * @param item 商品信息
     * @param userId 用户id
     * @return 结果
     */
    public boolean generateOrder(SecItem item,Long userId){
        SecOrder order=new SecOrder();
        order.setOrderId(SecKillUtils.getOrderCode(userId));
        order.setIsdone('0');
        order.setCreateTime(new Date());
        order.setItemId(item.getItemId());
        order.setUserId(userId);
        if (updateStock(item, 1)){
            orderMapper.insertOrder(order);
            return true;
        }
        return false;
    }
    /**
     * 更新库存
     * @param item 商品信息
     * @param i 改变库存的数量
     * @return 是否修改成功
     */
    private boolean updateStock(SecItem item, int i) {
        if (item!=null) {
            if(item.getItemStock()>0) {
                item.setItemStock(item.getItemStock() - i);
                if (itemMapper.updateItem(item) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /** 判断是否有效然后设置isok属性 */
    public void isOk(SecItem item){
        if (item!=null) {
            Date now = new Date();
            if (now.compareTo(item.getStartTime()) > 0 && now.compareTo(item.getEndTime()) < 0 && item.getItemStock() > 0) {
                item.setIsok('1');
            } else {
                item.setIsok('0');
            }
        }
    }
}
