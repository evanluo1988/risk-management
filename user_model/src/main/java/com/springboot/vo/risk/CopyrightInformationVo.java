package com.springboot.vo.risk;

import lombok.Data;

import java.util.List;

/**
 * 软件著作权信息
 */
@Data
public class CopyrightInformationVo {
    /**
     * 软件著作权总数
     */
    private String softwareApplicationNum;
    /**
     * 最近一笔软件著作权首次发表据今年份
     */
    private String lastSoftwareApplicationPubYear;
    /**
     * 最近一笔软件著作权登记据今年份
     */
    private String lastSoftwareApplicationRegYear;
    /**
     * 软件著作权明细
     */
    private List<StdIaCopyrightVo> stdIaCopyrightList;
}
