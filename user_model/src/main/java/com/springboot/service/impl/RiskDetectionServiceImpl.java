package com.springboot.service.impl;

import com.springboot.domain.risk.CloudInfoTimeliness;
import com.springboot.domain.risk.EdsGsBasic;
import com.springboot.service.CloudInfoTimelinessService;
import com.springboot.service.DataHandleService;
import com.springboot.service.EdsGsBasicService;
import com.springboot.service.RiskDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiskDetectionServiceImpl implements RiskDetectionService {
    @Autowired
    private CloudInfoTimelinessService cloudInfoTimelinessService;
    @Autowired
    private DataHandleService dataHandleService;
    @Autowired
    private EdsGsBasicService edsGsBasicService;

    @Override
    public void checkByEntName(String entName) {
        CloudInfoTimeliness cloudInfoTimeliness = cloudInfoTimelinessService.getCloudInfoTimelinessByEntName(entName);
        String reqId = cloudInfoTimeliness.getReqId();
        if(cloudInfoTimelinessService.checkTimeliness(cloudInfoTimeliness)) {
            //查询指标值表，如果指标值存在直接返回
            //todo
        } else {
            //调用远程接口查询，入库，计算，后返回
            reqId = dataHandleService.handelData(entName);
        }
        //通过本地标准表计算指标值
        //todo
    }

    @Override
    public String getEntAddress(String entName) {
        CloudInfoTimeliness cloudInfoTimeliness = cloudInfoTimelinessService.getCloudInfoTimelinessByEntName(entName);
        String reqId = cloudInfoTimeliness.getReqId();
        if(!cloudInfoTimelinessService.checkTimeliness(cloudInfoTimeliness)) {
            //调用远程接口查询，入库，计算，后返回
            reqId = dataHandleService.handelData(entName);
        }

        //查询数据库记录得到address后返回
        EdsGsBasic edsGsBasic = edsGsBasicService.getEdsGsBasicByReqId(reqId);
        return edsGsBasic.getAddress();
    }
}
