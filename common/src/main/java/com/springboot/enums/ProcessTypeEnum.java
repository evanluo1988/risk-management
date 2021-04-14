package com.springboot.enums;

/**
 * @author lhf
 * @date 2021/4/1 4:44 下午
 **/
public enum ProcessTypeEnum {
    DISPATCHER("下发"),
    RECHECK("重新核查"),
    PROCESS("处理"),
    REVOKE("撤回"),
    RETURN("退回"),


    ;

    private String desc;

    ProcessTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
