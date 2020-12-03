package com.springboot.enums;

public enum TaskStatusEnum {
    WAITING_CHECK("待核查"),
    CHECKING("审核中"),
    CHECKED("已审核"),
    REPORTED("已上报");

    private String name;
    TaskStatusEnum(String name){
        this.name = name;
    }

}
