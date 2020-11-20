package com.springboot.common.page;

import lombok.Data;

/**
 * 页码输入
 *
 * @Author 刘宏飞
 * @Date 2020/11/20 17:05
 * @Version 1.0
 */
@Data
public class PageIn {
    /**
     * 页码
     */
    private Integer pageNo;
    /**
     * 页大小
     */
    private Integer pageSize;
}
