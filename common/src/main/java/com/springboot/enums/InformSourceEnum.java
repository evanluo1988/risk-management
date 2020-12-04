package com.springboot.enums;

/**
 * @Author 刘宏飞
 * @Date 2020/12/4 17:42
 * @Version 1.0
 */
public enum InformSourceEnum {
    WX("WX","微信举报"),
    PHONE("PHONE","电话举报"),
    MAIL("MAIL","信件举报"),
    OTHER("MAIL","其他举报")

    ;
    private String code;
    private String desc;

    InformSourceEnum(String code, String desc) {
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

    public static InformSourceEnum descOf(String desc){
        InformSourceEnum[] values = InformSourceEnum.values();
        for (InformSourceEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }

    public static InformSourceEnum codeOf(String code){
        InformSourceEnum[] values = InformSourceEnum.values();
        for (InformSourceEnum value : values) {
            if (value.getCode().equalsIgnoreCase(code)){
                return value;
            }
        }
        return null;
    }
}
