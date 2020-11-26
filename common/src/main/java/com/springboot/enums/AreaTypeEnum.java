package com.springboot.enums;

/**
 * @Author 刘宏飞
 * @Date 2020/11/26 11:27
 * @Version 1.0
 */
public enum AreaTypeEnum {
    R("R","区"),
    S("S","街道办")

    ;
    private String code;
    private String desc;

    AreaTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
