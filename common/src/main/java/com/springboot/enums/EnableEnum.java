package com.springboot.enums;

/**
 * @Author 刘宏飞
 * @Date 2020/11/24 15:19
 * @Version 1.0
 */
public enum EnableEnum {
    Y("Y"),
    N("N");
    private String code;

    EnableEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
