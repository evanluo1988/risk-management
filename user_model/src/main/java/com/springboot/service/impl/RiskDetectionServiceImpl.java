package com.springboot.service.impl;

import com.springboot.domain.risk.CloudInfoTimeliness;
import com.springboot.domain.risk.EntWyBasic;
import com.springboot.service.*;
import com.springboot.vo.risk.EntHealthReportVo;
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
    @Autowired
    private QuotaValueService quotaValueService;

    @Override
    public EntHealthReportVo checkByEntName(String entName) {
        CloudInfoTimeliness cloudInfoTimeliness = cloudInfoTimelinessService.getCloudInfoTimelinessByEntName(entName);
        String reqId = cloudInfoTimeliness.getReqId();
        if(cloudInfoTimelinessService.checkTimeliness(cloudInfoTimeliness)) {
            //查询指标值表，如果指标值存在直接返回
            int count = quotaValueService.countQuotaValues(reqId);
            if(count > 0) {
                EntHealthReportVo reportVo = dataHandleService.getEntHealthReportVo(reqId);
                return reportVo;
            }
        } else {
            //调用远程接口查询，入库，计算，后返回
            try {
                reqId = dataHandleService.handelData(entName);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        //通过本地标准表计算指标值
        dataHandleService.culQuotas(reqId);
        //dataHandleService.culModels(reqId);
        EntHealthReportVo reportVo = dataHandleService.getEntHealthReportVo(reqId);

        return reportVo;
    }

    @Override
    public String getEntAddress(String entName) {
        CloudInfoTimeliness cloudInfoTimeliness = cloudInfoTimelinessService.getCloudInfoTimelinessByEntName(entName);
        String reqId = cloudInfoTimeliness.getReqId();
        if(!cloudInfoTimelinessService.checkTimeliness(cloudInfoTimeliness)) {
            //调用远程接口查询，入库，计算，后返回
            try {
                reqId = dataHandleService.handelData(entName);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        //查询数据库记录得到address后返回
        EntWyBasic edsGsBasic = edsGsBasicService.getEdsGsBasicByReqId(reqId);
        if(edsGsBasic == null){
            return null;
        }
        return edsGsBasic.getAddress();
    }
}
