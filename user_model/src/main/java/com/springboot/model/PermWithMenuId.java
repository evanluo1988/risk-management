package com.springboot.model;

import com.springboot.domain.Permission;

/**
 * @author evan
 */
public class PermWithMenuId extends Permission {
    private Long menuId;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
