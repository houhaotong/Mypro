package com.mypro.system.service.Impl;

import com.mypro.system.domain.SysMenu;
import com.mypro.system.mapper.ISysMenuMapper;
import com.mypro.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu服务层
 *
 * @author houhaotong
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Autowired
    ISysMenuMapper menuMapper;

    /**
     * 通过登录名获取菜单
     * @param loginName 登录名
     * @param isAdmin 是否为管理员
     * @return 菜单集合
     */
    @Override
    public List<SysMenu> selectMenuByLoginName(String loginName, boolean isAdmin) {
        List<SysMenu> menus = new ArrayList<SysMenu>();
        if (isAdmin) {
            menus = menuMapper.selectAllMenu();
        } else {
            menus = menuMapper.selectMenuByLoginName(loginName);
        }
        return getChildrenMenu(menus,0);
    }

    /**
     * 根据父节点的ID获取所有子节点
     * @param menus 原始菜单列表
     * @param parentId 需要查找的父id，一级目录都为0，所以一般从0开始
     * @return 所有子列表
     */
    public List<SysMenu> getChildrenMenu(List<SysMenu> menus,int parentId) {
        List<SysMenu> result=new ArrayList<SysMenu>();
        for (SysMenu menu : menus) {
            //此时为一级菜单
            if(menu.getParentId()==parentId){
                //递归获取所有下级子列表的集合
                recursiveList(menus,menu);
                //每个父节点添加完所有子节点后，加入到要返回的列表
                result.add(menu);
            }
        }
        return result;
    }

    /**
     * 递归列表，获取所有下级子元素，并添加到父节点上
     * @param menus 菜单列表
     * @param menu 父节点
     */
    public void recursiveList(List<SysMenu> menus,SysMenu menu){
        List<SysMenu> childrenList = getChildrenList(menus, menu);
        menu.setSunMenu(childrenList);
        for (SysMenu child : childrenList) {
            //判断当前遍历的节点是否有子节点
            if(hasChild(menus,child)){
                recursiveList(menus,child);
            }
        }
    }
    /**
     * 获取子列表
     */
    public List<SysMenu> getChildrenList(List<SysMenu> list,SysMenu menu){
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        for (SysMenu sysMenu : list) {
            //该节点的父节点ID=所传递的节点ID时，将他添加进列表
            if(sysMenu.getParentId().longValue()==menu.getMenuId().longValue()){
                tlist.add(sysMenu);
            }
        }
        return tlist;
    }

    /**
     * 判断某个父节点是否有子列表
     *
     * @return true或false
     */
    public boolean hasChild(List<SysMenu> list,SysMenu menu) {
        return getChildrenList(list, menu).size() > 0;
    }
}
