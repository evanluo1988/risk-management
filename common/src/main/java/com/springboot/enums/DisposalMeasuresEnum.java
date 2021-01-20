package com.springboot.enums;

/**
 * 处置措施
 * @Author 刘宏飞
 * @Date 2020/12/9 11:09
 * @Version 1.0
 */
public enum DisposalMeasuresEnum {
    NOTHING("NOTHING","无"),
    ADMINISTRATIVE_RESOLUTION("ADMINISTRATIVE_RESOLUTION","行政化解"),
        JUDICIAL_CASE("JUDICIAL_CASE","司法立案")

    ;
    private String code;
    private String desc;

    DisposalMeasuresEnum(String code, String desc) {
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

    public static DisposalMeasuresEnum descOf(String desc){
        DisposalMeasuresEnum[] values = DisposalMeasuresEnum.values();
        for (DisposalMeasuresEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }
}
