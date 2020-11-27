package com.springboot.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegUserVo extends UserVo {
    private static final long serialVersionUID = -4914078612150276709L;

    @NotBlank(groups = {UserVo.LoginGroup.class}, message = "密码不能为空")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
