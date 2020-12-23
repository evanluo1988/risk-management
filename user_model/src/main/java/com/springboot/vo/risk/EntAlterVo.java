package com.springboot.vo.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 企业经营发展信息
 */
@Data
public class EntAlterVo {
    /**
     * 年度
     */
    private String altdateYear;

    /**
     * 日期
     */
    private String altdate;

    /**
     * 变更项目
     */
    private String altitem;

    /**
     * 变更前
     */
    private String altbe;

    /**
     * 变更后
     */
    private String altaf;
}
