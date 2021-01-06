package com.springboot.enums;

/**
 * 处置阶段
 * @Author 刘宏飞
 * @Date 2020/12/7 10:48
 * @Version 1.0
 */
public enum DisposalStageEnum {
    RECTIFYING("RECTIFYING","正在整改"),
    RESOLVED("RESOLVED","化解完毕"),
    PUT_ON_RECORD("PUT_ON_RECORD","立案查处"),
    BUSINESS_LOST("BUSINESS_LOST","企业失联"),
    NO_DISPOSAL("NO_DISPOSAL","无需处置"),
    CLUE_CHECK("CLUE_CHECK","线索核查")

    ;
    private String code;
    private String desc;

    DisposalStageEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DisposalStageEnum descLikeOf(String desc) {
        DisposalStageEnum[] values = DisposalStageEnum.values();
        for (DisposalStageEnum value : values) {
            if (value.getDesc().startsWith(desc)){
                return value;
            }
        }
        return null;
    }

    public static DisposalStageEnum codeOf(String code) {
        DisposalStageEnum[] values = DisposalStageEnum.values();
        for (DisposalStageEnum value : values) {
            if (value.getCode().equalsIgnoreCase(code)){
                return value;
            }
        }
        return null;
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
}
