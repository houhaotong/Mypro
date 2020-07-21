package com.mypro.system.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单 实体类
 * @author houhaotong
 */
public class SecOrder implements Serializable {

    /** 订单id */
    private String orderId;

    /** 商品id */
    private Long itemId;

    /** 用户id */
    private Long userId;

    /** 创建时间 */
    private Date createTime;

    /** 订单状态 0未支付 1已支付 2已取消 */
    private char state;

    /** 更新者 */
    private String updateBy;

    /** 订单金额 */
    private Long price;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
