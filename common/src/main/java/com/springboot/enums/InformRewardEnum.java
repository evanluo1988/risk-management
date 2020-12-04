package com.springboot.enums;

/**
 * @Author 刘宏飞
 * @Date 2020/12/4 17:36
 * @Version 1.0
 */
public enum InformRewardEnum {
    REWARDED("REWARDED","奖励"),
    UN_REWARD("UN_REWARD","未奖励"),
    REWARD_AUDITING("REWARD_AUDITING","审核中"),
    NO_REWARD("NO_REWARD","不予奖励")

    ;
    private String code;
    private String desc;

    InformRewardEnum(String code, String desc) {
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

    public static InformRewardEnum codeOf(String code){
        InformRewardEnum[] values = InformRewardEnum.values();
        for (InformRewardEnum value : values) {
            if (value.getCode().equalsIgnoreCase(code)){
                return value;
            }
        }
        return null;
    }

    public static InformRewardEnum descOf(String desc){
        InformRewardEnum[] values = InformRewardEnum.values();
        for (InformRewardEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }
}
