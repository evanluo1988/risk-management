package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 15:08
 * @Version 1.0
 */
@Data
@TableName(value = "std_ent_caseinfo_list")
public class StdEntCaseinfo extends BaseDomain {

    private String reqId;

    /**
     * 处罚决定书文号
     */
    private String pendecno;

    /**
     * 处罚决定书签发日期
     */
    private String pendecissdate;

    /**
     * 公示日期
     */
    private String publicdate;

    /**
     * 处罚机关
     */
    private String penauth;

    /**
     * 主要违法事实
     */
    private String illegfact;

    /**
     * 处罚种类
     */
    private String pentype;

    /**
     * 处罚结果
     */
    private String penresult;
}
