package com.springboot.enums;

/**
 * 是否逾期
 * @Author 刘宏飞
 * @Date 2020/12/1 11:13
 * @Version 1.0
 */
public enum InformOverDueEnum {
    N(false,"否"),
    Y(true,"是"),

    ;

    private Boolean code;
    private String desc;

    InformOverDueEnum(Boolean code, String desc) {
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

    public static InformOverDueEnum codeOf(Boolean code){
        InformOverDueEnum[] values = InformOverDueEnum.values();
        for (InformOverDueEnum value : values) {
            if (value.getCode().equals(code)){
                return value;
            }
        }

        return null;
    }

    public static InformOverDueEnum descOf(String desc){
        InformOverDueEnum[] values = InformOverDueEnum.values();
        for (InformOverDueEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }

}
