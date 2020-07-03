package com.mypro.system.service.Impl;

import com.mypro.system.domain.SysRole;
import com.mypro.system.mapper.ISysRoleMapper;
import com.mypro.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现类
 * @author houhaotong
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private ISysRoleMapper roleMapper;

    @Override
    public List<SysRole> selectAllRole() {
       return roleMapper.selectAllRole();
    }

    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        //该用户所具有的角色
        List<SysRole> userRoles = roleMapper.selectRolesByUserId(userId);
        //所有的角色
        List<SysRole> roles = roleMapper.selectAllRole();
        //将所有的角色集合中，该用户所具有的角色flag设置为true
        for (SysRole role : roles) {
            for (SysRole userRole : userRoles) {
                if(role.getRoleId().longValue()==userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                }
            }
        }
        return roles;
    }
}
