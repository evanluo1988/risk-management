package com.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.springboot.domain.risk.*;
import com.springboot.domain.risk.executor.QuotaTask;
import com.springboot.mapper.*;
import com.springboot.model.IaAsPartentModel;
import com.springboot.model.QuotaModel;
import com.springboot.model.RemoteDataModel;
import com.springboot.model.StdGsEntInfoModel;
import com.springboot.model.remote.CustomerIndustrialAndJusticeRequest;
import com.springboot.model.remote.CustomerIndustrialAndJusticeResponse;
import com.springboot.service.*;
import com.springboot.service.remote.WYRemoteService;
import com.springboot.util.StrUtils;
import com.springboot.util.Utils;
import com.springboot.utils.ServerCacheUtils;
import com.springboot.utils.SqlSplicingUtils;
import com.springboot.vo.risk.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataHandleServiceImpl implements DataHandleService {
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

    @Autowired
    private StdDataService stdDataService;

    @Autowired
    private IaAsPartentService iaAsPartentService;
    @Autowired
    private IaAsBrandService iaAsBrandService;
    @Autowired
    private IaAsCopyrightService iaAsCopyrightService;


    /**
     * 创建原始表记录
     * @param reqId
     * @param entName
     */
    private void createEdsData(String reqId, String entName) {
        createIndustrialAndJustice(reqId, entName);
        createIntellectualProperty(reqId, entName);
    }

    /**
     * 创建知识产权原始表
     * @param reqId
     * @param entName
     */
    private void createIntellectualProperty(String reqId, String entName) {

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
     * 创建工商和司法原始表
     * @param reqId
     * @param entName
     */
    private void createIndustrialAndJustice(String reqId, String entName) {
        String response = wySourceDataService.getIndustrialAndJusticeData(entName);
        if(response == null){
            return;
        }

        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray gsDataJsonArray = (JSONArray)dataObject.getJSONObject("R11C53").get("data");

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
        cloudQueryLogService.create(cloudQueryLog);
    }



    /**
     * 创建标准表记录
     * @param reqId
     */
    public void createStdData(String reqId) {
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
        String reqId = UUID.randomUUID().toString();
        createEdsData(reqId, entName);
        createStdData(reqId);
        analysisJustice(reqId);

        //设置时效性，并记录日志
        cloudInfoTimelinessService.updateTimeLiness(entName, reqId);
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
        List<Quota> quotas = quotaList.stream().filter(item -> item.getQuotaType().equals("QUOTA")).collect(Collectors.toList());
        //初始化指标任务
        List<QuotaTask> quotaTaskList = Lists.newArrayList();
        for(Quota quota : Utils.getList(quotas)) {
            quotaTaskList.add(new QuotaTask(reqId ,quota));
        }
        List<QuotaValue> quotaValueList = culQuotaTasks(quotaTaskList);

        //save all quota values
        quotaValueService.saveQuotaValues(quotaValueList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void culModels(String reqId) {
        List<Quota> quotaList = ServerCacheUtils.getQuotaList();
        if(CollectionUtils.isEmpty(quotaList)){
            return;
        }
        List<Quota> quotas = quotaList.stream().filter(item -> item.getQuotaType().equals("MODEL")).collect(Collectors.toList());
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
                //if(quotaTask.getQuota().getId() == 58L){
                quotaValueList.add(quotaTask.call());
                //}
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return quotaValueList;
    }

    @Override
    public void analysisJustice(String reqId) throws Exception {
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

    @Override
    public EntHealthReportVo getEntHealthReportVo(String reqId) {
        List<QuotaModel> quotaModelList = quotaValueService.getQuotaList(reqId);
        StdGsEntInfoModel stdGsEntInfo = stdDataService.getStdGsEntInfo(reqId);
        EntHealthReportVo entHealthReportVo = new EntHealthReportVo();
        //企业健康评价
        entHealthReportVo.setEntHealthAssessment(getEntHealthAssessment(quotaModelList));
        //企业健康详情
        entHealthReportVo.setEntHealthDetails(getEntHealthDetails(reqId, quotaModelList, stdGsEntInfo));
        return entHealthReportVo;
    }

    /**
     * 企业健康评价
     * @return
     */
    private EntHealthAssessmentVo getEntHealthAssessment(List<QuotaModel> quotaModelList) {
        EntHealthAssessmentVo entHealthAssessment = new EntHealthAssessmentVo();
        entHealthAssessment.setEntHealthDetectionRadar(getEntHealthDetectionRadar(quotaModelList));
        entHealthAssessment.setEntHealthDialysis(getEntHealthDialysis(quotaModelList));
        return entHealthAssessment;
    }

    /**
     * 企业健康检测雷达
     * @return
     */
    private EntHealthDetectionRadarVo getEntHealthDetectionRadar(List<QuotaModel> quotaModelList) {
        EntHealthDetectionRadarVo entHealthDetectionRadarVo = new EntHealthDetectionRadarVo();
        for(QuotaModel quotaModel : quotaModelList) {
            switch(quotaModel.getQuotaCode().trim()){
                case "GS_ENT_INDUSTRY":
                    entHealthDetectionRadarVo.setIndustry(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_CREDITCODE":
                    entHealthDetectionRadarVo.setCreditCode(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_REGCAP":
                    entHealthDetectionRadarVo.setRegCap(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_REGCAPCUR":
                    entHealthDetectionRadarVo.setRegCapCur(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_LRNAME":
                    entHealthDetectionRadarVo.setLrName(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_ESTABLISH_PERIOD":
                    entHealthDetectionRadarVo.setEstablishPeriod(quotaModel.getQuotaValue());
                    break;
            }
        }
        return entHealthDetectionRadarVo;
    }

    /**
     * 企业健康检测透析
     */
    private EntHealthDialysisVo getEntHealthDialysis(List<QuotaModel> quotaModelList) {
        EntHealthDialysisVo entHealthDialysis = new EntHealthDialysisVo();
        FiveDRaderVo fiveDRader = new FiveDRaderVo();
        List<FiveDRaderItemVo> fiveDRaderItemList = Lists.newArrayList();
        fiveDRader.setFiveDRaderItemList(fiveDRaderItemList);

        List<DialysisVo> businessStabilityList = Lists.newArrayList();
        List<DialysisVo> businessRiskList = Lists.newArrayList();
        List<DialysisVo> legalRiskList = Lists.newArrayList();
        entHealthDialysis.setBusinessStabilityList(businessStabilityList);
        entHealthDialysis.setBusinessRiskList(businessRiskList);
        entHealthDialysis.setLegalRiskList(legalRiskList);
        entHealthDialysis.setFiveDRader(fiveDRader);

        for(QuotaModel quotaModel : quotaModelList) {
            switch(quotaModel.getQuotaCode().trim()){
                case "GS_BUSINESS_CONTINUITY":
                case "GS_BUSINESS_CHANGE":
                case "GS_CONTROL_STABILITY":
                case "GS_INVESTOR_CAPACITY":
                case "GS_BUSINESS_ANOMALIES":
                case "GS_MANAGE_ANOMALIES":
                case "GS_ADMINISTRATIVE_SANCTION":
                case "SF_CASE_RISK":
                case "SF_LITIGATION_RELATED_BEHAVIOR":
                    //5维雷达透析
                    fiveDRaderItemList.add(createFiveDRaderItemVo(quotaModel));
                    break;
                case "GS_ENT_ESTABLISH_PERIOD" :
                case "GS_NUM_MANAGE_CHANGE" :
                case "GS_NUM_INVESTOR_WITHDRAW":
                case "GS_NUM_LEGALPERSON_CHANGE":
                case "GS_REDU_RATIO_REGCAP":
                case "GS_MAX_SHARE_RATIO":
                case "GS_NUM_SHAREHOLDER":
                case "GS_MAX_QUALIFICATION_SHAREHOLDER":
                    //经营稳定性透析
                    businessStabilityList.add(createDialysisVo(quotaModel));
                    break;
                case "GS_ENT_ABNORMAL_STATE":
                case "GS_NUM_COMMERCIAL_PENALTIES":
                case "GS_NUM_SHARES_FROZEN":
                case "GS_NUM_ABNORMAL_DIRECTORY":
                case "GS_HAS_LIQUIDATION_RECORD":
                    //经营风险透析
                    businessRiskList.add(createDialysisVo(quotaModel));
                    break;
                case "SF_NUM_HIGH_RISK":
                case "SF_NUM_MEDIUM_RISK":
                case "SF_NUM_LOW_RISK":
                case "SF_LITIGA_FREQUENCY":
                case "SF_TENDENCY_JUDG_RESULT":
                case "SF_LITIGA_INITIATIVE":
                    //法律风险透析
                    legalRiskList.add(createDialysisVo(quotaModel));
                    break;
            }
        }
        culFiveDRader(fiveDRader, quotaModelList);

        return entHealthDialysis;
    }

    /**
     * 企业健康详情
     * @return
     */
    private EntHealthDetailsVo getEntHealthDetails(String reqId, List<QuotaModel> quotaModelList, StdGsEntInfoModel stdGsEntInfo) {
        EntHealthDetailsVo entHealthDetails = new EntHealthDetailsVo();
        entHealthDetails.setEntRegInformation(getEntRegInformation(quotaModelList, stdGsEntInfo));
        entHealthDetails.setEntAlterList(getEntAlter(stdGsEntInfo));
        entHealthDetails.setEntAbnormalDetails(getEntAbnormalDetails(stdGsEntInfo));
        //涉诉案件列表
        entHealthDetails.setLitigaCaseList(stdLegalService.getLitigaCase(reqId));
        return entHealthDetails;
    }

    /**
     * 企业注册信息
     * @return
     */
    private EntRegInformationVo getEntRegInformation(List<QuotaModel> quotaModelList, StdGsEntInfoModel stdGsEntInfo) {
        EntRegInformationVo entRegInformation = new EntRegInformationVo();
        //EntBasicInformationVo entBasicInformation = new EntBasicInformationVo();
        List<PersonnelVo> personnelList = Lists.newArrayList();
        List<ShareholderVo> shareholderList = Lists.newArrayList();
        entRegInformation.setEntBasicInformation(getEntBasicInformation(quotaModelList));

        entRegInformation.setPersonnelList(personnelList);
        entRegInformation.setShareholderList(shareholderList);

        //主要人员
        for(StdEntPerson stdEntPerson : Utils.getList(stdGsEntInfo.getStdEntPeople())) {
            PersonnelVo personnelVo = new PersonnelVo();
            BeanUtils.copyProperties(stdEntPerson, personnelVo);
            personnelList.add(personnelVo);
        }
        //股东信息
        for(StdEntShareHolder stdEntShareHolder : Utils.getList(stdGsEntInfo.getStdEntShareHolders())) {
            ShareholderVo shareholderVo = new ShareholderVo();
            BeanUtils.copyProperties(stdEntShareHolder, shareholderVo);
            shareholderList.add(shareholderVo);
        }
        //分支机构不要了

        return entRegInformation;
    }

    /**
     * 企业基本信息
     * @return
     */
    private EntBasicInformationVo getEntBasicInformation(List<QuotaModel> quotaModelList){
        EntBasicInformationVo entBasicInformation = new EntBasicInformationVo();
        for(QuotaModel quotaModel : quotaModelList) {
            switch(quotaModel.getQuotaCode().trim()){
                case "GS_ENT_LRNAME":
                    entBasicInformation.setLrName(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_ENTTYPE":
                    entBasicInformation.setEntType(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_REGCAP":
                    entBasicInformation.setRegCap(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_REGCAPCUR":
                    entBasicInformation.setRegCapCur(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_RECCAP":
                    entBasicInformation.setRecCap(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_ANCHEYEAR":
                    entBasicInformation.setAncheYear(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_INDUSTRY":
                    entBasicInformation.setIndustry(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_CANDATE":
                    entBasicInformation.setCanDate(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_OPENFROM":
                    entBasicInformation.setOpenFrom(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_REVDATE":
                    entBasicInformation.setRevDate(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_REGNO":
                    entBasicInformation.setRegNo(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_EMPNUM":
                    entBasicInformation.setEmpNum(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_CREDITCODE":
                    entBasicInformation.setCreditCode(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_OPENTO":
                    entBasicInformation.setOpenTo(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_ADDRESS":
                    entBasicInformation.setAddress(quotaModel.getQuotaValue());
                    break;
                case "GS_ENT_ORGCODE":
                    entBasicInformation.setOrgCode(quotaModel.getQuotaValue());
                    break;
                case "GX_ENT_OPERATESCOPE":
                    entBasicInformation.setOperateScope(quotaModel.getQuotaValue());
                    break;
                case "GX_ENT_REGORG":
                    entBasicInformation.setRegOrg(quotaModel.getQuotaValue());
                    break;
            }
        }
        return entBasicInformation;
    }

    /**
     * 企业经营发展信息
     */
    private List<EntAlterVo> getEntAlter(StdGsEntInfoModel stdGsEntInfo) {
        List<EntAlterVo> entAlterList = Lists.newArrayList();
        for(StdEntAlter stdEntAlter : Utils.getList(stdGsEntInfo.getStdEntAlters())) {
            EntAlterVo entAlterVo = new EntAlterVo();
            BeanUtils.copyProperties(stdEntAlter, entAlterVo);
            entAlterList.add(entAlterVo);
        }
        return entAlterList;
    }

    /**
     * 企业经营异常详情
     */
    private EntAbnormalDetailsVo getEntAbnormalDetails(StdGsEntInfoModel stdGsEntInfo){
        EntAbnormalDetailsVo entAbnormalDetails = new EntAbnormalDetailsVo();
        List<EntCaseinfoVo> caseinfoList = Lists.newArrayList();
        List<EntSharesfrostVo> entSharesfrostList = Lists.newArrayList();
        List<EntExceptionVo> entExceptionList = Lists.newArrayList();
        List<EntLiquidationVo> entLiquidationList = Lists.newArrayList();


        entAbnormalDetails.setCaseinfoList(caseinfoList);
        entAbnormalDetails.setEntSharesfrostList(entSharesfrostList);
        entAbnormalDetails.setEntExceptionList(entExceptionList);
        entAbnormalDetails.setEntLiquidationList(entLiquidationList);

        //行政处罚
        for(StdEntCaseinfo stdEntCaseinfo : Utils.getList(stdGsEntInfo.getStdEntCaseinfos())) {
            EntCaseinfoVo entCaseinfoVo = new EntCaseinfoVo();
            BeanUtils.copyProperties(stdEntCaseinfo, entCaseinfoVo);
            caseinfoList.add(entCaseinfoVo);
        }
        //股权冻结
        for(StdEntSharesfrost stdEntSharesfrost : stdGsEntInfo.getStdEntSharesfrosts()){
            EntSharesfrostVo entSharesfrostVo = new EntSharesfrostVo();
            BeanUtils.copyProperties(stdEntSharesfrost, entSharesfrostVo);
            entSharesfrostList.add(entSharesfrostVo);
        }
        //企业异常名录
        for(StdEntException stdEntException : stdGsEntInfo.getStdEntExceptions()) {
            EntExceptionVo entExceptionVo = new EntExceptionVo();
            BeanUtils.copyProperties(stdEntException, entExceptionVo);
            entExceptionList.add(entExceptionVo);
        }
        //企业清算信息
        for(StdEntLiquidation stdEntLiquidation : stdGsEntInfo.getStdEntLiquidations()) {
            EntLiquidationVo entLiquidationVo = new EntLiquidationVo();
            BeanUtils.copyProperties(stdEntLiquidation, entLiquidationVo);
            entLiquidationList.add(entLiquidationVo);
        }

        return entAbnormalDetails;
    }






    /**
     * 计算5维雷达综合得分
     * @param fiveDRader
     */
    private void culFiveDRader(FiveDRaderVo fiveDRader, List<QuotaModel> quotaModelList) {
        List<Long> firstLevelIds = Arrays.asList(new Long[] {10L,11L,12L,13L});
        Map<Long, List<QuotaModel>> quotaModelMap = quotaModelList.stream()
                .filter(item -> (firstLevelIds.contains(item.getFirstLevelId()) && "QUOTA".equals(item.getQuotaType()) && !"Y".equals(item.getIdealInterval())))
                .collect(Collectors.groupingBy(QuotaModel::getFirstLevelId));
        for(Long key : quotaModelMap.keySet()) {
            double score = 100;
            for(QuotaModel quotaModel : Utils.getList(quotaModelMap.get(key))) {
                score = score - quotaModel.getMinusPoints();
            }
            if(key == 10) {
                fiveDRader.setBusinessStabilityScore(score);
            } else if(key == 11) {
                fiveDRader.setIntellectualPropertyScore(score);
            } else if(key == 12) {
                fiveDRader.setBusinessRiskScore(score);
            } else if(key == 13) {
                fiveDRader.setLegalRiskScore(score);
            }
        }
    }

    private String getDimensionName(Long dimensionId) {
        QuotaDimension quotaDimension = ServerCacheUtils.getQuotaDimensionById(dimensionId);
        return quotaDimension != null ? quotaDimension.getDimensionName() : null;
    }

    /**
     * 创建透析实体
     * @param quotaModel
     * @return
     */
    private DialysisVo createDialysisVo(QuotaModel quotaModel) {
        DialysisVo dialysisVo = new DialysisVo();
        dialysisVo.setQuotaId(quotaModel.getId());
        dialysisVo.setEvaluationRadar(getDimensionName(quotaModel.getFirstLevelId()));
        dialysisVo.setEvaluationDimension(getDimensionName(quotaModel.getSecondLevelId()));
        dialysisVo.setTimeInterval(quotaModel.getTimeInterval());
        dialysisVo.setConcerns(quotaModel.getQuotaName());
        dialysisVo.setActualValue(quotaModel.getQuotaValue());
        //todo 理想区间
        return dialysisVo;
    }

    private FiveDRaderItemVo createFiveDRaderItemVo(QuotaModel quotaModel) {
        FiveDRaderItemVo fiveDRaderItemVo = new FiveDRaderItemVo();
        fiveDRaderItemVo.setQuotaId(quotaModel.getId());
        fiveDRaderItemVo.setQuotaName(quotaModel.getQuotaName());
        fiveDRaderItemVo.setQuotaCode(quotaModel.getQuotaCode());
        fiveDRaderItemVo.setQuotaValue(quotaModel.getQuotaValue());
        fiveDRaderItemVo.setMinusPoints(quotaModel.getMinusPoints());
        fiveDRaderItemVo.setFirstLevelId(quotaModel.getFirstLevelId());
        return fiveDRaderItemVo;
    }
}
