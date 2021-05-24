package com.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.springboot.domain.*;
import com.springboot.executor.QuotaTask;
import com.springboot.mapper.*;
import com.springboot.model.RemoteDataModel;
import com.springboot.service.*;
import com.springboot.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IndustrialJusticeServiceImpl extends QuotaTaskHandel implements IndustrialJusticeService {
    @Autowired
    private CloudInfoTimelinessService cloudInfoTimelinessService;
    @Autowired
    private CloudQueryLogService cloudQueryLogService;
    @Autowired
    private WYSourceDataService wySourceDataService;
    @Autowired
    private EntWyBasicMapper entWyBasicMapper;
    @Autowired
    private EntWyShareholderMapper entWyShareholderMapper;
    @Autowired
    private EntWyPersonMapper entWyPersonMapper;
    @Autowired
    private EntWyEntinvitemMapper entWyEntinvitemMapper;
    @Autowired
    private EntWyFrinvMapper entWyFrinvMapper;
    @Autowired
    private EntWyFrpositionMapper entWyFrpositionMapper;
    @Autowired
    private EntWyFiliationMapper entWyFiliationMapper;
    @Autowired
    private EntWyLiquidationMapper entWyLiquidationMapper;
    @Autowired
    private EntWyAlterMapper entWyAlterMapper;
    @Autowired
    private EntWyMortgagebasicMapper entWyMortgagebasicMapper;
    @Autowired
    private EntWyMortgageregMapper entWyMortgageregMapper;
    @Autowired
    private EntWyMortgagepawnMapper entWyMortgagepawnMapper;
    @Autowired
    private EntWyMortgagealtMapper entWyMortgagealtMapper;
    @Autowired
    private EntWyMortgagecanMapper entWyMortgagecanMapper;
    @Autowired
    private EntWyMortgagedebtMapper entWyMortgagedebtMapper;
    @Autowired
    private EntWyMortgageperMapper entWyMortgageperMapper;
    @Autowired
    private EntWyStockpawnMapper entWyStockpawnMapper;
    @Autowired
    private EntWyStockpawnaltMapper entWyStockpawnaltMapper;
    @Autowired
    private EntWyStockpawncanMapper entWyStockpawncanMapper;
    @Autowired
    private EntWyCaseinfoMapper entWyCaseinfoMapper;
    @Autowired
    private EntWyExceptionMapper entWyExceptionMapper;
    @Autowired
    private LegalWySsjghsjMapper legalWySsjghsjMapper;
    @Autowired
    private LegalWyBzxrComMapper legalWyBzxrComMapper;
    @Autowired
    private LegalWySxbzxrComMapper legalWySxbzxrComMapper;

    @Autowired
    private ExeSqlMapper exeSqlMapper;

    @Autowired
    private StdLegalDataStructuredService stdLegalDataStructuredService;
    @Autowired
    private LegalDataAddColumnServiceImpl legalDataAddColumnService;
    @Autowired
    private StdLegalDataAddedService stdLegalDataAddedService;
    @Autowired
    private StdLegalService stdLegalService;

    @Autowired
    private QuotaValueService quotaValueService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String handelData(String entName) throws Exception {
        String reqId = UUID.randomUUID().toString();
        createEdsData(reqId, entName);
        createStdData(reqId);
        analysisJustice(reqId);

        //设置时效性
        cloudInfoTimelinessService.updateTimeLiness(entName, reqId);
        return reqId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void culQuotas(String reqId, String quotaType) {
        //通过reqId先查找企业指标值是否存在，如果存在直接获取，如果不存在就通过标准表重新计算
        //指标列表
        List<Quota> quotaList = DetectCacheUtils.getQuotaList();
        //过滤出工商司法指标
        quotaList = quotaList.stream()
                .filter(item -> !StringUtils.isEmpty(item.getQuotaCode()) && (item.getQuotaCode().startsWith("GS_")
                        || item.getQuotaCode().startsWith("SF_")))
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(quotaList)){
            return;
        }
        List<Quota> quotas = quotaList.stream().filter(item -> !StringUtils.isEmpty(item.getQuotaRule())  && item.getQuotaType().equals(quotaType)).collect(Collectors.toList());
        //初始化指标任务
        List<QuotaTask> quotaTaskList = Lists.newArrayList();
        for(Quota quota : Utils.getList(quotas)) {
            quotaTaskList.add(new QuotaTask(reqId ,quota));
        }
        List<QuotaValue> quotaValueList = culQuotaTasks(quotaTaskList);

        //save all quota values
        quotaValueService.saveQuotaValues(quotaValueList);
    }

    /**
     * 司法解析
     * @param reqId
     * @throws Exception
     */
    @Override
    public void analysisJustice(String reqId) throws Exception {
        if (StringUtils.isEmpty(reqId)){
            return;
        }
        //解析司法引擎
        List<StdLegalDataStructured> stdLegalDataStructuredList = stdLegalDataStructuredService.findStdLegalDataStructuredByReqId(reqId);
        List<StdLegalDataAdded> stdLegalDataAddedList = Lists.newArrayList();
        for(StdLegalDataStructured legalDataStructured : stdLegalDataStructuredList) {
            stdLegalDataAddedList.addAll(legalDataAddColumnService.anynasisAndInsert(legalDataStructured ,reqId, null));
        }
        stdLegalDataAddedService.saveStdLegalDataAddedValues(stdLegalDataAddedList);
        //生成中间表
        stdLegalService.createStdLegalMidTable(reqId);
    }

    /**
     * 创建工商和司法原始表
     * @param reqId
     * @param entName
     */
    private void createEdsData(String reqId, String entName) {
        String response = wySourceDataService.getIndustrialAndJusticeData(entName);
        String orgEntName = entName;
        if(response == null){
            entName = StrUtils.brackets(entName);
            if(orgEntName.equals(entName)) {
                return;
            }
            response = wySourceDataService.getIndustrialAndJusticeData(entName);
            if(response == null) {
                return;
            }
        }

        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray gsDataJsonArray = (JSONArray)dataObject.getJSONObject("R11C53").get("data");

        // fix npe
        if (Objects.isNull(gsDataJsonArray) || gsDataJsonArray.size()<1){
            log.error("！！！！！！！！！！响应报文中不存在工商数据！！！！！！！！！！");
            return ;
        }
        RemoteDataModel data = JSON.parseObject(gsDataJsonArray.get(0).toString(), RemoteDataModel.class);
        //insert EDS_GS_BASIC
        for(EntWyBasic edsGsBasic : Utils.getList(data.getBasicList())){
            edsGsBasic.setReqId(reqId);
            entWyBasicMapper.insert(edsGsBasic);
        }
        //insert ent_wy_shareholderlist
        for(EntWyShareholder entWyShareholder : Utils.getList(data.getShareholderList())) {
            entWyShareholder.setReqId(reqId);
            entWyShareholderMapper.insert(entWyShareholder);
        }
        //insert ent_wy_personlist
        for(EntWyPerson entWyPerson : Utils.getList(data.getPersonList())) {
            entWyPerson.setReqId(reqId);
            entWyPersonMapper.insert(entWyPerson);
        }
        //insert ent_wy_entinvitemlist
        for(EntWyEntinvitem entWyEntinvitem : Utils.getList(data.getEntInvItemList())) {
            entWyEntinvitem.setReqId(reqId);
            entWyEntinvitemMapper.insert(entWyEntinvitem);
        }
        //insert ent_wy_frinvlist
        for(EntWyFrinv entWyFrinv : Utils.getList(data.getFrInvList())) {
            entWyFrinv.setReqId(reqId);
            entWyFrinvMapper.insert(entWyFrinv);
        }
        //insert ent_wy_frpositionlist
        for(EntWyFrposition entWyFrposition : Utils.getList(data.getFrPositionList())) {
            entWyFrposition.setReqId(reqId);
            entWyFrpositionMapper.insert(entWyFrposition);
        }
        //insert ent_wy_filiationlist
        for(EntWyFiliation entWyFiliation : Utils.getList(data.getFiliationList())) {
            entWyFiliation.setReqId(reqId);
            entWyFiliationMapper.insert(entWyFiliation);
        }
        //insert ent_wy_liquidationlist
        for(EntWyLiquidation entWyLiquidation : Utils.getList(data.getLiquidationList())) {
            entWyLiquidation.setReqId(reqId);
            entWyLiquidationMapper.insert(entWyLiquidation);
        }
        //insert ent_wy_alterlist
        for(EntWyAlter entWyAlter : Utils.getList(data.getAlterList())) {
            entWyAlter.setReqId(reqId);
            entWyAlterMapper.insert(entWyAlter);
        }
        //insert ent_wy_mortgagebasiclist
        for(EntWyMortgagebasic entWyMortgagebasic : Utils.getList(data.getMortgageBasicList())) {
            entWyMortgagebasic.setReqId(reqId);
            entWyMortgagebasicMapper.insert(entWyMortgagebasic);
        }
        //insert ent_wy_mortgagereglist
        for(EntWyMortgagereg entWyMortgagereg : Utils.getList(data.getMortgageRegList())) {
            entWyMortgagereg.setReqId(reqId);
            entWyMortgageregMapper.insert(entWyMortgagereg);
        }
        //insert ent_wy_mortgagepawnlist
        for(EntWyMortgagepawn entWyMortgagepawn : Utils.getList(data.getMortgagePawnList())) {
            entWyMortgagepawn.setReqId(reqId);
            entWyMortgagepawnMapper.insert(entWyMortgagepawn);
        }
        //insert ent_wy_mortgagealtlist
        for(EntWyMortgagealt entWyMortgagealt : Utils.getList(data.getMortgageAltList())) {
            entWyMortgagealt.setReqId(reqId);
            entWyMortgagealtMapper.insert(entWyMortgagealt);
        }
        //insert ent_wy_mortgagecanlist
        for(EntWyMortgagecan entWyMortgagecan : Utils.getList(data.getMortgageCanList())) {
            entWyMortgagecan.setReqId(reqId);
            entWyMortgagecanMapper.insert(entWyMortgagecan);
        }
        //insert ent_wy_mortgagedebtlist
        for(EntWyMortgagedebt entWyMortgagedebt : Utils.getList(data.getMortgageDebtList())) {
            entWyMortgagedebt.setReqId(reqId);
            entWyMortgagedebtMapper.insert(entWyMortgagedebt);
        }
        //insert ent_wy_mortgageperlist
        for(EntWyMortgageper entWyMortgageper : Utils.getList(data.getMortgagePerList())) {
            entWyMortgageper.setReqId(reqId);
            entWyMortgageperMapper.insert(entWyMortgageper);
        }
        //insert ent_wy_stockpawnlist
        for(EntWyStockpawn entWyStockpawn : Utils.getList(data.getStockPawnList())) {
            entWyStockpawn.setReqId(reqId);
            entWyStockpawnMapper.insert(entWyStockpawn);
        }
        //insert ent_wy_stockpawnaltlist
        for(EntWyStockpawnalt entWyStockpawnalt : Utils.getList(data.getStockPawnAltList())) {
            entWyStockpawnalt.setReqId(reqId);
            entWyStockpawnaltMapper.insert(entWyStockpawnalt);
        }
        //insert ent_wy_stockpawncanlist
        for(EntWyStockpawncan entWyStockpawncan : Utils.getList(data.getStockPawnCanList())) {
            entWyStockpawncan.setReqId(reqId);
            entWyStockpawncanMapper.insert(entWyStockpawncan);
        }
        //insert ent_wy_caseinfolist
        for(EntWyCaseinfo entWyCaseinfo : Utils.getList(data.getCaseInfoList())) {
            entWyCaseinfo.setReqId(reqId);
            entWyCaseinfoMapper.insert(entWyCaseinfo);
        }
        //insert ent_wy_exceptionlist
        for(EntWyException entWyException : Utils.getList(data.getExceptionList())) {
            entWyException.setReqId(reqId);
            entWyExceptionMapper.insert(entWyException);
        }


        //legal_wy_ssjghsj
        JSONObject ssjghsjObject = (JSONObject)dataObject.getJSONArray("R227").get(0);
        JSONArray ssjghsjData  = ssjghsjObject.getJSONArray("data");
        List<LegalWySsjghsj> legalWySsjghsjList = JSON.parseArray(ssjghsjData.toJSONString(), LegalWySsjghsj.class);
        for(LegalWySsjghsj legalWySsjghsj : Utils.getList(legalWySsjghsjList)){
            legalWySsjghsj.setReqId(reqId);
            legalWySsjghsjMapper.insert(legalWySsjghsj);
        }

        //legal_wy_bzxr_com
        JSONObject bzxrcomObject = (JSONObject)dataObject.getJSONArray("R228").get(0);
        JSONArray bzxrcomData  = bzxrcomObject.getJSONArray("data");
        List<LegalWyBzxrCom> legalWyBzxrComList = JSON.parseArray(bzxrcomData.toJSONString(), LegalWyBzxrCom.class);
        for(LegalWyBzxrCom legalWyBzxrCom : Utils.getList(legalWyBzxrComList)){
            legalWyBzxrCom.setReqId(reqId);
            legalWyBzxrComMapper.insert(legalWyBzxrCom);
        }

        //legal_wy_sxbzxr_com
        JSONObject sxbzxrcomObject = (JSONObject)dataObject.getJSONArray("R230").get(0);
        JSONArray sxbzxrcomData  = sxbzxrcomObject.getJSONArray("data");
        List<LegalWySxbzxrCom> legalWySxbzxrComList = JSON.parseArray(sxbzxrcomData.toJSONString(), LegalWySxbzxrCom.class);
        for(LegalWySxbzxrCom legalWySxbzxrCom : Utils.getList(legalWySxbzxrComList)){
            legalWySxbzxrCom.setReqId(reqId);
            legalWySxbzxrComMapper.insert(legalWySxbzxrCom);
        }


        //记录日志
        CloudQueryLog cloudQueryLog = new CloudQueryLog();
        cloudQueryLog.setEntName(entName);
        cloudQueryLog.setMessage(response);
        cloudQueryLog.setReqId(reqId);
        cloudQueryLog.setCreateTime(DateUtils.currentDate());
        cloudQueryLogService.create(cloudQueryLog);
    }

    /**
     * 创建标准表记录
     * @param reqId
     */
    public void createStdData(String reqId) {
        //从缓存获取执行sql
        List<EtlTranRule> etlTranRuleList = DetectCacheUtils.getEtlTranRuleListCache();
        //过滤工商司法转换规则
        etlTranRuleList = etlTranRuleList.stream().filter(item -> "4".equals(item.getType())).collect(Collectors.toList());
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
