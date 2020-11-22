package com.springboot.vo;

import java.util.Set;

public class UserWithRoleVo {
    private Long id;
    private String userName;
    private Set<RoleVo> roleVoSet;
    private Set<PermVo> permVoSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<RoleVo> getRoleVoSet() {
        return roleVoSet;
    }

    public void setRoleVoSet(Set<RoleVo> roleVoSet) {
        this.roleVoSet = roleVoSet;
    }

    public Set<PermVo> getPermVoSet() {
        return permVoSet;
    }

    public void setPermVoSet(Set<PermVo> permVoSet) {
        this.permVoSet = permVoSet;
    }
}
