package com.springboot.service.impl;

import com.springboot.domain.CloudInfoTimeliness;
import com.springboot.domain.Company;
import com.springboot.domain.EntWyBasic;
import com.springboot.enums.OrgEnum;
import com.springboot.exception.ServiceException;
import com.springboot.service.*;
import com.springboot.utils.StrUtils;
import com.springboot.vo.risk.EntHealthReportVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Slf4j
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
    @Autowired
    private CompanyService companyService;

    @Override
    public EntHealthReportVo checkByEntName(String entName, OrgEnum org) {
        if(StringUtils.isEmpty(entName)) {
            throw new ServiceException("entName must not be null!");
        }

        CloudInfoTimeliness cloudInfoTimeliness = cloudInfoTimelinessService.getCloudInfoTimelinessByEntName(entName);
        if(cloudInfoTimeliness == null) {
            String orgEntName = entName;
            entName = StrUtils.brackets(orgEntName);
            if(!entName.equals(orgEntName)) {
                cloudInfoTimeliness = cloudInfoTimelinessService.getCloudInfoTimelinessByEntName(entName);
            }
        }
        String reqId = "";
        try{
            if(cloudInfoTimelinessService.checkTimeliness(cloudInfoTimeliness)) {
                reqId = cloudInfoTimeliness.getReqId();
                //查询指标值表，如果指标值存在直接返回
                int count = quotaValueService.countQuotaValues(reqId);
                if(count > 0) {
                    EntHealthReportVo reportVo = dataHandleService.getEntHealthReportVo(reqId, org);
                    Company company = companyService.getCompanyByName(entName);
                    if (Objects.nonNull(company) && StringUtils.isEmpty(company.getReqId())){
                        company.setReqId(reqId);
                        companyService.updateById(company);
                    }
                    return reportVo;
                }
            } else {
                //调用远程接口查询，入库，计算，后返回
                reqId = dataHandleService.handelData(entName, org);
                Company company = companyService.getCompanyByName(entName);
                if (Objects.nonNull(company)){
                    company.setReqId(reqId);
                    companyService.updateById(company);
                }
            }
            //通过本地标准表计算指标值
            dataHandleService.culQuotas(reqId, org);
            EntHealthReportVo reportVo = dataHandleService.getEntHealthReportVo(reqId, org);
            Company company = companyService.getCompanyByName(entName);
            if (Objects.nonNull(company) && StringUtils.isEmpty(company.getReqId())){
                company.setReqId(reqId);
                companyService.updateById(company);
            }
            return reportVo;
        } catch (Exception e) {
            log.info("checkByEntName:" ,e);
        }
        return null;
    }

    @Override
    public String getEntAddress(String entName) {
        CloudInfoTimeliness cloudInfoTimeliness = cloudInfoTimelinessService.getCloudInfoTimelinessByEntName(entName);
        String reqId = "";
        if(!cloudInfoTimelinessService.checkTimeliness(cloudInfoTimeliness)) {
            //调用远程接口查询，入库，计算，后返回
            try {
                reqId = dataHandleService.handelData(entName, OrgEnum.FINANCE_OFFICE);
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new ServiceException("微云取数失败！");
            }
        } else {
            reqId = cloudInfoTimeliness.getReqId();
        }

        //查询数据库记录得到address后返回
        EntWyBasic edsGsBasic = edsGsBasicService.getEdsGsBasicByReqId(reqId);
        if(edsGsBasic == null){
            return null;
        }
        return edsGsBasic.getAddress();
    }
}
