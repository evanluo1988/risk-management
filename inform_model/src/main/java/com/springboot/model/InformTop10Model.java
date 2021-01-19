package com.springboot.model;

import lombok.Data;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/19 17:01
 */
@Data
public class InformTop10Model {
    /**
     * 企业名称
     */
    private String entName;
    /**
     * 举报次数
     */
    private String informNum;
}
