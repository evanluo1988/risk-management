package com.springboot.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/26 14:53
 */
@Data
public class UserInfoVo {
    private Long id;
    private String loginName;
    private String userName;
    private String phone;
    private Long areaId;
    private String areaName;
    private String roleName;
}
