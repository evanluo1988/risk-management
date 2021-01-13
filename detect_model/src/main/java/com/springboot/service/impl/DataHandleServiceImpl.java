package com.springboot.service.impl;

import com.google.common.collect.Lists;
import com.springboot.domain.*;
import com.springboot.enums.DicTypeEnum;
import com.springboot.enums.OrgEnum;
import com.springboot.model.QuotaModel;
import com.springboot.model.StdGsEntInfoModel;
import com.springboot.service.*;
import com.springboot.utils.Utils;
import com.springboot.utils.DetectCacheUtils;
import com.springboot.vo.risk.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataHandleServiceImpl implements DataHandleService {
    @Autowired
    private IndustrialJusticeService industrialJusticeService;
    @Autowired
    private IntellectualPropertyService intellectualPropertyService;
    @Autowired
    private StdLegalService stdLegalService;
    @Autowired
    private QuotaValueService quotaValueService;
    @Autowired
    private StdEntBasicService stdEntBasicService;

    @Autowired
    private StdDataService stdDataService;
    @Autowired
    private StdIaPartentService stdIaPartentService;
    @Autowired
    private StdIaBrandService stdIaBrandService;
    @Autowired
    private StdIaCopyrightService stdIaCopyrightService;


    @Override
    public String handelData(String entName, OrgEnum org) throws Exception {
        //工商司法
        String reqId = industrialJusticeService.handelData(entName);

        if(org == OrgEnum.SCIENCE_OFFICE) {
            //知识产权
            intellectualPropertyService.handelData(entName);
        }

        return reqId;
    }

    @Override
    public void culQuotas(String reqId, OrgEnum org) {
        /**
         * 司法预处理
         */
        stdLegalService.preStdSsDatas(reqId);
        industrialJusticeService.culQuotas(reqId,"QUOTA");
        industrialJusticeService.culQuotas(reqId, "MODEL");
        if(org == OrgEnum.SCIENCE_OFFICE) {
            intellectualPropertyService.culQuotas(reqId, "QUOTA");
            intellectualPropertyService.culQuotas(reqId, "MODEL");
        }
    }

    @Override
    public EntHealthReportVo getEntHealthReportVo(String reqId, OrgEnum org) {
        List<QuotaModel> quotaModelList = quotaValueService.getQuotaList(reqId);
        StdGsEntInfoModel stdGsEntInfo = stdDataService.getStdGsEntInfo(reqId);
        EntHealthReportVo entHealthReportVo = new EntHealthReportVo();
        //企业健康评价
        entHealthReportVo.setEntHealthAssessment(getEntHealthAssessment(reqId, quotaModelList, org));
        //企业健康详情
        entHealthReportVo.setEntHealthDetails(getEntHealthDetails(reqId, quotaModelList, stdGsEntInfo, org));
        return entHealthReportVo;
    }

    /**
     * 企业健康评价
     * @return
     */
    private EntHealthAssessmentVo getEntHealthAssessment(String reqId, List<QuotaModel> quotaModelList, OrgEnum org) {
        EntHealthAssessmentVo entHealthAssessment = new EntHealthAssessmentVo();
        entHealthAssessment.setEntHealthDetectionRadar(getEntHealthDetectionRadar(reqId, quotaModelList));
        entHealthAssessment.setEntHealthDialysis(getEntHealthDialysis(quotaModelList, org));
        return entHealthAssessment;
    }

    /**
     * 企业健康检测雷达
     * @return
     */
    private EntHealthDetectionRadarVo getEntHealthDetectionRadar(String reqId, List<QuotaModel> quotaModelList) {
        EntHealthDetectionRadarVo entHealthDetectionRadarVo = new EntHealthDetectionRadarVo();
        StdEntBasic stdEntBasic = stdEntBasicService.getStdEntBasicByReqId(reqId);
        entHealthDetectionRadarVo.setEntName(stdEntBasic.getEntName());
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
    private EntHealthDialysisVo getEntHealthDialysis(List<QuotaModel> quotaModelList, OrgEnum org) {
        EntHealthDialysisVo entHealthDialysis = new EntHealthDialysisVo();
        FiveDRaderVo fiveDRader = new FiveDRaderVo();
        List<FiveDRaderItemVo> fiveDRaderItemList = Lists.newArrayList();
        fiveDRader.setFiveDRaderItemList(fiveDRaderItemList);

        List<DialysisVo> businessStabilityList = Lists.newArrayList();
        List<DialysisVo> intellectualPropertyList = Lists.newArrayList();
        List<DialysisVo> businessRiskList = Lists.newArrayList();
        List<DialysisVo> legalRiskList = Lists.newArrayList();
        entHealthDialysis.setBusinessStabilityList(businessStabilityList);
        entHealthDialysis.setIntellectualPropertyList(intellectualPropertyList);
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
                case "ZS_PATENT_VAL":
                case "ZS_TRADEMARK_VAL":
                case "ZS_SOFTWARE_COPYRIGHT_VAL":
                    if(org == OrgEnum.SCIENCE_OFFICE){
                        //5维雷达透析
                        fiveDRaderItemList.add(createFiveDRaderItemVo(quotaModel));
                    }
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
                case "ZS_REPORT_STRENGTH":
                case "ZS_NUM_EFFECT_INVENTION_CUR":
                case "ZS_NUM_VALID_PATENTS_CUR":
                case "ZS_NUM_CITATIONS_PATENTS_CUR":
                case "ZS_NUM_EFFECT_DESIGN_PATENTS_CUR":
                case "ZS_NUM_EFFECT_TRADEMARKS_CUR":
                case "ZS_NUM_TRADEMARK_REG_CUR":
                case "ZS_NUM_INVALID_TRADEMARKS_CUR":
                case "ZS_NUM_TRADEMARK_CATEGORIES_CUR":
                case "ZS_SUC_RATE_TRADEMARK_REG":
                case "ZS_NUM_EFFECT_SOFTWORKS_CUR":
                case "ZS_NUM_SOFTWORK_YEARS":
                    //知识产权价值度透析
                    if(org == OrgEnum.SCIENCE_OFFICE){
                        intellectualPropertyList.add(createDialysisVo(quotaModel));
                    }
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
        culFiveDRader(fiveDRader, quotaModelList, org);

        return entHealthDialysis;
    }

    /**
     * 企业健康详情
     * @return
     */
    private EntHealthDetailsVo getEntHealthDetails(String reqId, List<QuotaModel> quotaModelList, StdGsEntInfoModel stdGsEntInfo, OrgEnum org) {
        EntHealthDetailsVo entHealthDetails = new EntHealthDetailsVo();
        entHealthDetails.setEntRegInformation(getEntRegInformation(quotaModelList, stdGsEntInfo));
        entHealthDetails.setEntAlterList(getEntAlter(stdGsEntInfo));
        if(org == OrgEnum.SCIENCE_OFFICE) {
            entHealthDetails.setPatentInformation(getPatentInformation(reqId, quotaModelList));
            entHealthDetails.setBrandInformation(getBrandInformation(reqId, quotaModelList));
            entHealthDetails.setCopyrightInformation(getCopyrightInformation(reqId, quotaModelList));
        }
        entHealthDetails.setEntAbnormalDetails(getEntAbnormalDetails(stdGsEntInfo));
        //涉诉案件列表
        entHealthDetails.setLitigaCaseList(stdLegalService.getLitigaCase(reqId));
        return entHealthDetails;
    }

    /**
     * 软件著作权信息
     * @param reqId
     * @param quotaModelList
     * @return
     */
    public CopyrightInformationVo getCopyrightInformation(String reqId, List<QuotaModel> quotaModelList) {
        CopyrightInformationVo copyrightInformationVo = new CopyrightInformationVo();
        //著作权概况
        for(QuotaModel quotaModel : quotaModelList) {
            switch(quotaModel.getQuotaCode().trim()){
                case "ZS_SOFTWARE_APPLICATION_NUM":
                    copyrightInformationVo.setSoftwareApplicationNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_LAST_SOFTWARE_APPLICATION_PUB_YEAR":
                    copyrightInformationVo.setLastSoftwareApplicationPubYear(quotaModel.getQuotaValue());
                    break;
                case "ZS_LAST_SOFTWARE_APPLICATION_REG_YEAR":
                    copyrightInformationVo.setLastSoftwareApplicationRegYear(quotaModel.getQuotaValue());
                    break;
            }
        }
        //软件著作权明细
        List<StdIaCopyrightVo> stdIaCopyrightVoList = Lists.newArrayList();
        copyrightInformationVo.setStdIaCopyrightList(stdIaCopyrightVoList);
        List<StdIaCopyright> stdIaCopyrightList = stdIaCopyrightService.findByReqId(reqId);
        for(StdIaCopyright stdIaCopyright : Utils.getList(stdIaCopyrightList)) {
            StdIaCopyrightVo stdIaCopyrightVo = new StdIaCopyrightVo();
            BeanUtils.copyProperties(stdIaCopyright, stdIaCopyrightVo);
            stdIaCopyrightVoList.add(stdIaCopyrightVo);
        }

        return copyrightInformationVo;
    }


    /**
     * 商标信息
     * @param reqId
     * @param quotaModelList
     * @return
     */
    private BrandInformationVo getBrandInformation(String reqId, List<QuotaModel> quotaModelList) {
        BrandInformationVo brandInformationVo = new BrandInformationVo();
        //商标概况
        for(QuotaModel quotaModel : quotaModelList) {
            switch(quotaModel.getQuotaCode().trim()){
//                case "ZS_INVALID_BRAND_NUM":
//                    brandInformationVo.setInvalidBrandNum(quotaModel.getQuotaValue());
//                    break;
//                case "ZS_INVALID_BRAND_SPECIES_DISTRIBUTION":
//                    brandInformationVo.setInvalidBrandSpeciesDistribution(quotaModel.getQuotaValue());
//                    break;
                case "ZS_VALID_BRAND_NUM":
                    brandInformationVo.setValidBrandNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_VALID_BRAND_SPECIES_DISTRIBUTION":
                    brandInformationVo.setValidBrandSpeciesDistribution(quotaModel.getQuotaValue());
                    break;
            }
        }

        List<DicTable> dicTableList = DetectCacheUtils.getDicTableListByType(DicTypeEnum.NICECLASSIFY.name());
        //无效商标种类数量
//        List<BrandVarietyVo> invalidBrandVarietyList = stdIaBrandService.getBrandVariety(reqId, false);
//        for(BrandVarietyVo brandVarietyVo : invalidBrandVarietyList) {
//            DicTable dicTable = dicTableList.stream().filter(item -> item.getDicValue().equals(brandVarietyVo.getNiceClassify())).findFirst().get();
//            brandVarietyVo.setNiceClassifyName(dicTable.getDicName());
//        }
//        brandInformationVo.setInvalidBrandVarietyList(invalidBrandVarietyList);

        //有效商标种类数量
        List<BrandVarietyVo> validBrandVarietyList = stdIaBrandService.getBrandVariety(reqId, true);
        for(BrandVarietyVo brandVarietyVo : validBrandVarietyList) {
            DicTable dicTable = dicTableList.stream().filter(item -> item.getDicValue().equals(brandVarietyVo.getNiceClassify())).findFirst().get();
            brandVarietyVo.setNiceClassifyName(dicTable.getDicName());
        }
        brandInformationVo.setValidBrandVarietyList(validBrandVarietyList);
        //商标明细
        List<StdIaBrandVo> stdIaBrandVoList = Lists.newArrayList();
        brandInformationVo.setStdIaBrandList(stdIaBrandVoList);
        List<StdIaBrand> stdIaBrandList = stdIaBrandService.findByReqId(reqId);
        for(StdIaBrand stdIaBrand : Utils.getList(stdIaBrandList)) {
            StdIaBrandVo stdIaBrandVo = new StdIaBrandVo();
            final String type = DicTypeEnum.NICECLASSIFY.name();
            DicTable dicTableByTypeAndDicValue = DetectCacheUtils.getDicTableByTypeAndDicValue(type, stdIaBrand.getNiceClassify());
            stdIaBrandVo.setNiceClassifyName(Optional.ofNullable(dicTableByTypeAndDicValue).orElse(new DicTable()).getDicName());
            BeanUtils.copyProperties(stdIaBrand, stdIaBrandVo);
            stdIaBrandVoList.add(stdIaBrandVo);
        }
        return brandInformationVo;
    }


    /**
     * 专利信息
     * @param reqId
     * @param quotaModelList
     * @return
     */
    private PatentInformationVo getPatentInformation(String reqId, List<QuotaModel> quotaModelList) {
        PatentInformationVo patentInformation = new PatentInformationVo();
        //专利概况
        for(QuotaModel quotaModel : quotaModelList) {
            switch(quotaModel.getQuotaCode().trim()){
                case "ZS_INVALID_PATENT_NUM":
                    patentInformation.setInvalidPatentNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_INVALID_INVENTION_PATENT_NUM":
                    patentInformation.setInvalidInventionPatentNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_INVALID_NEW_PATENT_NUM":
                    patentInformation.setInvalidNewPatentNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_INVALID_APPEARANCE_PATENT_NUM":
                    patentInformation.setInvalidAppearancePatentNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_ONTRIAL_PATENT_NUM":
                    patentInformation.setOntrialPatentNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_ONTRIAL_INVENTION_PATENT_NUM":
                    patentInformation.setOntrialInventionPatentNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_ONTRIAL_NEW_PATENT_NUM":
                    patentInformation.setOntrialNewPatentNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_ONTRIAL_APPEARANCE_PATENT_NUM":
                    patentInformation.setOntrialAppearancePatentNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_VALID_PATENT_NUM":
                    patentInformation.setValidPatentNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_VALID_INVENTION_PATENT_NUM":
                    patentInformation.setValidInventionPatentNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_VALID_NEW_PATENT_NUM":
                    patentInformation.setValidNewPatentNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_VALID_APPEARANCE_PATENT_NUM":
                    patentInformation.setValidAppearancePatentNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_TRANSFER_PATENT_NUM":
                    patentInformation.setTransferPatentNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_INVENTOR_NUM":
                    patentInformation.setInventorNum(quotaModel.getQuotaValue());
                    break;
                case "ZS_INDEPENDENT_DEVELOPMENT_PATENT_NUM":
                    patentInformation.setIndependentDevelopmentPatentNum(quotaModel.getQuotaValue());
                    break;
            }
        }

        //专利明细
        List<StdIaPartentVo> stdIaPartentVoList = Lists.newArrayList();
        patentInformation.setStdIaPartentList(stdIaPartentVoList);
        List<StdIaPartent> stdIaPartentList = stdIaPartentService.findByReqId(reqId);
        for(StdIaPartent stdIaPartent : Utils.getList(stdIaPartentList)) {
            StdIaPartentVo stdIaPartentVo = new StdIaPartentVo();
            BeanUtils.copyProperties(stdIaPartent, stdIaPartentVo);
            stdIaPartentVoList.add(stdIaPartentVo);
        }
        return patentInformation;
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
    private void culFiveDRader(FiveDRaderVo fiveDRader, List<QuotaModel> quotaModelList, OrgEnum org) {
        List<Long> firstLevelIds = Lists.newArrayList(10L,12L,13L);
        if(org == OrgEnum.SCIENCE_OFFICE) {
            firstLevelIds.add(11L);
        }

        Map<Long, List<QuotaModel>> quotaModelMap = quotaModelList.stream()
                .filter(item -> (firstLevelIds.contains(item.getFirstLevelId()) && "QUOTA".equals(item.getQuotaType())))
                .collect(Collectors.groupingBy(QuotaModel::getFirstLevelId));
        for(Long key : firstLevelIds) {
            double score = 100;
            double intellectualPropertyScore = 0;
            for(QuotaModel quotaModel : Utils.getList(quotaModelMap.get(key))) {
                //对于知识产权价值度，用加分计算
                if(key == 11) {
                    intellectualPropertyScore = intellectualPropertyScore + quotaModel.getMinusPoints();
                } else {
                    score = score - quotaModel.getMinusPoints();
                }
            }
            if(score < 0){
                score = 0;
            }
            if(key == 10) {
                fiveDRader.setBusinessStabilityScore(score);
            } else if (key == 11) {
                fiveDRader.setIntellectualPropertyScore(intellectualPropertyScore);
            } else if(key == 12) {
                fiveDRader.setBusinessRiskScore(score);
            } else if(key == 13) {
                fiveDRader.setLegalRiskScore(score);
            }
        }
    }

    private String getDimensionName(Long dimensionId) {
        QuotaDimension quotaDimension = DetectCacheUtils.getQuotaDimensionById(dimensionId);
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
