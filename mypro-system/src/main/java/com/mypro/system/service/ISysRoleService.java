package com.mypro.system.service;

import com.mypro.system.domain.SysRole;

import java.util.List;

/**
 * 角色服务 业务层
 * @author houhaotong
 */
public interface ISysRoleService {

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
}
