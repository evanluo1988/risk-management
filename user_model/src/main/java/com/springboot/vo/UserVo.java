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

    /**
     * 修改用户
     */
    public interface UserUpdateGroup {
    }

    /**
     * 修改用户密码
     */
    public interface UserUpdatePasswordGroup {
    }

    private static final long serialVersionUID = 4438086802592253397L;
    @NotNull(groups = {UserUpdateGroup.class}, message = "用户ID不能为空")
    private Long id;
    @NotBlank(groups = {LoginGroup.class, UserAddGroup.class, UserUpdateGroup.class}, message = "用户名不能为空")
    private String userName;
    @NotNull(groups = {UserAddGroup.class, UserUpdateGroup.class}, message = "区域信息不能为空")
    private Long areaId;
    private String enable;
    private String areaName;

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

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
