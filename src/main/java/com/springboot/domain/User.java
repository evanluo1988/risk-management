package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author evan
 * 用户
 */
@TableName("users")
public class User extends BaseDomain {
    private static final long serialVersionUID = -5971060169689289963L;

    private String userName;
    private String password;
    private String enable;

    private List<Role> roleList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
