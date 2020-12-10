package com.springboot.enums;

/**
 * 核查状态
 * @Author 刘宏飞
 * @Date 2020/12/1 10:39
 * @Version 1.0
 */
public enum CheckStatusEnum {
    WAITING_CHECK("WAITING_CHECK","待核查"),
    CHECKING("CHECKING","核查中"),
    CHECKED("CHECKED","已核查")

    ;
    private String code;
    private String desc;

    CheckStatusEnum(String code, String desc) {
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

    public static CheckStatusEnum codeOf(String code){
        CheckStatusEnum[] values = CheckStatusEnum.values();
        for (CheckStatusEnum value : values) {
            if (value.getCode().equalsIgnoreCase(code)){
                return value;
            }
        }

        return null;
    }

    public static CheckStatusEnum descOf(String desc){
        CheckStatusEnum[] values = CheckStatusEnum.values();
        for (CheckStatusEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }

}
