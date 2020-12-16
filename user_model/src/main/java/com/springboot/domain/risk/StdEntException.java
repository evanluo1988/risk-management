package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 15:30
 * @Version 1.0
 */
@Data
@TableName(value = "std_ent_exceptions")
public class StdEntException extends BaseDomain {

    private String reqId;

    /**
     * 企业名称
     */
    private String entname;

    /**
     * 列入日期
     */
    private String indate;

    /**
     * 列入原因
     */
    private String inreason;

    /**
     * 移出日期
     */
    private String outdate;

    /**
     * 移出异常名录原因
     */
    private String outreason;
}
