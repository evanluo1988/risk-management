package com.springboot.enums;

import org.springframework.util.StringUtils;

/**
 * 角色
 * @Author 刘宏飞
 * @Date 2020/11/26 13:29
 * @Version 1.0
 */
public enum RoleEnum {
    SYS_ADMIN("sys.admin", "系统管理员"),
    REGION_ADMIN("region.admin", "区级管理员"),
    STREET_ADMIN("street.admin", "街道办管理员");
    private String name;
    private String description;

    RoleEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 根据name获取RoleEnum
     *
     * @param name
     * @return
     */
    public static RoleEnum nameOf(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        RoleEnum[] values = RoleEnum.values();
        for (RoleEnum value : values) {
            if (name.equalsIgnoreCase(value.getName())) {
                return value;
            }
        }
        return null;
    }
}
