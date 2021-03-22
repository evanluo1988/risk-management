package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.springboot.domain.Company;
import com.springboot.dto.*;
import com.springboot.page.PageIn;
import com.springboot.service.CompanyService;
import com.springboot.service.StatisticsService;
import com.springboot.utils.ConvertUtils;
import com.springboot.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lhf
 * @date 2021/3/2 5:18 下午
 **/
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private CompanyService companyService;

    @Override
    public StatisticsCompanyNumOutputDto statisticsCompanyNum() {
        // total 企业总数量
        int total = companyService.count();
        // annualGrowth 今年新增数量
        Date beginDayOfYear = TimeUtils.getBeginDayOfYear();
        Date endDayOfYear = TimeUtils.getEndDayOfYear();
        LambdaQueryWrapper<Company> lambdaQueryWrapper = new LambdaQueryWrapper<Company>()
                .between(Company::getRegDate,beginDayOfYear,endDayOfYear);
        int annualGrowth = companyService.count(lambdaQueryWrapper);

        return new StatisticsCompanyNumOutputDto(total,annualGrowth);
    }

    @Override
    public List<StatisticsCompanyMapOutputDto> statisticsCompanyMap() {
        List<Company> list = companyService.list();
        return ConvertUtils.sourceToTarget(list, StatisticsCompanyMapOutputDto.class);
    }

    @Override
    public List<StatisticsCompanyRankByNumOutputDto> streetRank() {
        int total = companyService.count();
        List<StatisticsCompanyRankByNumOutputDto> statisticsCompanyRankByNumOutputDtoList = companyService.streetRank();
        statisticsCompanyRankByNumOutputDtoList.forEach(statisticsCompanyRankByNumOutputDto -> {
            statisticsCompanyRankByNumOutputDto.calcRatio(total);
        });
        return statisticsCompanyRankByNumOutputDtoList;
    }

    @Override
    public List<StatisticsCompanyRankByQuotaOutputDto> quotaRank(Long quotaId, PageIn pageIn) {
        return companyService.quotaRank(quotaId);
    }

    @Override
    public List<StatisticsCompanyAddedTheYearsOutputDto> addedOverTheYears() {
        List<StatisticsCompanyAddedTheYearsOutputDto> ret = Lists.newArrayListWithExpectedSize(12);
        int year = LocalDate.now().getYear();
        prepareAddedOverTheYearsRet(ret,year);
        List<StatisticsCompanyAddedTheYearsOutputDto> addedOverTheYears = companyService.addedOverTheYears();
        addedOverTheYearsRetDataInput(ret,addedOverTheYears);
        return ret;
    }

    @Override
    public List<StatisticsCompanyOperatingStatusOutputDto> operatingStatus() {
        List<StatisticsCompanyOperatingStatusOutputDto> ret = Lists.newArrayListWithCapacity(4);
        prepareOperatingStatusRet(ret);
        List<StatisticsCompanyOperatingStatusOutputDto> operatingStatus = companyService.operatingStatus();
        operatingStatusRetDataInput(ret,operatingStatus);
        return ret;
    }

    private void operatingStatusRetDataInput(List<StatisticsCompanyOperatingStatusOutputDto> ret, List<StatisticsCompanyOperatingStatusOutputDto> operatingStatus) {
        Map<String, StatisticsCompanyOperatingStatusOutputDto> statusMapObj = ret.stream().collect(Collectors.toMap(StatisticsCompanyOperatingStatusOutputDto::getOperatingStatus, statisticsCompanyOperatingStatusOutputDto -> statisticsCompanyOperatingStatusOutputDto));
        for (StatisticsCompanyOperatingStatusOutputDto status : operatingStatus) {
            String key = status.getOperatingStatus();
            Integer value = status.getNum();

            if (!StringUtils.isEmpty(key) && key.contains("在营")){
                StatisticsCompanyOperatingStatusOutputDto obj = statusMapObj.get("在营");
                obj.setNum(value);
            }else if (!StringUtils.isEmpty(key) && key.contains("注销")){
                StatisticsCompanyOperatingStatusOutputDto obj = statusMapObj.get("注销");
                obj.setNum(value);
            }else if (!StringUtils.isEmpty(key) && key.contains("吊销")){
                StatisticsCompanyOperatingStatusOutputDto obj = statusMapObj.get("吊销");
                obj.setNum(value);
            }else{
                StatisticsCompanyOperatingStatusOutputDto obj = statusMapObj.get("其他");
                obj.setNum(obj.getNum()+value);
            }

        }
    }

    private void prepareOperatingStatusRet(List<StatisticsCompanyOperatingStatusOutputDto> ret) {
        ret.add(new StatisticsCompanyOperatingStatusOutputDto("在营",0));
        ret.add(new StatisticsCompanyOperatingStatusOutputDto("注销",0));
        ret.add(new StatisticsCompanyOperatingStatusOutputDto("吊销",0));
        ret.add(new StatisticsCompanyOperatingStatusOutputDto("其他",0));
    }

    private void addedOverTheYearsRetDataInput(List<StatisticsCompanyAddedTheYearsOutputDto> ret, List<StatisticsCompanyAddedTheYearsOutputDto> addedOverTheYears) {
        StatisticsCompanyAddedTheYearsOutputDto before = null;
        Map<Integer, Integer> yearMapIncrementNum = addedOverTheYears.stream().collect(Collectors.toMap(StatisticsCompanyAddedTheYearsOutputDto::getYearName, StatisticsCompanyAddedTheYearsOutputDto::getIncrementNum));
        for (StatisticsCompanyAddedTheYearsOutputDto r : ret) {
            Integer yearName = r.getYearName();
            if (yearName!=-1){
                Integer yearIncrementNum = yearMapIncrementNum.get(yearName);
                if (Objects.nonNull(yearIncrementNum)){
                    r.setIncrementNum(yearIncrementNum);
                }
            }else{
                before = r;
            }
        }

        Integer beforeIncrement = addedOverTheYears.stream().skip(11).map(StatisticsCompanyAddedTheYearsOutputDto::getIncrementNum).collect(Collectors.summingInt(Integer::valueOf));
        before.setIncrementNum(beforeIncrement);
    }

    private void prepareAddedOverTheYearsRet(List<StatisticsCompanyAddedTheYearsOutputDto> ret, int year) {
        ret.add(new StatisticsCompanyAddedTheYearsOutputDto(year,0));
        ret.add(new StatisticsCompanyAddedTheYearsOutputDto(year-1,0));
        ret.add(new StatisticsCompanyAddedTheYearsOutputDto(year-2,0));
        ret.add(new StatisticsCompanyAddedTheYearsOutputDto(year-3,0));
        ret.add(new StatisticsCompanyAddedTheYearsOutputDto(year-4,0));
        ret.add(new StatisticsCompanyAddedTheYearsOutputDto(year-5,0));
        ret.add(new StatisticsCompanyAddedTheYearsOutputDto(year-6,0));
        ret.add(new StatisticsCompanyAddedTheYearsOutputDto(year-7,0));
        ret.add(new StatisticsCompanyAddedTheYearsOutputDto(year-8,0));
        ret.add(new StatisticsCompanyAddedTheYearsOutputDto(year-9,0));
        ret.add(new StatisticsCompanyAddedTheYearsOutputDto(year-10,0));
        ret.add(new StatisticsCompanyAddedTheYearsOutputDto(-1,0));
    }
}
