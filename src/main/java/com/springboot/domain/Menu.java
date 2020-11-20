package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author evan
 * 菜单
 */
@TableName("menus")
public class Menu extends BaseDomain {

    private static final long serialVersionUID = -1649944521084711686L;
    private Long parentId;
    private String menuName;
    private String menuUrl;
    private String menuType;
    private Boolean enable;

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

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
