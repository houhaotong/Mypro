package com.mypro.system.mapper;

import com.mypro.system.domain.SysUser;

import java.util.List;

/**
 *用户信息 数据层
 * @author mypro
 */
public interface ISysUserMapper {

    /**
     * 根据登录名来获取用户
     * @param loginname 登录名
     * @return 用户对象信息
     */
    public SysUser selectUserByLoginName(String loginname);

    /**
     * 查询所有用户 持久层
     * @return 用户
     */
    public List<SysUser> selectAllUser();

    /**
     * 根据ID查询
     * @param userId id
     */
    public SysUser selectUserByUserId(Long userId);

    /**
     * 更新用户信息
     * @param user 用户
     * @return 结果
     */
    public int updateByUser(SysUser user);

    /**
     * 根据用户ID删除用户信息
     * @param userId 用户id
     */
    public void deleteByUserId(Long userId);

    /**
     * 检查登录名是否唯一
     * @param loginName 登录名
     * @return true唯一，false已存在
     */
    public int checkLoginNameUnique(String loginName);

    /**
     * 插入用户
     * @param user 用户
     */
    public void insertUser(SysUser user);

}

