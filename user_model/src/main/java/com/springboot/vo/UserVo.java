package com.springboot.vo;

import lombok.Data;

import javax.servlet.Registration;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserVo implements Serializable {
    /**
     * 用户登录
     */
    public interface LoginGroup {
    }

    /**
     * 创建用户
     */
    public interface UserAddGroup {
    }

    private static final long serialVersionUID = 4438086802592253397L;
    private Long id;
    @NotBlank(groups = {LoginGroup.class, UserAddGroup.class}, message = "用户名不能为空")
    private String userName;
    @NotNull(groups = {UserAddGroup.class}, message = "区域信息不能为空")
    private Long areaId;

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
}
