package com.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.springboot.domain.risk.*;
import com.springboot.domain.risk.executor.QuotaTask;
import com.springboot.mapper.*;
import com.springboot.model.RemoteDataModel;
import com.springboot.service.*;
import com.springboot.service.remote.WYRemoteService;
import com.springboot.util.StrUtils;
import com.springboot.utils.ServerCacheUtils;
import com.springboot.utils.SqlSplicingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class DataHandleServiceImpl implements DataHandleService {

    @Value("${wy.product.code}")
    private String productCode;

    @Value("${wy.appid}")
    private String appId;

    @Value("${wy.appkey}")
    private String appKey;
    @Value("${wy.aeskey}")
    private String aesKey;



    @Autowired
    private WYRemoteService wyRemoteService;
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
    private StdLegalService stdLegalService;



    @Autowired
    private ExeSqlMapper exeSqlMapper;

    @Autowired
    private CloudInfoTimelinessService cloudInfoTimelinessService;
    @Autowired
    private CloudQueryLogService cloudQueryLogService;
    @Autowired
    private QuotaValueService quotaValueService;


    @Autowired
    private StdLegalEnterpriseExecutedService stdLegalEnterpriseExecutedService;
    @Autowired
    private StdLegalEntUnexecutedService stdLegalEntUnexecutedService;

    @Autowired
    private JudgeAnynasisMapper judgeAnynasisMapper;
    @Autowired
    private LegalDataAddColumnServiceImpl legalDataAddColumnService;
    @Autowired
    private StdLegalDataStructuredService stdLegalDataStructuredService;
    @Autowired
    private StdLegalDataAddedService stdLegalDataAddedService;

    private String grabData(String entName) {
        WYRemoteService.CustomerDataCollectionRequest customerDataCollectionRequest = new WYRemoteService.CustomerDataCollectionRequest();
        String businessId = StrUtils.randomStr(20);
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sign = WYRemoteService.calcSign(businessId,timeStamp,appKey);
        customerDataCollectionRequest
                .setBusinessID(businessId)
                .setEntName("广西南宁卓信商贸有限公司")
                .setEntCreditID("911112345671234567")
                .setIndName("黄日林")
                .setIndCertID("uTln8yoWNBcFDHgUv24IXvQTVoGyDvrYzKvAcHzbyhM=")
                .setProductCode(productCode)
                .setAppID(appId)
                .setTimestamp(timeStamp)
                .setSignature(sign);

        System.out.println("请求报文："+JSON.toJSONString(customerDataCollectionRequest));
        WYRemoteService.CustomerDataCollectionResponse customerDataCollectionResponse = wyRemoteService.customerDataCollection(customerDataCollectionRequest);

        return JSON.toJSONString(customerDataCollectionResponse);
    }


    /**
     * 创建原始表记录
     * @param reqId
     * @param response
     */
    private void createEdsData(String reqId, String response) {

        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray gsDataJsonArray = (JSONArray)dataObject.getJSONObject("R11C53").get("data");

        RemoteDataModel data = JSON.parseObject(gsDataJsonArray.get(0).toString(), RemoteDataModel.class);
        //insert EDS_GS_BASIC
        for(EntWyBasic edsGsBasic : data.getBasicList()){
            edsGsBasic.setReqId(reqId);
            entWyBasicMapper.insert(edsGsBasic);
        }
        //insert ent_wy_shareholderlist
        for(EntWyShareholder entWyShareholder : data.getShareholderList()) {
            entWyShareholder.setReqId(reqId);
            entWyShareholderMapper.insert(entWyShareholder);
        }
        //insert ent_wy_personlist
        for(EntWyPerson entWyPerson : data.getPersonList()) {
            entWyPerson.setReqId(reqId);
            entWyPersonMapper.insert(entWyPerson);
        }
        //insert ent_wy_entinvitemlist
        for(EntWyEntinvitem entWyEntinvitem : data.getEntInvItemList()) {
            entWyEntinvitem.setReqId(reqId);
            entWyEntinvitemMapper.insert(entWyEntinvitem);
        }
        //insert ent_wy_frinvlist
        for(EntWyFrinv entWyFrinv : data.getFrInvList()) {
            entWyFrinv.setReqId(reqId);
            entWyFrinvMapper.insert(entWyFrinv);
        }
        //insert ent_wy_frpositionlist
        for(EntWyFrposition entWyFrposition : data.getFrPositionList()) {
            entWyFrposition.setReqId(reqId);
            entWyFrpositionMapper.insert(entWyFrposition);
        }
        //insert ent_wy_filiationlist
        for(EntWyFiliation entWyFiliation : data.getFiliationList()) {
            entWyFiliation.setReqId(reqId);
            entWyFiliationMapper.insert(entWyFiliation);
        }
        //insert ent_wy_liquidationlist
        for(EntWyLiquidation entWyLiquidation : data.getLiquidationList()) {
            entWyLiquidation.setReqId(reqId);
            entWyLiquidationMapper.insert(entWyLiquidation);
        }
        //insert ent_wy_alterlist
        for(EntWyAlter entWyAlter : data.getAlterList()) {
            entWyAlter.setReqId(reqId);
            entWyAlterMapper.insert(entWyAlter);
        }
        //insert ent_wy_mortgagebasiclist
        for(EntWyMortgagebasic entWyMortgagebasic : data.getMortgageBasicList()) {
            entWyMortgagebasic.setReqId(reqId);
            entWyMortgagebasicMapper.insert(entWyMortgagebasic);
        }
        //insert ent_wy_mortgagereglist
        for(EntWyMortgagereg entWyMortgagereg : data.getMortgageRegList()) {
            entWyMortgagereg.setReqId(reqId);
            entWyMortgageregMapper.insert(entWyMortgagereg);
        }
        //insert ent_wy_mortgagepawnlist
        for(EntWyMortgagepawn entWyMortgagepawn : data.getMortgagePawnList()) {
            entWyMortgagepawn.setReqId(reqId);
            entWyMortgagepawnMapper.insert(entWyMortgagepawn);
        }
        //insert ent_wy_mortgagealtlist
        for(EntWyMortgagealt entWyMortgagealt : data.getMortgageAltList()) {
            entWyMortgagealt.setReqId(reqId);
            entWyMortgagealtMapper.insert(entWyMortgagealt);
        }
        //insert ent_wy_mortgagecanlist
        for(EntWyMortgagecan entWyMortgagecan : data.getMortgageCanList()) {
            entWyMortgagecan.setReqId(reqId);
            entWyMortgagecanMapper.insert(entWyMortgagecan);
        }
        //insert ent_wy_mortgagedebtlist
        for(EntWyMortgagedebt entWyMortgagedebt : data.getMortgageDebtList()) {
            entWyMortgagedebt.setReqId(reqId);
            entWyMortgagedebtMapper.insert(entWyMortgagedebt);
        }
        //insert ent_wy_mortgageperlist
        for(EntWyMortgageper entWyMortgageper : data.getMortgagePerList()) {
            entWyMortgageper.setReqId(reqId);
            entWyMortgageperMapper.insert(entWyMortgageper);
        }
        //insert ent_wy_stockpawnlist
        for(EntWyStockpawn entWyStockpawn : data.getStockPawnList()) {
            entWyStockpawn.setReqId(reqId);
            entWyStockpawnMapper.insert(entWyStockpawn);
        }
        //insert ent_wy_stockpawnaltlist
        for(EntWyStockpawnalt entWyStockpawnalt : data.getStockPawnAltList()) {
            entWyStockpawnalt.setReqId(reqId);
            entWyStockpawnaltMapper.insert(entWyStockpawnalt);
        }
        //insert ent_wy_stockpawncanlist
        for(EntWyStockpawncan entWyStockpawncan : data.getStockPawnCanList()) {
            entWyStockpawncan.setReqId(reqId);
            entWyStockpawncanMapper.insert(entWyStockpawncan);
        }
        //insert ent_wy_caseinfolist
        for(EntWyCaseinfo entWyCaseinfo : data.getCaseInfoList()) {
            entWyCaseinfo.setReqId(reqId);
            entWyCaseinfoMapper.insert(entWyCaseinfo);
        }
        //insert ent_wy_exceptionlist
        for(EntWyException entWyException : data.getExceptionList()) {
            entWyException.setReqId(reqId);
            entWyExceptionMapper.insert(entWyException);
        }


        //legal_wy_ssjghsj
        JSONObject ssjghsjObject = (JSONObject)dataObject.getJSONArray("R227").get(0);
        JSONArray ssjghsjData  = ssjghsjObject.getJSONArray("data");
        List<LegalWySsjghsj> legalWySsjghsjList = JSON.parseArray(ssjghsjData.toJSONString(), LegalWySsjghsj.class);
        for(LegalWySsjghsj legalWySsjghsj : legalWySsjghsjList){
            legalWySsjghsj.setReqId(reqId);
            legalWySsjghsjMapper.insert(legalWySsjghsj);
        }

        //legal_wy_bzxr_com
        JSONObject bzxrcomObject = (JSONObject)dataObject.getJSONArray("R228").get(0);
        JSONArray bzxrcomData  = bzxrcomObject.getJSONArray("data");
        List<LegalWyBzxrCom> legalWyBzxrComList = JSON.parseArray(bzxrcomData.toJSONString(), LegalWyBzxrCom.class);
        for(LegalWyBzxrCom legalWyBzxrCom : legalWyBzxrComList){
            legalWyBzxrCom.setReqId(reqId);
            legalWyBzxrComMapper.insert(legalWyBzxrCom);
        }

        //legal_wy_sxbzxr_com
        JSONObject sxbzxrcomObject = (JSONObject)dataObject.getJSONArray("R230").get(0);
        JSONArray sxbzxrcomData  = sxbzxrcomObject.getJSONArray("data");
        List<LegalWySxbzxrCom> legalWySxbzxrComList = JSON.parseArray(sxbzxrcomData.toJSONString(), LegalWySxbzxrCom.class);
        for(LegalWySxbzxrCom legalWySxbzxrCom : legalWySxbzxrComList){
            legalWySxbzxrCom.setReqId(reqId);
            legalWySxbzxrComMapper.insert(legalWySxbzxrCom);
        }

    }


    /**
     * 创建标准表记录
     * @param reqId
     */
    private void createStdData(String reqId) {
        //从缓存获取执行sql
        List<EtlTranRule> etlTranRuleList = ServerCacheUtils.getEtlTranRuleListCache();
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



    @Override
    @Transactional(rollbackFor = Exception.class)
    public String handelData(String entName) throws Exception {
        String response = grabData(entName);
        String reqId = UUID.randomUUID().toString();
        createEdsData(reqId, response);
        createStdData(reqId);
        analysisJustice(reqId);


        //设置时效性，并记录日志
        cloudInfoTimelinessService.updateTimeLiness(entName, reqId);

        CloudQueryLog cloudQueryLog = new CloudQueryLog();
        cloudQueryLog.setEntName(entName);
        cloudQueryLog.setMessage(response);
        cloudQueryLog.setReqId(reqId);
        cloudQueryLogService.create(cloudQueryLog);

        return reqId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void culQuotas(String reqId) {
        //通过reqId先查找企业指标值是否存在，如果存在直接获取，如果不存在就通过标准表重新计算
        //先得到指标列表
        List<Quota> quotaList = ServerCacheUtils.getQuotaList();
        if(CollectionUtils.isEmpty(quotaList)){
            return;
        }
        //初始化指标任务
        List<QuotaTask> quotaTaskList = Lists.newArrayList();
        for(Quota quota : quotaList) {
            quotaTaskList.add(new QuotaTask(reqId ,quota));
        }

        //执行任务
        List<QuotaValue> quotaValueList = Lists.newArrayList();
        try {
            for(QuotaTask quotaTask : quotaTaskList){

                if(quotaTask.getQuota().getId() == 58L){
                    quotaValueList.add(quotaTask.call());
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        //save all quota values
        quotaValueService.saveQuotaValues(quotaValueList);
    }

    @Override
    public void analysisJustice(String reqId) throws Exception {
        //解析司法引擎
        //legalDataAddColumnService.initAnalysisJudicialEngine();
        List<StdLegalDataStructured> stdLegalDataStructuredList = stdLegalDataStructuredService.findStdLegalDataStructuredByReqId(reqId);
        List<StdLegalDataAdded> stdLegalDataAddedList = Lists.newArrayList();
        for(StdLegalDataStructured legalDataStructured : stdLegalDataStructuredList) {
            stdLegalDataAddedList.addAll(legalDataAddColumnService.anynasisAndInsert(legalDataStructured ,reqId, null));
        }
        stdLegalDataAddedService.saveStdLegalDataAddedValues(stdLegalDataAddedList);
        //生成中间表
        stdLegalService.createStdLegalMidTable(reqId);
    }
}
