package com.mypro.common.utils;

/**
 * 存放静态常量
 * @author houhaotong
 */
public final class MyproCostant {

    /*              订单相关            */

    /** 订单未处理状态 */
    public static final char ORDER_NO_OPERATION='0';

    /** 订单已处理状态 */
    public static final char ORDER_OPERATED ='1';

    /** 订单已过期状态 */
    public static final char ORDER_EXPIRED='2';

    /*              Redis相关            */

    /** 所有商品信息 */
    public static final String REDIS_ITEM_KEY ="seckill_item";

    /** 商品库存 */
    public static final String REDIS_STOCK_KEY="stock:";
}
