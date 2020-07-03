package com.mypro.system.service.Impl;

import com.mypro.system.domain.SysUserOnline;
import com.mypro.system.mapper.ISysUserOnlineMapper;
import com.mypro.system.service.ISysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 在线用户 业务层
 * @author houhaotong
 */
@Service
public class SysUserOnlineServiceImpl implements ISysUserOnlineService {

    @Autowired
    ISysUserOnlineMapper userOnlineMapper;

    @Override
    public void insertUserOnline(SysUserOnline userOnline) {
        userOnlineMapper.insertUserOnline(userOnline);
    }
}
