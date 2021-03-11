package com.springboot.dto;

import com.springboot.order.Sortable;
import com.springboot.page.PageIn;
import lombok.Data;

/**
 * @author lhf
 * @date 2021/3/3 2:26 下午
 **/
@Data
public class CompanyPageQueryDto extends PageIn {
    /**
     * 企业名称或信用代码
     */
    private String key;
    /**
     * 街道
     */
    private String street;
    /**
     * 经营状态
     */
    private String operatingStatus;
}
