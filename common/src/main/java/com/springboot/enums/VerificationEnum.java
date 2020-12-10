package com.springboot.enums;

/**
 * 线索属实性审核
 * @Author 刘宏飞
 * @Date 2020/12/8 17:38
 * @Version 1.0
 */
public enum VerificationEnum {
    TO_VERIFY("TO_VERIFY","待核实"),
    INVALID_INFORM("INVALID_INFORM","无效举报"),
    CLUE_NOT_TRUE("CLUE_NOT_TRUE","线索不属实"),
    CLUE_TRUE("CLUE_TRUE","线索属实")

    ;
    private String code;
    private String desc;

    VerificationEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode()  {
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

    public static VerificationEnum descOf(String desc){
        VerificationEnum[] values = VerificationEnum.values();
        for (VerificationEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }
}
