package com.springboot.vo;

import lombok.Data;

@Data
public class GraphItemVo {
    private String areaName;
    private Integer informChecked = 0;
    private Integer informUncheck = 0;
    private Integer taskChecked = 0;
    private Integer taskUncheck = 0;
}
