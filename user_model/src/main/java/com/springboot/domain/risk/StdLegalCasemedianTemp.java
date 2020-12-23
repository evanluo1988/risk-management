package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/12/22 16:53
 * @Version 1.0
 */
@Data
@TableName(value = "std_legal_casemedian_temp")
public class StdLegalCasemedianTemp extends StdLegalCasemedian {

    private Long stdLegalCasemedianId;
}
