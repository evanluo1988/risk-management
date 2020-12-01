package com.springboot.enums;

/**
 * @Author 刘宏飞
 * @Date 2020/12/1 9:38
 * @Version 1.0
 */
public enum InformAnonymousEnum {
    Y(true,"否"),
    N(false,"是");

    private Boolean code;
    private String desc;

    InformAnonymousEnum(Boolean code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Boolean getCode() {
        return code;
    }

    public void setCode(Boolean code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static InformAnonymousEnum codeOf(Boolean code){
        InformAnonymousEnum[] values = InformAnonymousEnum.values();
        for (InformAnonymousEnum value : values) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

    public static InformAnonymousEnum descOf(String desc){
        InformAnonymousEnum[] values = InformAnonymousEnum.values();
        for (InformAnonymousEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }
}
