package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author evan
 * 用户
 */
@Data
@TableName("users")
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class User extends BaseDomain {
    private static final long serialVersionUID = -5971060169689289963L;

    private String userName;
    private String password;
    private String enable;
    private Long areaId;

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

}
