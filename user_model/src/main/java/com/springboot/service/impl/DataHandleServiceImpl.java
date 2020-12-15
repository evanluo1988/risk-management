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
    private EntWyBasicMapper edsGsBasicMapper;
//    @Autowired
//    private EdsGsQygdjczxxMapper edsGsQygdjczxxMapper;
//    @Autowired
//    private EdsGsQyzyglryMapper edsGsQyzyglryMapper;
//    @Autowired
//    private EdsGsQydwtzxxMapper edsGsQydwtzxxMapper;
//    @Autowired
//    private EdsGsFddbrdwtzxxMapper edsGsFddbrdwtzxxMapper;
//    @Autowired
//    private EdsGsFddbrzqtqyrzxxMapper edsGsFddbrzqtqyrzxxMapper;
//    @Autowired
//    private EdsGsFzjgxxMapper edsGsFzjgxxMapper;
//    @Autowired
//    private EdsGsQylsbgxxMapper edsGsQylsbgxxMapper;
//    @Autowired
//    private EdsGsGqdjlsxxMapper edsGsGqdjlsxxMapper;
//    @Autowired
//    private LegalWyBzxrComMapper edsSsBzxrComMapper;
//    @Autowired
//    private EdsSsSsjghsjMapper edsSsSsjghsjMapper;
//    @Autowired
//    private LegalWySxbzxrComMapper edsSsSxbzxrComMapper;
    @Autowired
    private ExeSqlMapper exeSqlMapper;

    @Autowired
    private CloudInfoTimelinessService cloudInfoTimelinessService;
    @Autowired
    private CloudQueryLogService cloudQueryLogService;
    @Autowired
    private QuotaValueService quotaValueService;

    @Autowired
    private StdLegalDataStructuredService stdLegalDataStructuredService;
    @Autowired
    private StdLegalEnterpriseExecutedService stdLegalEnterpriseExecutedService;
    @Autowired
    private StdLegalEntUnexecutedService stdLegalEntUnexecutedService;

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

        //JSONObject jsonObject = JSONObject.parseObject(customerDataCollectionResponse.getData());
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray gsDataJsonArray = (JSONArray)dataObject.getJSONObject("R11C53").get("data");

        RemoteDataModel data = JSON.parseObject(gsDataJsonArray.get(0).toString(), RemoteDataModel.class);
        //insert EDS_GS_BASIC
        for(EntWyBasic edsGsBasic : data.getBasicList()){
            edsGsBasic.setReqId(reqId);
            edsGsBasicMapper.insert(edsGsBasic);
        }
        //insert ent_wy_basiclist
