package com.springboot.enums;

/**
 * 分派状态
 * @Author 刘宏飞
 * @Date 2020/12/1 10:59
 * @Version 1.0
 */
public enum AssignmentEnum {
    NOT_ASSIGNED("NOT_ASSIGNED","未分派"),
    ASSIGNED("ASSIGNED","已分派"),
    RETURNED("RETURNED","已退回"),
    REVOKE("REVOKE","已撤回"),
    ASSIGNED_FAIL("ASSIGNED_FAIL","分派失败")


    ;
    private String code;
    private String desc;

    AssignmentEnum(String code, String desc) {
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

    public static AssignmentEnum codeOf(String code){
        AssignmentEnum[] values = AssignmentEnum.values();
        for (AssignmentEnum value : values) {
            if (value.getCode().equalsIgnoreCase(code)){
                return value;
            }
        }
        return null;
    }

    public static AssignmentEnum descOf(String desc){
        AssignmentEnum[] values = AssignmentEnum.values();
        for (AssignmentEnum value : values) {
            if (value.getDesc().equalsIgnoreCase(desc)){
                return value;
            }
        }
        return null;
    }


}
