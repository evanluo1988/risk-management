package com.springboot.model;

import com.springboot.domain.Permission;
import com.springboot.domain.Role;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/11/24 17:23
 * @Version 1.0
 */
public class RolePerm extends Role{
    private List<Permission> permissionList;

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}
