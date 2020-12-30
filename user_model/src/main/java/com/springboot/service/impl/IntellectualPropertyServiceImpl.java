package com.springboot.service.impl;

import com.google.common.collect.Lists;
import com.springboot.domain.risk.*;
import com.springboot.domain.risk.executor.QuotaTask;
import com.springboot.mapper.ExeSqlMapper;
import com.springboot.model.IaAsPartentModel;
import com.springboot.service.*;
import com.springboot.util.Utils;
import com.springboot.utils.ServerCacheUtils;
import com.springboot.utils.SqlSplicingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntellectualPropertyServiceImpl implements IntellectualPropertyService {
    @Autowired
    private CloudInfoTimelinessService cloudInfoTimelinessService;
    @Autowired
    private CloudQueryLogService cloudQueryLogService;
    @Autowired
    private WYSourceDataService wySourceDataService;
    @Autowired
    private IaAsPartentService iaAsPartentService;
    @Autowired
    private IaAsBrandService iaAsBrandService;
    @Autowired
    private IaAsCopyrightService iaAsCopyrightService;
    @Autowired
    private ExeSqlMapper exeSqlMapper;
    @Autowired
    private QuotaValueService quotaValueService;
    @Autowired
    private StdEntBasicService stdEntBasicService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handelData(String entName) throws Exception {
        /**
         * 看工商数据是否已经调用，如果没有调用则直接返回；
         * 首先通过entName查询时效性表获取reqId，再通过reqId获取工商basic表中的企业名称
         */
        CloudInfoTimeliness cloudInfoTimeliness = cloudInfoTimelinessService.getCloudInfoTimelinessByEntName(entName);
        if(cloudInfoTimeliness == null){
            return;
        }
        String reqId = cloudInfoTimeliness.getReqId();
        StdEntBasic stdEntBasic = stdEntBasicService.getStdEntBasicByReqId(reqId);
        if(stdEntBasic == null || StringUtils.isEmpty(stdEntBasic.getEntName())) {
            return;
        }

        createEdsData(reqId, stdEntBasic.getEntName());
        createStdData(reqId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void culQuotas(String reqId, String quotaType) {
        //通过reqId先查找企业指标值是否存在，如果存在直接获取，如果不存在就通过标准表重新计算
        //指标列表
        List<Quota> quotaList = ServerCacheUtils.getQuotaList();
        //过滤知识产权指标
        quotaList = quotaList.stream().filter(item -> !StringUtils.isEmpty(item.getQuotaCode()) && item.getQuotaCode().startsWith("ZS_")).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(quotaList)){
            return;
        }
        List<Quota> quotas = quotaList.stream().filter(item -> !StringUtils.isEmpty(item.getQuotaRule()) && item.getQuotaType().equals(quotaType)).collect(Collectors.toList());
        //初始化指标任务
        List<QuotaTask> quotaTaskList = Lists.newArrayList();
        for(Quota quota : Utils.getList(quotas)) {
            quotaTaskList.add(new QuotaTask(reqId ,quota));
        }
        List<QuotaValue> quotaValueList = culQuotaTasks(quotaTaskList);

        //save all quota values
        quotaValueService.saveQuotaValues(quotaValueList);
    }

    private List<QuotaValue> culQuotaTasks(List<QuotaTask> quotaTaskList) {
        //执行任务
        List<QuotaValue> quotaValueList = Lists.newArrayList();
        try {
            for(QuotaTask quotaTask : quotaTaskList){
                quotaValueList.add(quotaTask.call());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return quotaValueList;
    }

    /**
     * 创建知识产权原始表
     * @param reqId
     * @param entName
     */
    private void createEdsData(String reqId, String entName) {

        List<IaAsPartentModel> iaAsPartentModelList = wySourceDataService.getPatentData(entName);
        for(IaAsPartentModel iaAsPartentModel : iaAsPartentModelList){
            iaAsPartentModel.setReqId(reqId);
            iaAsPartentService.savePartent(iaAsPartentModel);
        }

        List<IaAsBrand> iaAsBrandList = wySourceDataService.getBrandData(entName);
        iaAsBrandList.stream().forEach(item -> item.setReqId(reqId));
        iaAsBrandService.saveIaAsBrands(iaAsBrandList);

        List<IaAsCopyright> iaAsCopyrightList = wySourceDataService.getCopyrightData(entName);
        iaAsCopyrightList.stream().forEach((item -> item.setReqId(reqId)));
        iaAsCopyrightService.saveIaAsCopyrights(iaAsCopyrightList);
    }

    /**
     * 创建标准表记录
     * @param reqId
     */
    public void createStdData(String reqId) {
        //从缓存获取执行sql
        List<EtlTranRule> etlTranRuleList = ServerCacheUtils.getEtlTranRuleListCache();
        //过滤知识产权转换规则
        etlTranRuleList = etlTranRuleList.stream().filter(item -> "7".equals(item.getType())).collect(Collectors.toList());
        for(EtlTranRule etlTranRule : etlTranRuleList){
            //拼接执行select sql
            String selectSql = etlTranRule.getTranRule();
            String targetTable = etlTranRule.getTargetTable();

            String selectExeSql = selectSql.replace("?", "'"+reqId+"'");
            //获取原始表数据
            List<HashMap> resultList = exeSqlMapper.exeSelectSql(selectExeSql);
            if(CollectionUtils.isEmpty(resultList)){
                continue;
            }
            for(HashMap row : resultList){
                //组装insert sql
                row.put("req_id", reqId);
                String insertSql = SqlSplicingUtils.getInsertSql(targetTable, row);
                exeSqlMapper.exeInsertSql(insertSql);
            }
        }
    }
}
