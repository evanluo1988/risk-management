package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.domain.Company;
import com.springboot.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lhf
 * @date 2021/3/1 3:47 下午
 **/
public interface CompanyMapper extends BaseMapper<Company> {
    /**
     * 按街道排名
     * @return
     */
    List<StatisticsCompanyRankByNumOutputDto> streetRank();

    /**
     * 企业按照指标排名
     * @param quotaCode
     * @return
     */
    List<StatisticsCompanyRankByQuotaOutputDto> quotaRank(@Param("quotaCode") String quotaCode);

    /**
     * 企业年增长数
     * @return
     */
    List<StatisticsCompanyAddedTheYearsOutputDto> addedOverTheYears();

    /**
     * 企业经营状态
     * @return
     */
    List<StatisticsCompanyOperatingStatusOutputDto> operatingStatus();

    /**
     * 企业列表
     * @param key 关键字，根据企业名称和企业唯一信用代码筛选
     * @param street    街道
     * @param operatingStatus   经营状态
     * @param page
     * @return
     */
    Page<CompanyPageOutputDto> pageCompany(@Param("key") String key,
                                           @Param("street") String street,
                                           @Param("operatingStatus") String operatingStatus,
                                           Page page);

    /**
     * 查询所有街道
     * @return
     */
    List<String> streets();
}
