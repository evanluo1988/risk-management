package com.springboot.model;

import com.springboot.domain.Role;
import com.springboot.domain.User;

import java.util.List;

public class UserRole extends User {
    private List<Role> roleList;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}


