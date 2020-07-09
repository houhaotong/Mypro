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

    /** 是否处理过 0否 1是 */
    private char isdone;

    /** 更新者 */
    private String updateBy;

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

    public char getIsdone() {
        return isdone;
    }

    public void setIsdone(char isdone) {
        this.isdone = isdone;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
