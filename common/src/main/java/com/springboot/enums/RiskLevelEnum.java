package com.springboot.enums;

/**
 * 风险等级
 * @Author 刘宏飞
 * @Date 2020/12/7 10:03
 * @Version 1.0
 */
public enum RiskLevelEnum {
    HIGH("HIGN","高"),
    MIDDLE("HIDDLE","中"),
    GENERAL("GENERAL","一般"),
    LOW("LOW","低")

    ;
    private String code;
    private String desc;

    RiskLevelEnum(String code, String desc) {
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

    public static RiskLevelEnum descOf(String desc){
        RiskLevelEnum[] values = RiskLevelEnum.values();
        for (RiskLevelEnum value : values) {
            if (value.getDesc().equals(desc)){
                return value;
            }
        }
        return null;
    }

    public static RiskLevelEnum codeOf(String code){
        RiskLevelEnum[] values = RiskLevelEnum.values();
        for (RiskLevelEnum value : values) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
