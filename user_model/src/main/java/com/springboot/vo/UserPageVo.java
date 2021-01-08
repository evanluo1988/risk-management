package com.springboot.vo;

import lombok.Data;


@Data
public class UserPageVo {
    private Long id;
    private String loginName;
    private String userName;
    private Long areaId;
    private String enable;
    private String phone;
    private String areaName;
}
