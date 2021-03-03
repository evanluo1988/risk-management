package com.springboot.controller;

import com.springboot.dto.*;
import com.springboot.page.PageIn;
import com.springboot.ret.ReturnT;
import com.springboot.service.StatisticsService;
import com.springboot.utils.ReturnTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 统计
 * @author lhf
 * @date 2021/3/2 5:10 下午
 **/
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/companyNum")
    public ReturnT<StatisticsCompanyNumOutputDto> statisticsCompanyNum(){
        StatisticsCompanyNumOutputDto statisticsCompanyNumOutputDto = statisticsService.statisticsCompanyNum();
        return ReturnTUtils.getReturnT(statisticsCompanyNumOutputDto);
    }

    @GetMapping("/companyMap")
    public ReturnT<List<StatisticsCompanyMapOutputDto>> statisticsCompanyMap(){
        List<StatisticsCompanyMapOutputDto> statisticsCompanyMapOutputDtoList = statisticsService.statisticsCompanyMap();
        return ReturnTUtils.getReturnT(statisticsCompanyMapOutputDtoList);
    }

    @GetMapping("/streetRank")
    public ReturnT<List<StatisticsCompanyRankByNumOutputDto>> streetRank(){
        List<StatisticsCompanyRankByNumOutputDto> statisticsCompanyRankByNumOutputDtoList = statisticsService.streetRank();
        return ReturnTUtils.getReturnT(statisticsCompanyRankByNumOutputDtoList);
    }

    @GetMapping("/patentRank")
    public ReturnT<List<StatisticsCompanyRankByQuotaOutputDto>> patentRank(PageIn pageIn){
        // 有效专利总数
        String quotaCode = "ZS_VALID_PATENT_NUM";
        List<StatisticsCompanyRankByQuotaOutputDto> statisticsCompanyRankByQuotaOutputDtoList = statisticsService.quotaRank(quotaCode,pageIn);
        return ReturnTUtils.getReturnT(statisticsCompanyRankByQuotaOutputDtoList);
    }

    @GetMapping("/softRank")
    public ReturnT<List<StatisticsCompanyRankByQuotaOutputDto>> softRank(PageIn pageIn){
        // 报告主体有效软著数量
        String quotaCode = "ZS_NUM_EFFECT_SOFTWORKS_CUR";
        List<StatisticsCompanyRankByQuotaOutputDto> statisticsCompanyRankByQuotaOutputDtoList = statisticsService.quotaRank(quotaCode,pageIn);
        return ReturnTUtils.getReturnT(statisticsCompanyRankByQuotaOutputDtoList);
    }

    @GetMapping("/businessPenaltyRank")
    public ReturnT<List<StatisticsCompanyRankByQuotaOutputDto>> businessPenaltyRank(PageIn pageIn){
        // 严重工商处罚次数
        String quotaCode = "GS_NUM_COMMERCIAL_PENALTIES";
        List<StatisticsCompanyRankByQuotaOutputDto> statisticsCompanyRankByQuotaOutputDtoList = statisticsService.quotaRank(quotaCode,pageIn);
        return ReturnTUtils.getReturnT(statisticsCompanyRankByQuotaOutputDtoList);
    }

    @GetMapping("/addedOverTheYears")
    public ReturnT<List<StatisticsCompanyAddedTheYearsOutputDto>> addedOverTheYears(){
        List<StatisticsCompanyAddedTheYearsOutputDto> statisticsCompanyAddedTheYearsOutputDtoList = statisticsService.addedOverTheYears();
        return ReturnTUtils.getReturnT(statisticsCompanyAddedTheYearsOutputDtoList);
    }

    @GetMapping("/operatingStatus")
    public ReturnT<List<StatisticsCompanyOperatingStatusOutputDto>> operatingStatus(){
        List<StatisticsCompanyOperatingStatusOutputDto> statisticsCompanyOperatingStatusOutputDtoList = statisticsService.operatingStatus();
        return ReturnTUtils.getReturnT(statisticsCompanyOperatingStatusOutputDtoList);
    }

}
