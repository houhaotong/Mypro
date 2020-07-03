package com.mypro.system.service;

import com.mypro.system.domain.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * User服务接口，实现UserDetailsService接口，使用springSecurity
 * @author houhaotong
 */
public interface ISysUserService extends UserDetailsService {

    /**
     * 根据登录名获取用户
     * @param loginName 登录名
     * @return 用户对象
     */
    public SysUser selectUserByLoginName(String loginName);

    /**
     * 查询所有用户
     * @return 用户
     */
    public List<SysUser> selectAllUser();

    /**
     * 根据userId查询
     * @param userId 用户ID
     * @return 用户
     */
    public SysUser selectUserByUserId(Long userId);

    /**
     * 修改保存用户
     * @param user 用户
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 根据用户Id批量删除用户
     * @param userIds 用户ids
     */
    public void deleteByUserIds(String userIds);

    /**
     * 检查登录名是否唯一
     * @param loginName 登录名
     * @return true为唯一，false为已存在
     */
    public boolean checkLoginNameUnique(String loginName);

    /**
     * 插入用户
     * @param user 用户
     */
    public void insertUser(SysUser user);

    /**
     * 获取在线人数
     * @return 在线人数
     */
    public int getOnlineNum();

    /**
     * 获取在线用户信息
     * @return 获取在线用户信息
     */
    public List<SysUser> getOnlineUser();
}
