package com.mypro.system.mapper;

import com.mypro.system.domain.SysMenu;

import java.util.List;

/**
 * 菜单信息 数据层
 * @author houhaotong
 */
public interface ISysMenuMapper {

    /**
     * 根据登录名查询菜单
     * @param loginName 登录名
     * @return 菜单集合
     */
    public List<SysMenu> selectMenuByLoginName(String loginName);

    /**
     * 查询所有菜单
     * @return 菜单
     */
    public List<SysMenu> selectAllMenu();
}
