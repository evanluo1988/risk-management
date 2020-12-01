package com.springboot.enums;

/**
 * @Author 刘宏飞
 * @Date 2020/12/1 10:39
 * @Version 1.0
 */
public enum InformCheckStatusEnum {
    WAITING_CHECK("WAITING_CHECK","待核查"),
    CHECKING("CHECKING","核查中"),
    CHECKED("CHECKED","已核查")

    ;
    private String code;
    private String desc;

    InformCheckStatusEnum(String code, String desc) {
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

    public static InformCheckStatusEnum codeOf(String code){
        InformCheckStatusEnum[] values = InformCheckStatusEnum.values();
        for (InformCheckStatusEnum value : values) {
            if (value.getCode().equalsIgnoreCase(code)){
                return value;
            }
        }

        return null;
    }

    public static InformCheckStatusEnum descOf(String desc){
        InformCheckStatusEnum[] values = InformCheckStatusEnum.values();
        for (InformCheckStatusEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }

}
