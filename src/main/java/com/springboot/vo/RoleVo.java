package com.springboot.vo;

import java.util.List;

public class RoleVo {
    private Long id;
    private String roleName;
    private String roleDescribe;

    private List<PermVo> permissionVoList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<PermVo> getPermissionVoList() {
        return permissionVoList;
    }

    public void setPermissionVoList(List<PermVo> permissionVoList) {
        this.permissionVoList = permissionVoList;
    }
}
