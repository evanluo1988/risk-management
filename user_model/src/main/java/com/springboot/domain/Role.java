package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

/**
 * @author evan
 * 角色
 */
@TableName("roles")
public class Role extends BaseDomain {
    private static final long serialVersionUID = 7214947818602354972L;

    private String roleName;
    private String roleDescribe;
    private String enable;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
