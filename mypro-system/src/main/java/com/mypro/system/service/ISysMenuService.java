package com.mypro.system.service;

import com.mypro.system.domain.SysMenu;

import java.util.List;

/**
 * 菜单服务层接口
 * @author houhaotong
 */
public interface ISysMenuService {

    /**
     * 根据登录名查询菜单
     * @param loginName 登录名
     * @param isAdmin 是否为管理员
     * @return 菜单集合
     */
    public List<SysMenu> selectMenuByLoginName(String loginName,boolean isAdmin);
}
