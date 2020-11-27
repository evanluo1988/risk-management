package com.springboot.model;

import com.springboot.domain.User;

import java.util.List;

public class UserRoleDomain extends User {
    private List<RolePerm> roleList;

    public List<RolePerm> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RolePerm> roleList) {
        this.roleList = roleList;
    }
}


