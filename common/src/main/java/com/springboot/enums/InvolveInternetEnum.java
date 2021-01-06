package com.springboot.enums;

/**
 * 是否涉及互联网
 * @Author 刘宏飞
 * @Date 2020/12/7 11:01
 * @Version 1.0
 */
public enum InvolveInternetEnum {
    ON_LINE("ON_LINE","线上"),
    OFFLINE("OFFLINE","线下"),
    ON_LINE_AND_OFFLINE("ON_LINE_AND_OFFLINE","线上线下相结合"),
    OTHER("OTHER","其他")
    ;

    private String code;
    private String desc;

    InvolveInternetEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static InvolveInternetEnum descOf(String desc) {
        InvolveInternetEnum[] values = InvolveInternetEnum.values();
        for (InvolveInternetEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }

    public static InvolveInternetEnum codeOf(String code) {
        InvolveInternetEnum[] values = InvolveInternetEnum.values();
        for (InvolveInternetEnum value : values) {
            if (value.getCode().equalsIgnoreCase(code)){
                return value;
            }
        }
        return null;
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
