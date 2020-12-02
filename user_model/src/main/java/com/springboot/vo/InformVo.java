package com.springboot.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author 刘宏飞
 * @Date 2020/12/1 16:42
 * @Version 1.0
 */
@Data
public class InformVo {
    public interface DispatcherGroup{}
    public interface CheckGroup{}

    @NotNull(groups = DispatcherGroup.class,message = "区域不能为空")
    private Long areaId;


}
