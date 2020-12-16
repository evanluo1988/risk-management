package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.domain.risk.*;
import com.springboot.mapper.*;
import com.springboot.model.*;
import com.springboot.service.StdDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 15:47
 * @Version 1.0
 */
@Slf4j
@Service
public class StdDataServiceImpl implements StdDataService {

    @Autowired
    private StdEntAlterMapper stdEntAlterMapper;
    @Autowired
    private StdEntCaseinfoMapper stdEntCaseinfoMapper;
    @Autowired
    private StdEntExceptionMapper stdEntExceptionMapper;
    @Autowired
    private StdEntFiliationMapper stdEntFiliationMapper;
    @Autowired
    private StdEntLiquidationMapper stdEntLiquidationMapper;
    @Autowired
    private StdEntPersonMapper stdEntPersonMapper;
    @Autowired
    private StdEntShareHolderMapper stdEntShareHolderMapper;
    @Autowired
    private StdEntSharesfrostMapper stdEntSharesfrostMapper;

    @Override
    public StdGsEntInfoModel getStdGsEntInfo(String reqId) {

        List<StdEntAlter> stdEntAlters = listStdEntAlter(reqId);
        List<StdEntCaseinfo> stdEntCaseinfos = listStdEntCaseinfo(reqId);
        List<StdEntException> stdEntExceptions = listStdEntException(reqId);
        List<StdEntFiliation> stdEntFiliations = listStdEntFiliation(reqId);
        List<StdEntLiquidation> stdEntLiquidations = listStdEntLiquidation(reqId);
        List<StdEntPerson> stdEntPeople = listStdEntPerson(reqId);
        List<StdEntShareHolder> stdEntShareHolders = listStdEntShareHolder(reqId);
        List<StdEntSharesfrost> stdEntSharesfrosts = listStdEntSharesfrost(reqId);

        return StdGsEntInfoModel
                .builder()
                .stdEntAlters(stdEntAlters)
                .stdEntCaseinfos(stdEntCaseinfos)
                .stdEntExceptions(stdEntExceptions)
                .stdEntFiliations(stdEntFiliations)
                .stdEntLiquidations(stdEntLiquidations)
                .stdEntPeople(stdEntPeople)
                .stdEntShareHolders(stdEntShareHolders)
                .stdEntSharesfrosts(stdEntSharesfrosts)
                .build();
    }

    private List<StdEntSharesfrost> listStdEntSharesfrost(String reqId) {
        LambdaQueryWrapper<StdEntSharesfrost> queryWrapper = new LambdaQueryWrapper<StdEntSharesfrost>().eq(StdEntSharesfrost::getReqId, reqId);
        return stdEntSharesfrostMapper.selectList(queryWrapper);
    }

    private List<StdEntShareHolder> listStdEntShareHolder(String reqId) {
        LambdaQueryWrapper<StdEntShareHolder> queryWrapper = new LambdaQueryWrapper<StdEntShareHolder>().eq(StdEntShareHolder::getReqId, reqId);
        return stdEntShareHolderMapper.selectList(queryWrapper);
    }

    private List<StdEntPerson> listStdEntPerson(String reqId) {
        LambdaQueryWrapper<StdEntPerson> queryWrapper = new LambdaQueryWrapper<StdEntPerson>().eq(StdEntPerson::getReqId, reqId);
        return stdEntPersonMapper.selectList(queryWrapper);
    }

    private List<StdEntLiquidation> listStdEntLiquidation(String reqId) {
        LambdaQueryWrapper<StdEntLiquidation> queryWrapper = new LambdaQueryWrapper<StdEntLiquidation>().eq(StdEntLiquidation::getReqId, reqId);
        return stdEntLiquidationMapper.selectList(queryWrapper);
    }

    private List<StdEntFiliation> listStdEntFiliation(String reqId) {
        LambdaQueryWrapper<StdEntFiliation> queryWrapper = new LambdaQueryWrapper<StdEntFiliation>().eq(StdEntFiliation::getReqId, reqId);
        return stdEntFiliationMapper.selectList(queryWrapper);
    }

    private List<StdEntException> listStdEntException(String reqId) {
        LambdaQueryWrapper<StdEntException> queryWrapper = new LambdaQueryWrapper<StdEntException>().eq(StdEntException::getReqId, reqId);
        return stdEntExceptionMapper.selectList(queryWrapper);
    }

    private List<StdEntCaseinfo> listStdEntCaseinfo(String reqId) {
        LambdaQueryWrapper<StdEntCaseinfo> queryWrapper = new LambdaQueryWrapper<StdEntCaseinfo>().eq(StdEntCaseinfo::getReqId, reqId);
        return stdEntCaseinfoMapper.selectList(queryWrapper);
    }

    private List<StdEntAlter> listStdEntAlter(String reqId) {
        LambdaQueryWrapper<StdEntAlter> queryWrapper = new LambdaQueryWrapper<StdEntAlter>().eq(StdEntAlter::getReqId, reqId);
        List<StdEntAlter> stdEntAlters = stdEntAlterMapper.selectList(queryWrapper);
        stdEntAlters.stream().forEach(item->{
            if (!StringUtils.isEmpty(item.getAltdate())){
                try{
                    LocalDate altDate = LocalDate.parse(item.getAltdate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    item.setAltdateYear(String.valueOf(altDate.getYear()));
                }catch (Exception e){
                    log.error("日期转换赋值失败：{}",e);
                }
            }
        });
        return stdEntAlters;
    }
}
