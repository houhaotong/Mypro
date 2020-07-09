package com.mypro.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 秒杀工具类
 * @author houhaotong
 */
public class SecKillUtils {

    private static final ThreadLocalRandom RANDOM=ThreadLocalRandom.current();

    /** 时间格式转换 */
    private static final SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("yyyyMMddHHmmss");

    /** 定义锁对象 */
    private static final Lock LOCK=new ReentrantLock();

    /** 雪花算法 */
    private static final SnowFlake SNOW_FLAKE=new SnowFlake(2,3);

    private static String getNowDateString(){
        return DATE_FORMAT.format(new Date());
    }
    /**
     * 生成订单编号（时间+随机6位数+4位用户id）
     * @param userId 用户id
     * @return 订单编号
     */
    public static String getOrderCode(Long userId){
        String orderCode=null;
        //加锁
        LOCK.lock();
        try {
            String nowTime=DATE_FORMAT.format(new Date());
            if (userId!=null){
                if(userId.intValue()<10){
                    orderCode=nowTime+getRandomString(6)+"000"+userId;
                }
                else if (userId.intValue()<100){
                    orderCode=nowTime+getRandomString(6)+"00"+userId;
                }
                else if (userId.intValue()<1000){
                    orderCode=nowTime+getRandomString(6)+"0"+userId;
                }
                orderCode=nowTime+getRandomString(6)+userId;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //解锁
            LOCK.unlock();
        }
        return orderCode;
    }

    /**
     * 生成随机数
     * @param num 长度
     * @return 随机数字符串
     */
    public static String getRandomString(final int num){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<num;i++){
            stringBuffer.append(RANDOM.nextInt(9));
        }
        return stringBuffer.toString();
    }

}
