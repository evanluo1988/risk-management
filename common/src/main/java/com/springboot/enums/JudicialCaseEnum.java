package com.springboot.enums;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/6 13:56
 */
public enum JudicialCaseEnum {
    FILE_CASE(1,"立案"),
    UN_FILE_CASE(0,"未立案")
    ;
    private Integer code;
    private String desc;

    JudicialCaseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static JudicialCaseEnum descOf(String desc) {
        JudicialCaseEnum[] values = JudicialCaseEnum.values();
        for (JudicialCaseEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }

    public static JudicialCaseEnum codeOf(Integer code) {
        JudicialCaseEnum[] values = JudicialCaseEnum.values();
        for (JudicialCaseEnum value : values) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
