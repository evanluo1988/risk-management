package com.springboot;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/12/31 15:37
 * @Version 1.0
 */
@Data
public class DicTableVo {
    @ExcelProperty(index = 0)
    private String dicValue;
    @ExcelProperty(index = 1)
    private String dicName;
    @ExcelProperty(index = 2)
    private String dicMark;
}
