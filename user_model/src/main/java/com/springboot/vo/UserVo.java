package com.springboot.vo;

import java.io.Serializable;

public class UserVo implements Serializable {
    private static final long serialVersionUID = 4438086802592253397L;
    private long id;
    private String userName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
