package com.mypro.system.mapper;

import com.mypro.system.domain.SysRole;

import java.util.List;

/**
 * 角色信息 数据层
 * @author houhaotong
 */
public interface ISysRoleMapper {

    /**
     * 查询所有角色
     * @return 角色列表
     */
    public List<SysRole> selectAllRole();

    /**
     * 根据用户ID查询角色
     * @param userId 用户id
     * @return 结果
     */
    public List<SysRole> selectRolesByUserId(Long userId);

    /**
     * 根据角色id查询
     * @param roleId 角色id
     * @return 信息
     */
    public SysRole selectRoleByRoleId(Long roleId);

    /**
     * 更新角色
     * @param role 角色
     */
    public void updateRole(SysRole role);

    /**
     * 删除角色
     * @param roleId 角色id
     */
    public void deleteByRoleId(Long roleId);
}
