package com.springboot.model;

import com.springboot.domain.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 15:38
 * @Version 1.0
 */
@Data
@Builder
public class StdGsEntInfoModel {
    /**
     * 信息变更记录
     */
    private List<StdEntAlter> stdEntAlters;
    /**
     * 行政处罚
     */
    private List<StdEntCaseinfo> stdEntCaseinfos;
    /**
     * 企业异常名录
     */
    private List<StdEntException> stdEntExceptions;
    /**
     * 分支机构
     */
    private List<StdEntFiliation> stdEntFiliations;
    /**
     * 企业清算信息
     */
    private List<StdEntLiquidation> stdEntLiquidations;
    /**
     * 主要人员
     */
    private List<StdEntPerson> stdEntPeople;
    /**
     * 股东信息
     */
    private List<StdEntShareHolder> stdEntShareHolders;
    /**
     * 股权冻结
     */
    private List<StdEntSharesfrost> stdEntSharesfrosts;
}
