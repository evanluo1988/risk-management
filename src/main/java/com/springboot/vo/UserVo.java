package com.springboot.vo;

import java.io.Serializable;

public class UserVo implements Serializable {
    private static final long serialVersionUID = 4438086802592253397L;
    private long id;

    private String name;
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
