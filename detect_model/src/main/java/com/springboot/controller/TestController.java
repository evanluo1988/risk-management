package com.springboot.controller;

import com.google.common.collect.Maps;
import com.springboot.domain.Quota;
import com.springboot.domain.QuotaGrand;
import com.springboot.domain.QuotaValue;
import com.springboot.enums.OrgEnum;
import com.springboot.executor.QuotaTask;
import com.springboot.mapper.QuotaMapper;
import com.springboot.mapper.QuotaValueMapper;
import com.springboot.model.QuotaModel;
import com.springboot.ret.ReturnT;
import com.springboot.service.DataHandleService;
import com.springboot.service.IndustrialJusticeService;
import com.springboot.service.QuotaValueService;
import com.springboot.service.StdLegalService;
import com.springboot.utils.ReturnTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/13 16:45
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private DataHandleService dataHandleService;
    @Autowired
    private IndustrialJusticeService industrialJusticeService;
    @Autowired
    private StdLegalService stdLegalService;
    @Autowired
    private QuotaValueService quotaValueService;
    @Autowired
    private QuotaMapper quotaMapper;
    @Autowired
    private QuotaValueMapper quotaValueMapper;


    /**
     * 生成原始表，标准表 & 司法解析 数据
     * @param entName
     * @return
     * @throws Exception
     */
    @GetMapping("/generatorOriginTableAndStdTableData")
    public ReturnT generatorOriginTableAndStdTableData(@RequestParam("entName") String entName) throws Exception {
        String reqId = dataHandleService.handelData(entName, OrgEnum.SCIENCE_OFFICE);

        HashMap<String, String> retBody = Maps.newHashMapWithExpectedSize(1);
        retBody.put("reqId",reqId);
        return ReturnTUtils.getReturnT(retBody);
    }

    /**
     * 司法解析
     * @param reqId
     * @return
     * @throws Exception
     */
    @GetMapping("/analysisJustice")
    public ReturnT analysisJustice(@RequestParam("reqId") String reqId) throws Exception {
        industrialJusticeService.analysisJustice(reqId);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 司法预处理
     * @param reqId
     * @return
     */
    @GetMapping("/preStdSsDatas")
    public ReturnT preStdSsDatas(@RequestParam("reqId") String reqId){
        stdLegalService.preStdSsDatas(reqId);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 计算指标
     * @param reqId
     * @return
     */
    @GetMapping("/culQuotasForTest")
    public ReturnT culQuotasForTest(@RequestParam("reqId") String reqId){
        dataHandleService.culQuotasForTest(reqId);
        //dataHandleService.culQuotas(reqId, OrgEnum.SCIENCE_OFFICE);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 计算指标
     * @param reqIds
     * @return
     */
    @PostMapping("/batchCulQuotasForTest")
    public ReturnT batchCulQuotasForTest(@RequestBody Set<String> reqIds){
        for (String reqId : reqIds) {
            dataHandleService.culQuotasForTest(reqId);
        }
        //dataHandleService.culQuotas(reqId, OrgEnum.SCIENCE_OFFICE);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 模型计算
     * @param reqId
     * @return
     */
    @GetMapping("/culModelsForTest")
    public ReturnT culModelsForTest(@RequestParam("reqId") String reqId){
        dataHandleService.culModelsForTest(reqId);
        //dataHandleService.culQuotas(reqId, OrgEnum.SCIENCE_OFFICE);
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 批量模型计算
     * @param reqIds
     * @return
     */
    @PostMapping("/batchCulModelsForTest")
    public ReturnT batchCulModelsForTest(@RequestBody Set<String> reqIds){
        for (String reqId : reqIds) {
            dataHandleService.culModelsForTest(reqId);
        }
        return ReturnTUtils.newCorrectReturnT();
    }

    @PostMapping("/batchCulQuotaGrandForTest")
    public ReturnT batchCulQuotaGrandForTest(@RequestBody Set<String> reqIds) {
        for(String reqId : reqIds) {
            List<QuotaModel> quotaModelList = quotaValueService.getQuotaList(reqId);
            List<QuotaModel> quotaModelList1 = quotaModelList.stream().filter(item -> item.getQuotaType().equals("QUOTA")).collect(Collectors.toList());
            for(QuotaModel quotaModel : quotaModelList1) {
                Quota quota = quotaMapper.selectById(quotaModel.getId());
                QuotaTask quotaTask = new QuotaTask(reqId, quota);
                if(quota.getGrandCode() != null){
                    QuotaGrand quotaGrand = quotaTask.getQuotaGrand(quotaModel.getQuotaValue());
                    QuotaValue quotaValue = new QuotaValue();
                    BeanUtils.copyProperties(quotaModel, quotaValue, "id");
                    quotaValue.setId(quotaModel.getQuotaValueId());
                    quotaValue.setQuotaId(quota.getId());
                    quotaValue.setMinusPoints(quotaGrand.getMinusPoints());
                    quotaValue.setIdealInterval(quotaGrand.getIdealInterval());
                    quotaValueMapper.updateById(quotaValue);
                }
            }
        }
        return ReturnTUtils.newCorrectReturnT();
    }

    /**
     * 分档计算
     * @param reqId
     * @return
     */
    @GetMapping("/culQuotaGrandForTest")
    public ReturnT culQuotaGrandForTest(@RequestParam("reqId") String reqId){
        List<QuotaModel> quotaModelList = quotaValueService.getQuotaList(reqId);
        List<QuotaModel> quotaModelList1 = quotaModelList.stream().filter(item -> item.getQuotaType().equals("QUOTA")).collect(Collectors.toList());
        for(QuotaModel quotaModel : quotaModelList1) {
            Quota quota = quotaMapper.selectById(quotaModel.getId());
            QuotaTask quotaTask = new QuotaTask(reqId, quota);
            if(quota.getGrandCode() != null){
                QuotaGrand quotaGrand = quotaTask.getQuotaGrand(quotaModel.getQuotaValue());
                QuotaValue quotaValue = new QuotaValue();
                BeanUtils.copyProperties(quotaModel, quotaValue,"id");
                quotaValue.setId(quotaModel.getQuotaValueId());
                quotaValue.setQuotaId(quota.getId());
                quotaValue.setMinusPoints(quotaGrand.getMinusPoints());
                quotaValue.setIdealInterval(quotaGrand.getIdealInterval());
                quotaValueMapper.updateById(quotaValue);
            }
        }
        return ReturnTUtils.newCorrectReturnT();
    }
}
