package com.mypro.system.mapper;

import com.mypro.system.domain.SysUserOnline;

/**
 * 在线用户 数据层
 * @author houhaotong
 */
public interface ISysUserOnlineMapper {

    /**
     * 插入在线用户信息
     * @param userOnline 用户信息
     */
    public void insertUserOnline(SysUserOnline userOnline);
}
