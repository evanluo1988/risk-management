package com.springboot.enums;

/**
 * 地域规模
 * @Author 刘宏飞
 * @Date 2020/12/10 15:12
 * @Version 1.0
 */
public enum RegionalScaleEnum {
    INTERPROVINCIAL("INTERPROVINCIAL","跨省"),
    NOT_INTERPROVINCIAL("NOT_INTERPROVINCIAL","非跨省")

    ;
    private String code;
    private String desc;

    RegionalScaleEnum(String code, String desc) {
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

    private static RegionalScaleEnum descOf(String desc){
        RegionalScaleEnum[] values = RegionalScaleEnum.values();
        for (RegionalScaleEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }
}
