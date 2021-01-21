package com.springboot.enums;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/20 19:09
 */
public enum RewardStatusEnum {
    REWARDED("REWARDED","奖励"),
    UN_REWARD("UN_REWARD","未奖励"),
    UNDER_REVIEW("UNDER_REVIEW","评审中"),
    NO_REWARD("NO_REWARD","不予奖励")

    ;
    private String code;
    private String desc;

    RewardStatusEnum(String code, String desc) {
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

    public static RewardStatusEnum codeOf(String code){
        RewardStatusEnum[] values = RewardStatusEnum.values();
        for (RewardStatusEnum value : values) {
            if (value.getCode().equalsIgnoreCase(code)){
                return value;
            }
        }
        return null;
    }

    public static RewardStatusEnum descOf(String desc){
        RewardStatusEnum[] values = RewardStatusEnum.values();
        for (RewardStatusEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }
}
