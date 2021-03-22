package com.springboot.service;

import com.springboot.dto.*;
import com.springboot.page.PageIn;
import com.springboot.page.Pagination;

import java.util.List;

/**
 * @author lhf
 * @date 2021/3/2 5:17 下午
 **/
public interface StatisticsService {
    /**
     * 统计企业数量
     * @return
     */
    StatisticsCompanyNumOutputDto statisticsCompanyNum();

    /**
     * 统计企业地图
     * @return
     */
    List<StatisticsCompanyMapOutputDto> statisticsCompanyMap();

    /**
     * 各街道高新企业数量
     * @return
     */
    List<StatisticsCompanyRankByNumOutputDto> streetRank();

    /**
     * 根据指标值排名
     * @param quotaCode
     * @param pageIn
     * @return
     */
    List<StatisticsCompanyRankByQuotaOutputDto> quotaRank(Long quotaId, PageIn pageIn);

    /**
     * 企业历年增长
     * @return
     */
    List<StatisticsCompanyAddedTheYearsOutputDto> addedOverTheYears();

    /**
     * 企业经营状态分布
     * @return
     */
    List<StatisticsCompanyOperatingStatusOutputDto> operatingStatus();
}
