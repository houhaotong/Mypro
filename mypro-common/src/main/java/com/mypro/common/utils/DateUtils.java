package com.mypro.common.utils;

/**
 * 日期工具类
 * @author houhaotong
 */
public class DateUtils {

    /**
     * 获取当前时间戳（秒）
     */
    public static long getDateStamp(){
        return System.currentTimeMillis()/1000;
    }
}