//        for(EdsGsQygdjczxx edsGsQygdjczxx: data.getShareholderList()){
//            edsGsQygdjczxx.setReqId(reqId);
//            edsGsQygdjczxxMapper.insert(edsGsQygdjczxx);
//        }
//        //insert EDS_GS_QYZYGLRY
//        for(EdsGsQyzyglry edsGsQyzyglry : data.getPersonList()){
//            edsGsQyzyglry.setReqId(reqId);
//            edsGsQyzyglryMapper.insert(edsGsQyzyglry);
//        }
//        //EDS_GS_QYDWTZXX
//        for(EdsGsQydwtzxx edsGsQydwtzxx : data.getEntInvItemList()){
//            edsGsQydwtzxx.setReqId(reqId);
//            edsGsQydwtzxxMapper.insert(edsGsQydwtzxx);
//        }
//        //EDS_GS_FDDBRDWTZXX
//        for(EdsGsFddbrdwtzxx edsGsFddbrdwtzxx : data.getFrInvList()) {
//            edsGsFddbrdwtzxx.setReqId(reqId);
//            edsGsFddbrdwtzxxMapper.insert(edsGsFddbrdwtzxx);
//        }
//        //EDS_GS_FDDBRZQTQYRZXX
//        for(EdsGsFddbrzqtqyrzxx edsGsFddbrzqtqyrzxx : data.getFrPositionList()) {
//            edsGsFddbrzqtqyrzxx.setReqId(reqId);
//            edsGsFddbrzqtqyrzxxMapper.insert(edsGsFddbrzqtqyrzxx);
//        }
//        //EDS_GS_FZJGXX
//        for(EdsGsFzjgxx edsGsFzjgxx : data.getFiliationList()){
//            edsGsFzjgxx.setReqId(reqId);
//            edsGsFzjgxxMapper.insert(edsGsFzjgxx);
//        }
//        //EDS_GS_QYLSBGXX
//        for(EdsGsQylsbgxx edsGsQylsbgxx : data.getAlterList()) {
//            edsGsQylsbgxx.setReqId(reqId);
//            edsGsQylsbgxxMapper.insert(edsGsQylsbgxx);
//        }
//        //EDS_GS_GQDJLSXX
//        for(EdsGsGqdjlsxx edsGsGqdjlsxx : data.getSharesFrostList()){
//            edsGsGqdjlsxx.setReqId(reqId);
//            edsGsGqdjlsxxMapper.insert(edsGsGqdjlsxx);
//        }
//
//        //EDS_SS_SSJGHSJ
//        JSONObject ssjghsjObject = (JSONObject)dataObject.getJSONArray("R227").get(0);
//        JSONArray ssjghsjData  = ssjghsjObject.getJSONArray("data");
//        List<EdsSsSsjghsj> edsSsSsjghsjList = JSON.parseArray(ssjghsjData.toJSONString(), EdsSsSsjghsj.class);
//        for(EdsSsSsjghsj edsSsSsjghsj : edsSsSsjghsjList){
//            edsSsSsjghsj.setReqId(reqId);
//            edsSsSsjghsjMapper.insert(edsSsSsjghsj);
//        }
//
//        //EDS_SS_BZXR_COM
//        JSONObject bzxrcomObject = (JSONObject)dataObject.getJSONArray("R228").get(0);
//        JSONArray bzxrcomData  = bzxrcomObject.getJSONArray("data");
//        List<LegalWyBzxrCom> edsSsBzxrComListList = JSON.parseArray(bzxrcomData.toJSONString(), LegalWyBzxrCom.class);
//        for(LegalWyBzxrCom edsSsBzxrCom : edsSsBzxrComListList){
//            edsSsBzxrCom.setReqId(reqId);
//            edsSsBzxrComMapper.insert(edsSsBzxrCom);
//        }
//
//        //EDS_SS_SXBZXR_COM
//        JSONObject sxbzxrcomObject = (JSONObject)dataObject.getJSONArray("R230").get(0);
//        JSONArray sxbzxrcomData  = sxbzxrcomObject.getJSONArray("data");
//        List<LegalWySxbzxrCom> edsSsSxbzxrComList = JSON.parseArray(sxbzxrcomData.toJSONString(), LegalWySxbzxrCom.class);
//        for(LegalWySxbzxrCom edsSsSxbzxrCom : edsSsSxbzxrComList){
//            edsSsSxbzxrCom.setReqId(reqId);
//            edsSsSxbzxrComMapper.insert(edsSsSxbzxrCom);
//        }

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
    public String handelData(String entName) {
        String response = grabData(entName);
        String reqId = UUID.randomUUID().toString();
        createEdsData(reqId, response);
        createStdData(reqId);

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
                if(quotaTask.getQuota().getId() == 100L
                ){
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
    public void preStdSsDatas(String reqId) {
//        List<StdLegalDataStructured> set1 = stdLegalDataStructuredService.findStdLegalDataStructuredByReqId(reqId);
//        List<StdLegalEnterpriseExecuted> set2 = stdLegalEnterpriseExecutedService.findStdLegalEnterpriseExecutedByReqId(reqId);
//        List<StdLegalEntUnexecuted> set3 = stdLegalEntUnexecutedService.findStdLegalEntUnexecutedByReqId(reqId);
//
//        List<String> set1CaseNoList = set1.stream().filter(item -> item.getCaseNo() != null)
//                .map(StdLegalDataStructured::getCaseNo)
//                .collect(Collectors.toList());
//        List<String> set3CaseNoList = set3.stream().filter(item -> item.getCaseCode() != null)
//                .map(StdLegalEntUnexecuted::getCaseCode)
//                .collect(Collectors.toList());
//        //①若set1与set2存在非空【案号】一致的案件记录，则保留set1的案件记录；
//        if(!CollectionUtils.isEmpty(set2)) {
//            Iterator<StdLegalEnterpriseExecuted> set2Iterator = set2.iterator();
//            while(set2Iterator.hasNext()){
//                StdLegalEnterpriseExecuted stdLegalEnterpriseExecuted = set2Iterator.next();
//                if(set1CaseNoList.contains(stdLegalEnterpriseExecuted.getCaseCode())) {
//                    stdLegalEnterpriseExecutedService.delete(stdLegalEnterpriseExecuted);
//                    set2Iterator.remove();
//                }
//            }
//        }
//
//        //②若set1与set3存在非空【案号】一致的案件记录，则保留set3的案件记录；
//        if(!CollectionUtils.isEmpty(set1)) {
//            Iterator<StdLegalDataStructured> set1Iterator = set1.iterator();
//            while(set1Iterator.hasNext()){
//                StdLegalDataStructured stdLegalDataStructured = set1Iterator.next();
//                if(set3CaseNoList.contains(stdLegalDataStructured.getCaseNo())) {
//                    stdLegalDataStructuredService.delete(stdLegalDataStructured);
//                    set1Iterator.remove();
//                }
//            }
//        }
//
//        //③若set2与set3存在非空【案号】一致的案件记录，则保留set3的案件记录。
//        if(!CollectionUtils.isEmpty(set2)){
//            Iterator<StdLegalEnterpriseExecuted> set2Iterator = set2.iterator();
//            while(set2Iterator.hasNext()){
//                StdLegalEnterpriseExecuted stdLegalEnterpriseExecuted = set2Iterator.next();
//                if(set3CaseNoList.contains(stdLegalEnterpriseExecuted.getCaseCode())) {
//                    stdLegalEnterpriseExecutedService.delete(stdLegalEnterpriseExecuted);
//                    set2Iterator.remove();
//                }
//            }
//        }
//
//
//        //保留各表内distinct（非空【案号】）& 最高（【@修订案件风险等级】）的案件记录
//        Map<String, List<StdLegalDataStructured>> set1Map = set1.stream().collect(Collectors.groupingBy(StdLegalDataStructured::getPtype));
//        Integer maxSet1Ptype = set1Map.keySet().stream().map(Integer::valueOf).max(Comparator.naturalOrder()).get();
//        List<StdLegalDataStructured> maxSet1 = set1Map.get(String.valueOf(maxSet1Ptype));
//        set1.removeAll(maxSet1);
//        if(!CollectionUtils.isEmpty(set1)){
//            for(StdLegalDataStructured stdLegalDataStructured : set1){
//                stdLegalDataStructuredService.delete(stdLegalDataStructured);
//            }
//        }
//        set1 = maxSet1;
//
//
//        Map<String, List<StdLegalEnterpriseExecuted>> set2Map = set2.stream().collect(Collectors.groupingBy(StdLegalEnterpriseExecuted::getPtype));
//        Integer maxSet1Ptype = map.keySet().stream().map(Integer::valueOf).max(Comparator.naturalOrder()).get();
//        List<StdLegalDataStructured> maxSet1 = map.get(String.valueOf(maxSet1Ptype));
//        set1.removeAll(maxSet1);
//        if(!CollectionUtils.isEmpty(set1)){
//            for(StdLegalDataStructured stdLegalDataStructured : set1){
//                stdLegalDataStructuredService.delete(stdLegalDataStructured);
//            }
//        }
//        set1 = maxSet1;

    }

}
