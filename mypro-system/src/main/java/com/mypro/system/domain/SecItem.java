package com.mypro.system.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品实体类
 * @author houhaotong
 */
public class SecItem implements Serializable {

    /** 商品id */
    private Long itemId;

    /** 商品名称 */
    private String itemName;

    /** 商品库存 */
    private Long itemStock;

    /** 开始时间 */
    private Date startTime;

    /** 结束时间 */
    private Date endTime;

    /** 是否有效 0无效 1有效 */
    private char isActive;

    /** 是否处于可以抢购 0不可以 1可以*/
    private char isok;

    /** 商品价格 */
    private Long price;

    /** 商品图片名 */
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemStock() {
        return itemStock;
    }

    public void setItemStock(Long itemStock) {
        this.itemStock = itemStock;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public char getIsActive() {
        return isActive;
    }

    public void setIsActive(char isActive) {
        this.isActive = isActive;
    }

    public char getIsok() {
        return isok;
    }

    public void setIsok(char isok) {
        this.isok = isok;
    }
}
