package com.springboot.vo;

import lombok.Data;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/19 16:38
 */
@Data
public class InformTop10Vo {
    /**
     * 企业名称
     */
    private String entName;
    /**
     * 举报次数
     */
    private String informNum;
}
