package com.mypro.system.service.Impl;

import com.mypro.common.utils.DateUtils;
import com.mypro.common.utils.RedisUtils;
import com.mypro.common.utils.SecurityUtils;
import com.mypro.system.domain.SysUser;
import com.mypro.system.domain.SysUserOnline;
import com.mypro.system.domain.SysUserRole;
import com.mypro.system.mapper.ISysUserMapper;
import com.mypro.system.mapper.ISysUserOnlineMapper;
import com.mypro.system.mapper.ISysUserRoleMapper;
import com.mypro.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * User服务实现类
 * @author houhaotong
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    ISysUserMapper userMapper;

    @Autowired
    ISysUserRoleMapper userRoleMapper;

    @Autowired
    ISysUserOnlineMapper userOnlineMapper;

    @Autowired
    RedisUtils redisUtils;

    //默认所有用户的redis键
    private final static String DEFAULT_REDIS_USERKEY="allUser";
    /**锁*/
    Lock lock=new ReentrantLock();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.selectUserByLoginName(username);
        if(user==null){
            throw new UsernameNotFoundException("账户不存在");
        }
        //记录登录日志
        loginRecord(user);
        return User.withUserDetails(user).build();
    }

    /**
     * 记录登录日志
     */
    private void loginRecord(SysUser user) {
        //3小时后的时间戳
        double timeStamp=DateUtils.getDateStamp()+60*60*3;
        //添加当前用户进redis的ZSet集合在线用户，score为当前时间戳+3小时，如果不logout则3小时后扫描清除
        redisUtils.zAdd("onlineUser",timeStamp,user.getLoginName());
        //清除过期用户
        redisUtils.zRemByScore("onlineUser",0,DateUtils.getDateStamp()-1);
        //更新最后登录时间
        user.setLoginDate(new Date());
        updateUser(user);
        //增加访问量
        redisUtils.incr("pageView");
    }

    @Override
    public SysUser selectUserByLoginName(String loginName) {
        return userMapper.selectUserByLoginName(loginName);
    }

    @Override
    public List<SysUser> selectAllUser() {
        //使用redis获取数据
        List<SysUser> users = (List<SysUser>) redisUtils.get(DEFAULT_REDIS_USERKEY);
        //双重检测锁
        if (users==null) {
            lock.lock();
            try {
                users = (List<SysUser>) redisUtils.get(DEFAULT_REDIS_USERKEY);
                if (users == null) {
                    users = userMapper.selectAllUser();
                    redisUtils.set(DEFAULT_REDIS_USERKEY,users);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return users;
    }

    @Override
    public SysUser selectUserByUserId(Long userId) {
        return userMapper.selectUserByUserId(userId);
    }

    /**
     * 修改用户信息和关联
     * @param user 用户
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        if(user.getRoleIds()!=null){
            //删除userRole表中的关联
            userRoleMapper.deleteUserRoleByUserId(user.getUserId());
            //添加userRole关联
            insertUserRole(user.getUserId(),user.getRoleIds());
        }
        redisUtils.del(DEFAULT_REDIS_USERKEY);
        //修改用户信息
        return userMapper.updateByUser(user);
    }

    /**
     * 批量删除用户
     * @param userIds 用户ids
     */
    @Override
    public void deleteByUserIds(String userIds) {
        String[] strings = userIds.split(",");
        Long[] ids=new Long[strings.length];
        for (int i = 0; i < ids.length; i++) {
            ids[i]=Long.valueOf(strings[i]);
            //删除userRole关联表关联
            userRoleMapper.deleteUserRoleByUserId(ids[i]);
            userMapper.deleteByUserId(ids[i]);
        }
        redisUtils.del(DEFAULT_REDIS_USERKEY);
    }

    /**
     * 检查是否唯一
     * @param loginName 登录名
     * @return true唯一，false已存在
     */
    @Override
    public boolean checkLoginNameUnique(String loginName) {
        int i = userMapper.checkLoginNameUnique(loginName);
        return i == 0;
    }

    /**
     * 插入用户
     * @param user 用户
     */
    @Override
    public void insertUser(SysUser user) {
        //插入user数据
        userMapper.insertUser(user);
        if(user.getUserId()==null){
            user.setUserId(userMapper.selectUserByLoginName(user.getLoginName()).getUserId());
        }
        //加入userRole关联
        insertUserRole(user.getUserId(),user.getRoleIds());
        redisUtils.del(DEFAULT_REDIS_USERKEY);
    }

    /**
     * 获取在线人数
     * @return 人数
     */
    @Override
    public int getOnlineNum() {
        return redisUtils.zCard("onlineUser");
    }

    /**
     * 获取在线用户信息
     * @return 用户信息
     */
    @Override
    public List<SysUser> getOnlineUser() {
        Set<Object> set = redisUtils.zRange("onlineUser",0,-1);
        String[] loginNames = set.toArray(new String[set.size()]);
        List<SysUser> users=new ArrayList<SysUser>();
        for (String loginName : loginNames) {
            SysUser user = selectUserByLoginName(loginName);
            users.add(user);
        }
        return users;
    }

    @Override
    public int getPageView() {
        Object pageView = redisUtils.get("pageView");
        if(pageView!=null){
            return (int)pageView;
        }
        return 0;
    }

    /**
     * 判断和数据库中密码是否一致
     * @param originPwd 原始密码
     * @param pwd 密码
     * @return true一致
     */
    @NotNull
    public boolean pwdIsRightOrNot(String originPwd, String pwd) {
        return new BCryptPasswordEncoder().matches(pwd,originPwd);
    }

    @Override
    public boolean updatePassword(Long userId,String oldPwd, String newPwd) {
        SysUser user = userMapper.selectUserByUserId(userId);
        if (user!=null){
            //旧密码匹配则修改新密码
            if (pwdIsRightOrNot(user.getPassword(),oldPwd)){
                user.setPassword(new BCryptPasswordEncoder().encode(newPwd));
                updateUser(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public Long selectUserIdByLoginName(String loginName) {
        SysUser user = selectUserByLoginName(loginName);
        return user.getUserId();
    }

    /**
     * 添加角色用户关联
     * @param userId 用户id
     * @param roleIds 需要的角色id
     */
    public void insertUserRole(Long userId,Long[] roleIds){
        if(roleIds!=null){
            List<SysUserRole> list=new ArrayList<SysUserRole>();
            for (Long roleId : roleIds) {
                SysUserRole userRole=new SysUserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                list.add(userRole);
            }
            if(list.size()>0){
                userRoleMapper.insertUserRole(list);
            }
        }
    }
}
