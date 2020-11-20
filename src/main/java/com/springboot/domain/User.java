package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zx on 2020/3/7.
 */
@TableName("t_users")
public class User extends BaseDomain{
    private static final long serialVersionUID = -5971060169689289963L;

    private String name;
    private String email;
    private String password;
    private UUID personId;

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

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
