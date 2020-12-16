package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 14:52
 * @Version 1.0
 */
@Data
@TableName(value = "std_ent_filiation_list")
public class StdEntFiliation extends BaseDomain {

    private String reqId;

    /**
     * 企业名称
     */
    private String brname;
}
