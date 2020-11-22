package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

/**
 * @author evan
 * 菜单
 */
@TableName("menus")
public class Menu extends BaseDomain {

    private static final long serialVersionUID = -1649944521084711686L;

    @TableField(value = "parent_id")
    private Long parentId;
    @TableField(value = "menu_name")
    private String menuName;
    @TableField(value = "menu_describe")
    private String menuDescribe;
    @TableField(value = "menu_url")
    private String menuUrl;
    @TableField(value = "menu_type")
    private String menuType;
    @TableField(value = "menu_level")
    private int menuLevel;
    @TableField(value = "enable")
    private String enable;

    private List<Permission> permissionList;
    private List<Menu> subMenuList;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDescribe() {
        return menuDescribe;
    }

    public void setMenuDescribe(String menuDescribe) {
        this.menuDescribe = menuDescribe;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public int getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(int menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public List<Menu> getSubMenuList() {
        return subMenuList;
    }

    public void setSubMenuList(List<Menu> subMenu) {
        this.subMenuList = subMenu;
    }
}
