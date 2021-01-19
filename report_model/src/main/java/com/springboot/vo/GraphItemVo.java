package com.springboot.vo;

import lombok.Data;

@Data
public class GraphItemVo {
    private String areaName;
    private Integer informChecked;
    private Integer informUncheck;
    private Integer taskChecked;
    private Integer taskUncheck;
}
