package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author lhf
 * @date 2021/3/2 9:43 上午
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("companies_detect_err_log")
public class CompanyDetectErrLog extends BaseDomain{
    /**
     * 公司ID
     */
    private Long companyId;
    /**
     * 公司名
     */
    private String companyName;
    /**
     * 异常信息
     */
    private String exceptionMsg;
    /**
     * 查询索引
     */
    private String queryIndex;

}
