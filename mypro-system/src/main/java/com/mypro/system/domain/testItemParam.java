package com.mypro.system.domain;

import java.io.Serializable;

/**
 * 测试接口用，接受json字符串
 * @author houhaotong
 */
public class testItemParam implements Serializable {

    private Long userId;

    private Long itemId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
