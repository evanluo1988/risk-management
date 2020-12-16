package com.springboot.component;

import com.google.common.collect.Lists;
import com.springboot.domain.risk.StdEntAlter;
import com.springboot.mapper.StdEntAlterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("InvestorsWithdrawNum")
public class InvestorsWithdrawNum implements QuotaComponent{

    @Autowired
    private StdEntAlterMapper stdEntAlterMapper;

    @Override
    public String execQuota(String reqId) {

        //distinct(变更前内容【ALTBE】、变更后内容【ALTAF】、变更日期【ALTDATE】)if{月化(【@当前时间】-变更日期【ALTDATE】)<=12 &【变更事项ALTITEM】='21'}
        List<StdEntAlter> stdEntAlterList = stdEntAlterMapper.findInvestorWithdrawList(reqId);



        return null;
    }
}
