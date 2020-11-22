package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author evan
 * 权限
 */
@TableName("permissions")
public class Permission extends BaseDomain {
    private static final long serialVersionUID = -4198418960175593808L;

    private String permName;
    private String permDescribe;
    private String enable;

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getPermDescribe() {
        return permDescribe;
    }

    public void setPermDescribe(String permDescribe) {
        this.permDescribe = permDescribe;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
