package com.mypro.system.service;

import com.mypro.system.domain.SysUserOnline;

/**
 * 在线用户服务接口
 * @author houhaotong
 */
public interface ISysUserOnlineService {

    /**
     * 插入在线用户信息
     * @param userOnline 用户信息
     */
    public void insertUserOnline(SysUserOnline userOnline);
}
