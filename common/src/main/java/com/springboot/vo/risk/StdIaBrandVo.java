package com.springboot.vo.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * 商标明细
 */
@Data
public class StdIaBrandVo {
    /**
     * 商标
     */
    private String graph;
    /**
     * 商标名
     */
    private String brandName;
    /**
     * 尼斯分类
     */
    private String niceClassify;

    private String niceClassifyName;
    /**
     * 申请日期
     */
    private LocalDate registrationDate;
    /**
     * 注册号
     */
    private String applicationNumber;
    /**
     * 状态
     */
    private String authorityStatus;
    /**
     * 代理人
     */
    private String agentName;

    public String getNiceClassifyName() {
        return niceClassifyName;
    }

    public String getRegistrationDate() {
        return registrationDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
