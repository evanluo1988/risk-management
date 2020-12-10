package com.springboot.enums;

/**
 * 移交状态
 * @Author 刘宏飞
 * @Date 2020/12/1 11:23
 * @Version 1.0
 */
public enum InformTransferEnum {
    Y(true,"是"),
    N(false,"否")

    ;
    private Boolean code;
    private String desc;

    InformTransferEnum(Boolean code, String desc) {
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

    public static InformTransferEnum codeOf(Boolean code){
        InformTransferEnum[] values = InformTransferEnum.values();
        for (InformTransferEnum value : values) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

    public static  InformTransferEnum descOf(String desc){
        InformTransferEnum[] values = InformTransferEnum.values();
        for (InformTransferEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }
}
