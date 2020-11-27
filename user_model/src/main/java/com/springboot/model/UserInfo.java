package com.springboot.model;

import com.springboot.domain.User;

public class UserInfo extends User {
    private String areaName;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
