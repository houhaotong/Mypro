package com.mypro.system.mapper;

import com.mypro.system.domain.SysUserRole;

import java.util.List;

/**
 * User与Role关联 数据层
 * @author houhaotong
 */
public interface ISysUserRoleMapper {

    /**
     * 根据UserID删除关联
     * @param userId 用户id
     * @return 影响行数
     */
    public int deleteUserRoleByUserId(Long userId);

    /**
     * 批量插入数据
     * @param userRoles userRole集合
     * @return 结果
     */
    public int insertUserRole(List<SysUserRole> userRoles);

    /**
     * 根据roleId删除关联
     * @param roleId 角色id
     */
    public void deleteUserRoleByRoleId(Long roleId);
}
