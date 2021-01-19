package com.springboot.model;

import lombok.Data;

@Data
public class TaskGraphModel {
    private Long areaId;
    private String areaName;
    private String checkStatus;
    private Integer count;
}
