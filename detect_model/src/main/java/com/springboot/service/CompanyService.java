package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.Company;
import com.springboot.dto.*;
import com.springboot.order.Sortable;
import com.springboot.page.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lhf
 * @date 2021/3/1 3:45 下午
 **/
public interface CompanyService extends IService<Company> {
    /**
     * 导入公司信息
     * @param file
     */
    void importCompany(MultipartFile file);

    /**
     * 探测数据
     */
    void detect();

    /**
     * 街道排名
     * @return
     */
    List<StatisticsCompanyRankByNumOutputDto> streetRank();

    /**
     * 根据指标值查询企业排名
     * @param quotaCode
     * @return
     */
    List<StatisticsCompanyRankByQuotaOutputDto> quotaRank(Long quotaCode);

    /**
     * 企业年增长
     * @return
     */
    List<StatisticsCompanyAddedTheYearsOutputDto> addedOverTheYears();

    /**
     * 企业经营状态
     * @return
     */
    List<StatisticsCompanyOperatingStatusOutputDto> operatingStatus();

    /**
     * 公司列表
     * @param query
     * @param sortable
     * @return
     */
    Pagination<CompanyPageOutputDto> pageCompany(CompanyPageQueryDto query, Sortable sortable);

    /**
     * 查询所有街道
     * @return
     */
    List<String> streets();

    /**
     * 根据企业名称查询企业
     * @param entName
     * @return
     */
    Company getCompanyByName(String entName);
}
