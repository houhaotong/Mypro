package com.mypro.system.domain;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.mypro.common.core.domain.BaseEntities;

import java.util.List;

/**
 * 菜单实体类
 * @author houhaotong
 */
public class SysMenu extends BaseEntities {

    /** 菜单ID */
    private Long menuId;

    /** 菜单名称 */
    private String menuName;

    /** 父菜单Id */
    private Long parentId;

    /** 排序顺序编号 */
    private Integer orderNum;

    /** 菜单Url */
    private String url;

    /** 菜单的class,menuItem为页签 menuBlank为新窗口 */
    private String target;

    /** 菜单类型  */
    private String menuType;

    /** 菜单的可见性 0显示 1隐藏 */
    private String visible;

    /** 权限字符串 */
    private String perms;

    /** 菜单图标 */
    private String icon;

    /** 子菜单 */
    private List<SysMenu> sunMenu;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<SysMenu> getSunMenu() {
        return sunMenu;
    }

    public void setSunMenu(List<SysMenu> sunMenu) {
        this.sunMenu = sunMenu;
    }
}
