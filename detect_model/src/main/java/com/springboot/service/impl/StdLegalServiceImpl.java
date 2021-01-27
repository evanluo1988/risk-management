package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.springboot.domain.*;
import com.springboot.enums.DicTypeEnum;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.StdLegalCasemedianMapper;
import com.springboot.mapper.StdLegalDataStructuredMapper;
import com.springboot.service.*;
import com.springboot.utils.ConvertUtils;
import com.springboot.utils.DateUtils;
import com.springboot.utils.StrUtils;
import com.springboot.utils.Utils;
import com.springboot.utils.CalculatUtil;
import com.springboot.utils.DetectCacheUtils;
import com.springboot.vo.risk.LitigaCaseVo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StdLegalServiceImpl implements StdLegalService {

    @Autowired
    private StdLegalDataStructuredService stdLegalDataStructuredService;
    @Autowired
    private StdLegalDataStructuredMapper stdLegalDataStructuredMapper;
    @Autowired
    private StdLegalEnterpriseExecutedService stdLegalEnterpriseExecutedService;
    @Autowired
    private StdLegalEntUnexecutedService stdLegalEntUnexecutedService;
    @Autowired
    private StdLegalCasemedianMapper stdLegalCasemedianMapper;
    @Autowired
    private StdLegalCasemedianServiceImpl stdLegalCasemedianService;
    @Autowired
    private StdLegalCasemedianTempService stdLegalCasemedianTempService;
    @Autowired
    private StdLegalDataStructuredTempService stdLegalDataStructuredTempService;
    @Autowired
    private StdLegalEnterpriseExecutedTempService stdLegalEnterpriseExecutedTempService;
    @Autowired
    private StdLegalEntUnexecutedTempService stdLegalEntUnexecutedTempService;


    //案件风险等级判断
    public static final String CASERISKLEVEL_N = "N";
    public static final String CASERISKLEVEL_L = "L";
    public static final String CASERISKLEVEL_M1 = "M1";
    public static final String CASERISKLEVEL_M2 = "M2";
    public static final String CASERISKLEVEL_M3 = "M3";
    public static final String CASERISKLEVEL_M4 = "M4";
    public static final String CASERISKLEVEL_M5 = "M5";
    public static final String CASERISKLEVEL_M6 = "M6";
    public static final String CASERISKLEVEL_H = "H";

    /**
     * H>M6>M5>M4>M3>M2>M1>L
     **/
    public static final List<String> CASE_RISK_LEVEL_STAGE = Lists.newArrayList(CASERISKLEVEL_L, CASERISKLEVEL_M1, CASERISKLEVEL_M2, CASERISKLEVEL_M3, CASERISKLEVEL_M4, CASERISKLEVEL_M5, CASERISKLEVEL_M6, CASERISKLEVEL_H);

    public static final List<String> P_TYPES = Lists.newArrayList("14", "15", "16");

    @Override
    public void createStdLegalMidTable(String reqId) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("reqID", reqId);

        String regexCaseReason = ".*(小额借款|生命权|建设用地|房地产开发|商品房买卖|典当|船舶买卖|不正当竞争|出资人权益|公司盈余|股东损害公司|民间借贷|房屋买卖).*";
        String regexPDesc1 = ".*(生产场所|生产场地|生产用地|经营场所|经营场地|经营用地).*";
        String regexPDesc2 = ".*?((已经?申请仲裁|环保|环境保护|环评报告|环境评价影响)|((厂房|生产场所).{0,10}(违建|违章|违规))).*?";
        String regexPDesc3 = ".*(民间|不正当竞争|建筑|工程|专利|借贷|金融|借款).*";
        String regexPDesc4 = ".*(物业|劳动|劳务|机动车|人事).*";
        String[] pTypes1 = new String[]{"15", "16", "14", "17", "18", "22", "9", "19", "23", "20", "12"};
        String[] pTypes2 = new String[]{"19", "23"};
        String[] pTypes3 = new String[]{"15", "16"};
        String[] pTypes4 = new String[]{"9", "20"};
        String[] pTypes5 = new String[]{"12", "18", "22"};
        String[] pDescs = new String[]{"已撤诉", "已撤销", "撤销开庭"};
        String[] sentences = new String[]{"01", "02", "08", "09", "10", "11", "12", "13", "14", "23"};
        String[] addSentenceEffects = new String[]{"01", "02", "19", "23", "25"};

        Set<String> caseNo1Set = new HashSet<>();// 获取 pType为 14的案号
        Set<String> caseNo2Set = new HashSet<>();// 获取 pType为 15的案号
        Set<String> caseNo3Set = new HashSet<>();// 获取 pType =16 的案号
        String pType = null;// 公告类型

        List<StdLegalCasemedian> stdLegalCasemedianList = Lists.newArrayList();
        // 14-立案公告 15-开庭公告 16-裁判文书


        List<StdLegalDataStructured> stdLegalDataStructuredList = stdLegalDataStructuredService.findStdLegalDataStructuredByReqId(reqId);
        if (CollectionUtils.isEmpty(stdLegalDataStructuredList)) {
            return;
        }

        for (StdLegalDataStructured legalData : stdLegalDataStructuredList) {
            pType = legalData.getPtype();
            if ("14".equals(pType)) {
                caseNo1Set.add(legalData.getCaseNo());
            }
            if ("15".equals(pType)) {
                caseNo2Set.add(legalData.getCaseNo());
            }
            if ("16".equals(pType)) {
                caseNo3Set.add(legalData.getCaseNo());
            }
        }

        Map<String, Object> caseMap;
        for (StdLegalDataStructured stdLegalDataStructured : stdLegalDataStructuredList) {
            StdLegalCasemedian stdLegalCasemedian = new StdLegalCasemedian();

            BigDecimal referMoney = null;// 以人民币为单位的涉案金额
            String caseReason = stdLegalDataStructured.getCaseReason();// 案由
            String serialNo = stdLegalDataStructured.getSerialno(); // 序列号
            String pDesc = stdLegalDataStructured.getPdesc();// 公告内容
            String caseLevel = "";// 案件重要性
            Double intervalYear = null;//间隔年数
            String casereasonLevel = "";// 案由等级
            String caseClass = null;// 案由分类
            String payment = null;// 涉案金额
            String caseResult = "";// 结案状态
            String sentenceBrief = "";// 审理结果
            String phase = null; // 审理程序
            String courtLevel = ""; // 法院等级
            String caseImpact = null; // 案件影响力
            String sentenceEffect = null;// 案件结果对客户的影响
            String caseriskLevel = null;// 案件风险等级

            pType = stdLegalDataStructured.getPtype();// 公告类型


            // 6.诉讼地位 lawStatus
            //TODO 各项目根据情况选择修改  如果有这个字段直接使用,如果没就根据企业名称进行判断 根据数据分析
            String lawStatus = stdLegalDataStructured.getLawstatus();

            //2.案件重要性  caseLevel
            boolean caseImportanceResult = CalculatUtil.hasChar(caseReason)
                    ? Pattern.compile(regexPDesc3).matcher(caseReason).matches() : false;
            boolean caseImportanceResult2 = CalculatUtil.hasChar(caseReason)
                    ? Pattern.compile(regexPDesc4).matcher(caseReason).matches() : false;
            if (caseImportanceResult) {
                caseLevel = "重要案件";
            } else if (caseImportanceResult2) {
                caseLevel = "不重要案件";
            } else {
                caseLevel = "一般案件";
            }

            // 1.案由等级(重要案由，一般案由，无法判断)
            if (CalculatUtil.equals(pTypes3, pType)) {
                paraMap.put("serialno", serialNo);
                paraMap.put("varname", "caseClass");
                caseMap = stdLegalDataStructuredMapper.getvarname(paraMap);
                if (!CollectionUtils.isEmpty(caseMap) && CalculatUtil.hasChar(caseMap.get("legalvalue"))) {
                    caseClass = caseMap.get("legalvalue").toString();
                }
                boolean regexCaseReasonResult = CalculatUtil.hasChar(caseReason)
                        ? Pattern.compile(regexCaseReason).matcher(caseReason).matches() : false;
                boolean regexPDescResult = CalculatUtil.hasChar(pDesc) ? Pattern.compile(regexPDesc1).matcher(pDesc).matches()
                        : false;
                if (CalculatUtil.hasChar(caseReason) //
                        && ((!CalculatUtil.inStr("农村房屋买卖", caseReason) && regexCaseReasonResult)//
                        || (caseReason.contains("房屋租赁合同") && regexPDescResult) //
                        || "07".equals(caseClass) //
                        || "4".equals(stdLegalDataStructured.getDocuClass()))) {
                    casereasonLevel = "重要案由";
                } else if (!CalculatUtil.hasChar(caseReason)
                        || (CalculatUtil.inStr("房屋租赁合同", caseReason) && !CalculatUtil.hasChar(pDesc))) {
                    casereasonLevel = "无法判断";
                } else {
                    casereasonLevel = "一般案由";
                }
            }

            // 3,是否结案：(已结案，已撤诉，无法判断)
            if ("14".equals(pType) || "15".equals(pType)) {
                if (CalculatUtil.hasChar(pDesc) && CalculatUtil.contains(pDescs, pDesc)) {
                    caseResult = "已撤诉";
                } else {
                    caseResult = "无法判断";
                }
            }

            boolean flag = true;
            if ("16".equals(pType)) {
                // 审理结果
                paraMap.put("serialno", serialNo);
                paraMap.put("varname", "sentenceBrief");
                caseMap = stdLegalDataStructuredMapper.getvarname(paraMap);
                if (!CollectionUtils.isEmpty(caseMap) && CalculatUtil.hasChar(caseMap.get("legalvalue"))) {
                    sentenceBrief = caseMap.get("legalvalue").toString();
                    String[] split = sentenceBrief.split("\\、");
                    for (String res : split) {
                        if (!CalculatUtil.equals(sentences, res)) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(caseMap) && CalculatUtil.hasChar(caseMap.get("legalvalue")) && flag) {
                    caseResult = "已结案";
                } else if ("03".equals(sentenceBrief)) {
                    caseResult = "已撤诉";
                } else {
                    caseResult = "无法判断";
                }
            }

            // 4,间隔年数 intervalYear
            Date caseDate = DateUtils.localDateToDate(stdLegalDataStructured.getCaseDate()); // 审判时间/开庭时间/立案时间
            Date publishDate = DateUtils.localDateToDate(stdLegalDataStructured.getPdate()); // 发布时间

            //和刘林确认，用当前时间减，因为获取不到用户申请网贷时间
            // TODO: 2021/1/27 测试要写成固定值 DateUtils.convertDateStr(new Date());
            String createTime = "2020-03-01";
            // 用公共方法计算日期
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (caseDate != null && createTime != null) {
                    intervalYear = CalculatUtil.DoubleConversion(CalculatUtil.getDaySub(sdf.parse(sdf.format(caseDate)), createTime) / (double) 365);
                } else if (publishDate != null && createTime != null) {
                    intervalYear = CalculatUtil.DoubleConversion(CalculatUtil.getDaySub(sdf.parse(sdf.format(publishDate)), createTime) / (double) 365);
                }
            } catch (Exception e) {
                throw new ServiceException("用公共方法计算日期" + e);
            }
            //【间隔年数】(intervalYear)＜0，则赋值【间隔年数】(intervalYear)为0
            if (intervalYear != null && intervalYear < 0) {
                intervalYear = 0.0;
            }
            // 法院等级
            paraMap.put("serialno", serialNo);
            paraMap.put("varname", "courtLevel");
            caseMap = stdLegalDataStructuredMapper.getvarname(paraMap);
            if (!CollectionUtils.isEmpty(caseMap) && CalculatUtil.hasChar(caseMap.get("legalvalue"))) {
                courtLevel = caseMap.get("legalvalue").toString();
            }

            //案件影响力 caseImpact
            paraMap.put("serialno", serialNo);
            paraMap.put("varname", "caseImpact");
            caseMap = stdLegalDataStructuredMapper.getvarname(paraMap);
            if (!CollectionUtils.isEmpty(caseMap) && CalculatUtil.hasChar(caseMap.get("legalvalue"))) {
                caseImpact = caseMap.get("legalvalue").toString();
            }

            //5 涉案金额比例  paymentScale
            //TODO 【涉案金额比例】（paymentScale）=【涉案金额】（payment）/申请企业近12个月增值税销售收入  具体根据各个项目确定

            paraMap.put("serialno", serialNo);
            paraMap.put("varname", "payment");
            Map<String, Object> mappayment = stdLegalDataStructuredMapper.getvarname(paraMap);
            if (mappayment != null) {
                payment = (String) mappayment.get("legalvalue");
            }
            if (CalculatUtil.hasChar(payment)) {
                referMoney = new BigDecimal(payment);
            }

            // 案件结果对客户的影响
            paraMap.put("serialno", serialNo);
            paraMap.put("varname", "sentenceEffect");
            caseMap = stdLegalDataStructuredMapper.getvarname(paraMap);
            if (!CollectionUtils.isEmpty(caseMap) && CalculatUtil.hasChar(caseMap.get("legalvalue"))) {
                sentenceEffect = caseMap.get("legalvalue").toString();
            }

            // 审理程序
            paraMap.put("serialno", serialNo);
            paraMap.put("varname", "phase");
            caseMap = stdLegalDataStructuredMapper.getvarname(paraMap);
            if (!CollectionUtils.isEmpty(caseMap) && CalculatUtil.hasChar(caseMap.get("legalvalue"))) {
                phase = caseMap.get("legalvalue").toString();
            }

            //整体规则
            if (CalculatUtil.inStr("无关第三人", lawStatus) || CalculatUtil.inStr("无关第三方", lawStatus)) {
                caseriskLevel = CASERISKLEVEL_L;
            } else if (CalculatUtil.inStr("刑", stdLegalDataStructured.getCaseNo())) {
                if (intervalYear != null && intervalYear < 3 && intervalYear >= 0) {
                    caseriskLevel = CASERISKLEVEL_H;
                } else if (intervalYear != null && intervalYear >= 3) {
                    caseriskLevel = CASERISKLEVEL_L;
                } else {
                    caseriskLevel = CASERISKLEVEL_M6;
                }
            } else {

                // （1）Ptype = 19/23 （破产/清算）
                if (CalculatUtil.equals(pTypes2, pType)) {
                    caseriskLevel = getCaseriskLevelByPtype19or23(caseriskLevel, lawStatus, intervalYear);
                }

                // （2） Ptype = 9/20（拍卖公告/票据催告）
                if (CalculatUtil.equals(pTypes4, pType)) {
                    caseriskLevel = CASERISKLEVEL_L;
                }

                // （3） Pytpe = 12/18/22（其他公告/送达公告/仲裁公告）
                if (CalculatUtil.equals(pTypes5, pType)) {
                    caseriskLevel = getCaseriskLevelByPtype12or18or22(caseriskLevel, intervalYear);
                }

                // （6） Ptype = 17（执行公告）
                if ("17".equals(pType)) {
                    caseriskLevel = getCaseriskLevelByPtype17(caseriskLevel, lawStatus, intervalYear);
                }
                // （4） Pytpe = 14（立案公告）
                if ("14".equals(pType)) {
                    caseriskLevel = getCaseriskLevelByPtype14(caseriskLevel, stdLegalDataStructured, intervalYear,
                            caseResult, caseLevel, caseNo2Set, caseNo3Set);
                }

                // Pytpe = 15（开庭公告）
                if ("15".equals(pType)) {
                    caseriskLevel = getCaseriskLevelByPtype15(caseriskLevel, stdLegalDataStructured, intervalYear,
                            caseResult, caseLevel, caseNo3Set);
                }

                // Pytpe = 16（裁判文书）或空
                //todo referMoney and paymentScale can not get
                if ("16".equals(pType) || StringUtils.isEmpty(pType)) {
                    caseriskLevel = getCaseriskLevelByPtype16orNull(caseriskLevel, stdLegalDataStructured, intervalYear,
                            caseResult, caseLevel, lawStatus, pDesc, sentenceBrief,
                            phase, addSentenceEffects, sentenceEffect, referMoney);
                }
            }

            stdLegalCasemedian.setCaseClass(caseClass);
            stdLegalCasemedian.setCaseImpact(caseImpact);
            stdLegalCasemedian.setCaseLevel(caseLevel);
            stdLegalCasemedian.setCaseReasonLevel(casereasonLevel);
            stdLegalCasemedian.setCaseResult(caseResult);
            stdLegalCasemedian.setCaseRiskLevel(caseriskLevel);
            stdLegalCasemedian.setCourtLevel(courtLevel);
            stdLegalCasemedian.setIntervalYear(intervalYear == null ? null : intervalYear.toString());
            stdLegalCasemedian.setLawStatus(lawStatus);
            stdLegalCasemedian.setPayment(payment);
            //stdLegalCasemedian.setPaymentScale(CollectionUtils.isEmpty(paymentScale) ? null : paymentScale.toString());
            stdLegalCasemedian.setPhase(phase);
            stdLegalCasemedian.setSentenceBrief(sentenceBrief);
            stdLegalCasemedian.setSentenceEffect(sentenceEffect);
            stdLegalCasemedian.setReqId(reqId);
            stdLegalCasemedian.setSerialNo(serialNo);

            stdLegalCasemedianList.add(stdLegalCasemedian);
        }
        stdLegalCasemedianService.saveBatch(stdLegalCasemedianList);
    }

    /**
     * Ptype = 19/23 （破产/清算）
     *
     * @param caseriskLevel
     * @param lawStatus
     * @param intervalYear
     * @return
     */
    private String getCaseriskLevelByPtype19or23(String caseriskLevel, String lawStatus, Double intervalYear) {
        if (StringUtils.isNotEmpty(lawStatus) && (lawStatus.contains("原告") || lawStatus.contains("申请人"))) {
            if (intervalYear == null || (intervalYear >= 0 && intervalYear < 0.5)) {
                caseriskLevel = CASERISKLEVEL_M2;
            } else if (intervalYear >= 0.5 && intervalYear < 1) {
                caseriskLevel = CASERISKLEVEL_L;
            } else if (intervalYear >= 1 && intervalYear < 2) {
                caseriskLevel = CASERISKLEVEL_L;
            } else {
                caseriskLevel = CASERISKLEVEL_L;
            }
        } else {
            if (intervalYear == null || (intervalYear >= 0 && intervalYear < 0.5)) {
                caseriskLevel = CASERISKLEVEL_M6;
            } else if (intervalYear >= 0.5 && intervalYear < 1) {
                caseriskLevel = CASERISKLEVEL_M5;
            } else if (intervalYear >= 1 && intervalYear < 2) {
                caseriskLevel = CASERISKLEVEL_M3;
            } else {
                caseriskLevel = CASERISKLEVEL_L;
            }
        }
        return caseriskLevel;
    }

    /**
     * （3） Pytpe = 12/18/22（其他公告/送达公告/仲裁公告）
     *
     * @param caseriskLevel
     * @param intervalYear
     * @return
     */
    private String getCaseriskLevelByPtype12or18or22(String caseriskLevel, Double intervalYear) {
        if (intervalYear != null && intervalYear >= 0 && intervalYear < 1) {
            caseriskLevel = CASERISKLEVEL_M4;
        } else if (intervalYear == null || (intervalYear >= 1 && intervalYear < 2)) {
            caseriskLevel = CASERISKLEVEL_M3;
        } else {
            caseriskLevel = CASERISKLEVEL_L;
        }
        return caseriskLevel;
    }

    /**
     * （4） Pytpe = 14（立案公告）
     *
     * @param caseriskLevel
     * @param legalData
     * @param intervalYear
     * @param caseResult
     * @param caseLevel
     * @param caseNo2Set
     * @param caseNo3Set
     * @return
     */
    private String getCaseriskLevelByPtype14(String caseriskLevel, StdLegalDataStructured legalData, Double intervalYear,
                                             String caseResult, String caseLevel, Set<String> caseNo2Set, Set<String> caseNo3Set) {
        if (caseNo2Set.contains(legalData.getCaseNo()) || caseNo3Set.contains(legalData.getCaseNo())) {
            caseriskLevel = CASERISKLEVEL_L;
        } else if (CalculatUtil.myEquals("已撤诉", caseResult)) {
            caseriskLevel = CASERISKLEVEL_L;
        } else if (CalculatUtil.myEquals("重要案件", caseLevel)) {
            if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                caseriskLevel = CASERISKLEVEL_M5;
            } else if (intervalYear == null || (intervalYear >= 0.25 && intervalYear < 0.5)) {
                caseriskLevel = CASERISKLEVEL_M4;
            } else if (intervalYear >= 0.5 && intervalYear < 1.5) {
                caseriskLevel = CASERISKLEVEL_M3;
            } else if (intervalYear >= 1.5) {
                caseriskLevel = CASERISKLEVEL_L;
            }
        } else if (CalculatUtil.myEquals("不重要案件", caseLevel) || CalculatUtil.myEquals("一般案件", caseLevel)) {
            if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                caseriskLevel = CASERISKLEVEL_M4;
            } else if (intervalYear == null || (intervalYear >= 0.25 && intervalYear < 0.5)) {
                caseriskLevel = CASERISKLEVEL_M4;
            } else if (intervalYear >= 0.5 && intervalYear < 1.5) {
                caseriskLevel = CASERISKLEVEL_M2;
            } else if (intervalYear >= 1.5) {
                caseriskLevel = CASERISKLEVEL_L;
            }
        }
        return caseriskLevel;
    }

    /**
     * （6） Ptype = 17（执行公告）
     *
     * @param caseriskLevel
     * @param lawStatus
     * @param intervalYear
     * @return
     */
    private String getCaseriskLevelByPtype17(String caseriskLevel, String lawStatus, Double intervalYear) {
        if ("执行人".equals(lawStatus) || "申请人".equals(lawStatus) || "原告".equals(lawStatus) || "申请执行人".equals(lawStatus)) {
            if (intervalYear != null && intervalYear >= 1) {
                caseriskLevel = CASERISKLEVEL_L;
            } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                caseriskLevel = CASERISKLEVEL_L;
            } else if (intervalYear >= 0 && intervalYear < 0.5) {
                caseriskLevel = CASERISKLEVEL_L;
            }
        } else {
            if (intervalYear != null && intervalYear >= 1) {
                caseriskLevel = CASERISKLEVEL_L;
            } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                caseriskLevel = CASERISKLEVEL_M3;
            } else if (intervalYear >= 0 && intervalYear < 0.5) {
                caseriskLevel = CASERISKLEVEL_M6;
            }
        }
        return caseriskLevel;
    }

    /**
     * Pytpe = 15（开庭公告）
     *
     * @param caseriskLevel
     * @param legalData
     * @param intervalYear
     * @param caseResult
     * @param caseLevel
     * @param caseNo3Set
     * @return
     */
    private String getCaseriskLevelByPtype15(String caseriskLevel, StdLegalDataStructured legalData, Double intervalYear,
                                             String caseResult, String caseLevel, Set<String> caseNo3Set) {
        if (caseNo3Set.contains(legalData.getCaseNo())) {
            caseriskLevel = CASERISKLEVEL_L;
        } else if (CalculatUtil.myEquals("已撤诉", caseResult)) {
            caseriskLevel = CASERISKLEVEL_L;
        } else if (CalculatUtil.myEquals("重要案件", caseLevel)) {
            if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                caseriskLevel = CASERISKLEVEL_M5;
            } else if (intervalYear == null || (intervalYear >= 0.25 && intervalYear < 0.5)) {
                caseriskLevel = CASERISKLEVEL_M4;
            } else if (intervalYear >= 0.5 && intervalYear < 1.5) {
                caseriskLevel = CASERISKLEVEL_M3;
            } else if (intervalYear >= 1.5) {
                caseriskLevel = CASERISKLEVEL_L;
            }
        } else {
            if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                caseriskLevel = CASERISKLEVEL_M4;
            } else if (intervalYear == null || (intervalYear >= 0.25 && intervalYear < 0.5)) {
                caseriskLevel = CASERISKLEVEL_M4;
            } else if (intervalYear >= 0.5 && intervalYear < 1.5) {
                caseriskLevel = CASERISKLEVEL_M2;
            } else if (intervalYear >= 1.5) {
                caseriskLevel = CASERISKLEVEL_L;
            }
        }
        return caseriskLevel;
    }

    /**
     * Pytpe = 16（裁判文书）或空
     *
     * @param caseriskLevel
     * @param legalData
     * @param intervalYear
     * @param caseResult
     * @param caseLevel
     * @param lawStatus
     * @param pDesc
     * @param sentenceBrief
     * @param phase
     * @param addSentenceEffects
     * @param sentenceEffect
     * @param referMoney
     * @return
     */
    private String getCaseriskLevelByPtype16orNull(String caseriskLevel, StdLegalDataStructured legalData, Double intervalYear,
                                                   String caseResult, String caseLevel, String lawStatus, String pDesc, String sentenceBrief,
                                                   String phase, String[] addSentenceEffects, String sentenceEffect, BigDecimal referMoney) {
        // ①一级判断规则
        if (CalculatUtil.myEquals("已撤诉", caseResult)) {
            caseriskLevel = CASERISKLEVEL_L;
        } else if (StringUtils.isEmpty(legalData.getJudgementResult()) && CalculatUtil.inStr("调解", pDesc)) {
            caseriskLevel = CASERISKLEVEL_L;
        } else if (CalculatUtil.contains(addSentenceEffects, sentenceBrief)) {
            caseriskLevel = CASERISKLEVEL_L;
        } else if (CalculatUtil.inStr("金融合同纠纷", legalData.getCaseReason())
                || CalculatUtil.inStr("借款合同纠纷", legalData.getCaseReason())) {
            // ② 二级判断规则
            caseriskLevel = getPtype16orNullSecondLevel(caseriskLevel, intervalYear, caseResult, phase, sentenceEffect, referMoney, lawStatus);
        } else {
            // ③ 三级判断规则
            caseriskLevel = getPtype16orNullThreeLevel(caseriskLevel, intervalYear, caseLevel, lawStatus,
                    sentenceEffect, referMoney);
        }
        return caseriskLevel;
    }

    /**
     * Pytpe = 16（裁判文书）或空中  二级判断规则
     *
     * @param caseriskLevel
     * @param intervalYear
     * @param caseResult
     * @param phase
     * @param sentenceEffect
     * @param referMoney
     * @return
     */
    private String getPtype16orNullSecondLevel(String caseriskLevel, Double intervalYear, String caseResult, String phase, String sentenceEffect, BigDecimal referMoney, String lawStatus) {

        if (CalculatUtil.myEquals("一审", phase) && CalculatUtil.myEquals("原告", lawStatus)
                && CalculatUtil.myEquals("0", sentenceEffect)) {
            if (referMoney != null && 0 <= referMoney.doubleValue() && referMoney.doubleValue() < 200000) {
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_L;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_L;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_L;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_L;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            } else {
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_M4;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_M3;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_M2;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_M1;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            }
        } else {
            if (referMoney != null && 0 <= referMoney.doubleValue() && referMoney.doubleValue() < 50000) {
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_M3;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_M2;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_M1;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_M1;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            } else if (referMoney == null || (50000 <= referMoney.doubleValue() && referMoney.doubleValue() < 200000)) {
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_M4;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_M3;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_M2;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_M1;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            } else if (200000 <= referMoney.doubleValue() && referMoney.doubleValue() < 500000) {
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_M6;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_M5;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_M4;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_M2;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            } else {
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_M6;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_M6;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_M5;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_M3;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            }
        }
        return caseriskLevel;
    }

    /**
     * Pytpe = 16（裁判文书）或空 中 三级判断规则
     *
     * @param caseriskLevel
     * @param intervalYear
     * @param caseLevel
     * @param lawStatus
     * @param sentenceEffect
     * @param referMoney
     * @return
     */
    private String getPtype16orNullThreeLevel(String caseriskLevel, Double intervalYear, String caseLevel, String lawStatus, String sentenceEffect, BigDecimal referMoney) {
        if (CalculatUtil.myEquals("0", sentenceEffect)) {
            if (referMoney != null && ((0 <= referMoney.doubleValue() && referMoney.doubleValue() < 500000))) {
                caseriskLevel = CASERISKLEVEL_L;
            } else {
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_M4;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_M3;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_M1;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_M1;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            }
        } else if (StringUtils.isEmpty(sentenceEffect) || CalculatUtil.myEquals("1", sentenceEffect) || CalculatUtil.myEquals("99", sentenceEffect)) {
            if (referMoney != null && 0 <= referMoney.doubleValue() && referMoney.doubleValue() < 300000) {
                //条件1
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_M1;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_M1;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_M1;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_M1;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            } else if (referMoney != null && referMoney.doubleValue() <= 500000) {
                //条件2
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_M4;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_M3;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_M2;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_M1;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            } else if (referMoney != null && referMoney.doubleValue() <= 800000) {
                //条件3
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_M5;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_M4;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_M3;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_M2;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            } else if (referMoney != null && referMoney.doubleValue() > 800000) {
                //条件4
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_M6;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_M6;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_M4;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_M3;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            } else if (CalculatUtil.myEquals("重要案件", caseLevel) && CalculatUtil.myEquals("被告", lawStatus)) {
                //条件5
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_M5;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_M4;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_M3;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_M2;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            } else if (CalculatUtil.myEquals("不重要案件", caseLevel)) {
                //条件7
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_M3;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_M3;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_M2;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_M1;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            } else {
                //条件6
                if (intervalYear != null && (intervalYear >= 0 && intervalYear < 0.25)) {
                    caseriskLevel = CASERISKLEVEL_M4;
                } else if (intervalYear != null && (intervalYear >= 0.25 && intervalYear < 0.5)) {
                    caseriskLevel = CASERISKLEVEL_M4;
                } else if (intervalYear == null || (intervalYear >= 0.5 && intervalYear < 1)) {
                    caseriskLevel = CASERISKLEVEL_M3;
                } else if (intervalYear >= 1 && intervalYear < 2) {
                    caseriskLevel = CASERISKLEVEL_M1;
                } else {
                    caseriskLevel = CASERISKLEVEL_L;
                }
            }
        }
        return caseriskLevel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void preStdSsDatas(String reqId) {
        List<StdLegalDataStructuredTemp> set1 = getS1(reqId);
        List<StdLegalEnterpriseExecutedTemp> set2 = getS2(reqId);
        List<StdLegalEntUnexecutedTemp> set3 = getS3(reqId);

        List<StdLegalDataStructuredTemp> copyS1 = Lists.newArrayList(set1);
        List<StdLegalEnterpriseExecutedTemp> copyS2 = Lists.newArrayList(set2);
        List<StdLegalEntUnexecutedTemp> copyS3 = Lists.newArrayList(set3);

        calcRiskLevel(reqId,copyS1,copyS2,copyS3);

        /**
         * 1.为空【案号】的案件记录，不做清洗。
         */
        List<StdLegalDataStructuredTemp> nullCaseNoInS1 = copyS1.stream().filter(stdLegalDataStructuredTemp -> Objects.isNull(stdLegalDataStructuredTemp.getCaseNo())).collect(Collectors.toList());
        List<StdLegalEnterpriseExecutedTemp> nullCaseNoInS2 = copyS2.stream().filter(stdLegalEnterpriseExecutedTemp -> Objects.isNull(stdLegalEnterpriseExecutedTemp.getCaseCode())).collect(Collectors.toList());
        List<StdLegalEntUnexecutedTemp> nullCaseNoInS3 = copyS3.stream().filter(stdLegalEntUnexecutedTemp -> Objects.isNull(stdLegalEntUnexecutedTemp.getCaseCode())).collect(Collectors.toList());

        copyS1.removeAll(nullCaseNoInS1);
        copyS2.removeAll(nullCaseNoInS2);
        copyS3.removeAll(nullCaseNoInS3);

        /**
         * 2.清洗不同司法表间的重复记录：
         * ①若set1与set2存在非空【案号】一致的案件记录，则保留set1的案件记录；    //清理s2
         * ②若set1与set3存在非空【案号】一致的案件记录，则保留set3的案件记录；    //清理s1
         * ③若set2与set3存在非空【案号】一致的案件记录，则保留set3的案件记录。    //清理s2
         */
        cleanRepeat2(copyS1, copyS2, copyS3);

        /**
         * 3.利用上一步保留的案件记录，再清洗各司法表内的重复记录：
         * ①保留各表内distinct（非空【案号】）& 最高（【@修订案件风险等级】）的案件记录。     // 每个案号组风险等级最高的n条保留
         * 注：各表统计采用的【案件风险等级】（风险等级由高到低顺序：H>M6>M5>M4>M3>M2>M1>L）
         */
        cleanRepeat3(copyS1, copyS2, copyS3);

        /**
         * set1
         * ②若set1存在多个非空【案号】且【案件风险等级】均一致案件记录，处理规则如下：
         * if 存在【PTYPE】=14 or 15 or 16的 至少两条案件记录，则优先保留顺序：16 > 15> 14
         * else if 【PTYPE】一致 ，则优先保留 案件性质/案件类型【DOCUCLASS】不为空的案件记录
         * else if 【PTYPE】一致 & 案件性质/案件类型【DOCUCLASS】一致，优先保留立案时间/开庭时间/案件日期/裁定时间【CASEDATE】不为空的案件记录
         * else if 【PTYPE】一致 & 案件性质/案件类型【DOCUCLASS】 & 立案时间/开庭时间/案件日期/裁定时间【CASEDATE】，随机保留一条案件记录
         * esle 均保留案件记录
         */
        cleanS1(copyS1);

        /**
         * ③若set2或set3各自表内存在多个非空【案号】且【案件风险等级】均一致案件记录，处理规则如下：
         * else if 优先取各自表内【立案时间】有值的案件记录
         * else 随机保留一条"
         *
         * ③若set2或set3各自表内存在多个非空【案号】且【案件风险等级】均一致案件记录，处理规则如下：
         * if优先取各自表内【立案时间】不为空的记录
         * else if【立案时间】一致的多条案件记录，随机保留一条案件记录
         * else 均保留案件记录
         */
        clearS2(copyS2);
        clearS3(copyS3);
        //获取中间表临时表数据
        List<StdLegalCasemedianTemp> stdLegalCasemedianTempList = getStdLegalCasemedianTemps(reqId, nullCaseNoInS1, copyS1);
        //保存结果
        saveTemp(stdLegalCasemedianTempList, copyS1, nullCaseNoInS1, copyS2, nullCaseNoInS2, copyS3, nullCaseNoInS3);
    }

    @Override
    public List<LitigaCaseVo> getLitigaCase(String reqId) {
        List<LitigaCaseVo> litigaCaseVos = Lists.newArrayList();
        List<StdLegalEnterpriseExecutedTemp> stdLegalEnterpriseExecutedTempList = stdLegalEnterpriseExecutedTempService.findByReqId(reqId);
        List<StdLegalDataStructuredTemp> stdLegalDataStructuredTempList = stdLegalDataStructuredTempService.findByReqId(reqId);
        List<StdLegalEntUnexecutedTemp> stdLegalEntUnexecutedTempList = stdLegalEntUnexecutedTempService.findByReqId(reqId);

        List<String> serialNos = stdLegalDataStructuredTempList.stream().map(StdLegalDataStructuredTemp::getSerialno).collect(Collectors.toList());
        List<StdLegalCasemedianTemp> stdLegalCasemedianTempList = stdLegalCasemedianTempService.findByReqAndSerialNos(reqId,serialNos);
        Map<String, StdLegalCasemedianTemp> casemedianTempMap = stdLegalCasemedianTempList.stream().collect(Collectors.toMap(item -> item.getSerialNo(), item -> item));

        for (StdLegalEnterpriseExecutedTemp stdLegalEnterpriseExecutedTemp : stdLegalEnterpriseExecutedTempList) {
            litigaCaseVos.add(stdLegalEnterpriseExecutedTemp.toLitigaCaseVo());
        }

        for (StdLegalDataStructuredTemp stdLegalDataStructuredTemp : stdLegalDataStructuredTempList) {
            litigaCaseVos.add(stdLegalDataStructuredTemp.toLitigaCaseVo(casemedianTempMap.get(stdLegalDataStructuredTemp.getSerialno())));
        }

        for (StdLegalEntUnexecutedTemp stdLegalEntUnexecutedTemp : stdLegalEntUnexecutedTempList) {
            litigaCaseVos.add(stdLegalEntUnexecutedTemp.toLitigaCaseVo());
        }

        for(LitigaCaseVo litigaCaseVo : litigaCaseVos) {
            DicTable dic = DetectCacheUtils.getDicTableByTypeAndDicValue(DicTypeEnum.COURTLEVEL.name(), litigaCaseVo.getCourtLevel());
            if(Objects.nonNull(dic)) {
                litigaCaseVo.setCourtLevel(dic.getDicName());
            }
        }
        return litigaCaseVos.stream().filter(item -> Objects.nonNull(item.getCaseCode())).collect(Collectors.toList());
    }

    public void calcRiskLevel(String reqId, List<StdLegalDataStructuredTemp> copyS1, List<StdLegalEnterpriseExecutedTemp> copyS2, List<StdLegalEntUnexecutedTemp> copyS3) {
        calcRiskLevelOfS1(reqId, copyS1);
        calcRiskLevelOfS2(copyS2);
        calcRiskLevelOfS3(copyS3);
    }

    private List<StdLegalEntUnexecutedTemp> getS3(String reqId) {
        List<StdLegalEntUnexecutedTemp> set3 = ConvertUtils.sourceToTarget(stdLegalEntUnexecutedService.findStdLegalEntUnexecutedByReqId(reqId), StdLegalEntUnexecutedTemp.class);
        set3.forEach(stdLegalEntUnexecutedTemp -> {
            Long id = stdLegalEntUnexecutedTemp.getId();
            stdLegalEntUnexecutedTemp.setStdLegalEntUnexecutedId(id);
            stdLegalEntUnexecutedTemp.setId(null);
        });
        return set3;
    }

    private List<StdLegalEnterpriseExecutedTemp> getS2(String reqId) {
        List<StdLegalEnterpriseExecutedTemp> set2 = ConvertUtils.sourceToTarget(stdLegalEnterpriseExecutedService.findStdLegalEnterpriseExecutedByReqId(reqId), StdLegalEnterpriseExecutedTemp.class);
        set2.forEach(stdLegalEnterpriseExecutedTemp -> {
            Long id = stdLegalEnterpriseExecutedTemp.getId();
            stdLegalEnterpriseExecutedTemp.setStdLegalEnterpriseExecutedId(id);
            stdLegalEnterpriseExecutedTemp.setId(null);
        });
        return set2;
    }

    private List<StdLegalDataStructuredTemp> getS1(String reqId) {
        List<StdLegalDataStructuredTemp> set1 = ConvertUtils.sourceToTarget(stdLegalDataStructuredService.findStdLegalDataStructuredByReqId(reqId), StdLegalDataStructuredTemp.class);
        set1.forEach(stdLegalDataStructuredTemp -> {
            Long id = stdLegalDataStructuredTemp.getId();
            stdLegalDataStructuredTemp.setStdLegalDataStructuredId(id);
            stdLegalDataStructuredTemp.setId(null);
        });
        return set1;
    }

    private List<StdLegalCasemedianTemp> getStdLegalCasemedianTemps(String reqId, List<StdLegalDataStructuredTemp> nullCaseNoInS1, List<StdLegalDataStructuredTemp> copyS1) {
        List<String> s1SerialNos = nullCaseNoInS1.stream().map(StdLegalDataStructuredTemp::getSerialno).collect(Collectors.toList());
        s1SerialNos.addAll(copyS1.stream().map(StdLegalDataStructuredTemp::getSerialno).collect(Collectors.toList()));
        List<StdLegalCasemedianTemp> stdLegalCasemedianTempList = ConvertUtils.sourceToTarget(stdLegalCasemedianService.findStdLegalCasemedianBySerialNos(reqId, s1SerialNos), StdLegalCasemedianTemp.class);
        stdLegalCasemedianTempList.forEach(stdLegalCasemedianTemp -> {
            Long id = stdLegalCasemedianTemp.getId();
            stdLegalCasemedianTemp.setStdLegalCasemedianId(id);
            stdLegalCasemedianTemp.setId(null);
        });
        return stdLegalCasemedianTempList;
    }

    private void saveTemp(List<StdLegalCasemedianTemp> stdLegalCasemedianTempList, List<StdLegalDataStructuredTemp> copyS1, List<StdLegalDataStructuredTemp> nullCaseNoInS1, List<StdLegalEnterpriseExecutedTemp> copyS2, List<StdLegalEnterpriseExecutedTemp> nullCaseNoInS2, List<StdLegalEntUnexecutedTemp> copyS3, List<StdLegalEntUnexecutedTemp> nullCaseNoInS3) {
        stdLegalCasemedianTempService.saveBatch(stdLegalCasemedianTempList);
        copyS1.addAll(nullCaseNoInS1);
        stdLegalDataStructuredTempService.saveBatch(copyS1);
        copyS2.addAll(nullCaseNoInS2);
        stdLegalEnterpriseExecutedTempService.saveBatch(copyS2);
        copyS3.addAll(nullCaseNoInS3);
        stdLegalEntUnexecutedTempService.saveBatch(copyS3);
    }

    public void clearS3(List<StdLegalEntUnexecutedTemp> copyS3) {
        Map<String, List<StdLegalEntUnexecutedTemp>> copyS3GroupByRiskLevel = copyS3.stream().collect(Collectors.groupingBy(item -> item.getCaseCode() +"_"+ item.getCaseRiskLevel() + "_"+ StrUtils.getDataStr(item.getRegDate())));
        for (Map.Entry<String, List<StdLegalEntUnexecutedTemp>> entry : copyS3GroupByRiskLevel.entrySet()) {
            List<StdLegalEntUnexecutedTemp> v = entry.getValue();
            if (v.size() > 1) {
//                List<StdLegalEntUnexecutedTemp> nullCaseCreateTimeDatas = v.stream().filter(stdLegalEntUnexecutedTemp -> Objects.isNull(stdLegalEntUnexecutedTemp.getRegDate())).collect(Collectors.toList());
//                if (v.size() == nullCaseCreateTimeDatas.size()) {
//                    //随机保留一条
//                    v.remove(v.get(RandomUtils.nextInt(v.size())));
//                    if (!CollectionUtils.isEmpty(v)) {
//                        copyS3.removeAll(v);
//                    }
//                } else {
//                    // 优先取各自表内【立案时间】有值的案件记录
//                    if (!CollectionUtils.isEmpty(nullCaseCreateTimeDatas)) {
//                        copyS3.removeAll(nullCaseCreateTimeDatas);
//                    }
//                }
                //随机保留一条
                v.remove(v.get(RandomUtils.nextInt(v.size())));
                if (!CollectionUtils.isEmpty(v)) {
                    copyS3.removeAll(v);
                }
            }
        }
    }

    public void clearS2(List<StdLegalEnterpriseExecutedTemp> copyS2) {
        //根据案号_风险等级分组
        Map<String, List<StdLegalEnterpriseExecutedTemp>> copyS2Group = Utils.getList(copyS2).stream().collect(Collectors.groupingBy(item -> item.getCaseCode() + "_" + item.getCaseRiskLevel() + "_" + StrUtils.getDataStr(item.getCaseCreateTime())));
        List<StdLegalEnterpriseExecutedTemp> removeCaseCreateTimeDatas = Lists.newArrayList();
        for (Map.Entry<String, List<StdLegalEnterpriseExecutedTemp>> entry : copyS2Group.entrySet()) {
            List<StdLegalEnterpriseExecutedTemp> v = entry.getValue();
//            if (v.size() > 1) {
//                List<StdLegalEnterpriseExecutedTemp> nullCaseCreateTimeDatas = v.stream().filter(stdLegalEnterpriseExecutedTemp -> Objects.isNull(stdLegalEnterpriseExecutedTemp.getCaseCreateTime())).collect(Collectors.toList());
//                //过滤掉组内立案时间为空的记录，如果都为空随机保留一条
//                if (v.size() == nullCaseCreateTimeDatas.size()) {
//                    //随机保留一条
//                    v.remove(v.get(RandomUtils.nextInt(v.size())));
//                    if (!CollectionUtils.isEmpty(v)) {
//                        copyS2.removeAll(v);
//                    }
//                } else {
//                    // 优先取各自表内【立案时间】有值的案件记录
//                    if (!CollectionUtils.isEmpty(nullCaseCreateTimeDatas)) {
//                        copyS2.removeAll(nullCaseCreateTimeDatas);
//                    }
//                }
//
//            }
            //随机保留一条记录
            if(v.size() > 1) {
                v.remove(v.get(RandomUtils.nextInt(v.size())));
                if (!CollectionUtils.isEmpty(v)) {
                    copyS2.removeAll(v);
                }
            }
        }
    }

    public void cleanS1(List<StdLegalDataStructuredTemp> copyS1) {
        if (condition0(copyS1)) {
            Map<String, List<StdLegalDataStructuredTemp>> copyS1GroupByCaseNoAndCaseRiskLevelMap = copyS1.stream().collect(Collectors.groupingBy(stdLegalDataStructuredTemp -> stdLegalDataStructuredTemp.getCaseNo()+ stdLegalDataStructuredTemp.getCaseRiskLevel()));
            for (Map.Entry<String, List<StdLegalDataStructuredTemp>> copyS1GroupByCaseRiskLevelMapEntry : copyS1GroupByCaseNoAndCaseRiskLevelMap.entrySet()) {
                List<StdLegalDataStructuredTemp> v = copyS1GroupByCaseRiskLevelMapEntry.getValue();
                //1 存在【PTYPE】=14 or 15 or 16的 至少两条案件记录，则优先保留顺序：16 > 15> 14
                if (condition1(v)) {
                    String maxPtype = v.stream().map(StdLegalDataStructuredTemp::getPtype).max(Comparator.comparingInt(Integer::valueOf)).get();
                    List<StdLegalDataStructuredTemp> notMaxPtypeDatas = v.stream().filter(stdLegalDataStructuredTemp -> !stdLegalDataStructuredTemp.getPtype().equalsIgnoreCase(maxPtype)).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(notMaxPtypeDatas)) {
                        copyS1.removeAll(notMaxPtypeDatas);
                        v.removeAll(notMaxPtypeDatas);
                    }
                }

                //2 if 【PTYPE】一致 ，则优先保留 案件性质/案件类型【DOCUCLASS】不为空的案件记录
                Map<String, List<StdLegalDataStructuredTemp>> groupByPtype = v.stream().collect(Collectors.groupingBy(StdLegalDataStructuredTemp::getPtype));
                for (Map.Entry<String, List<StdLegalDataStructuredTemp>> groupByPtypeEntry : groupByPtype.entrySet()) {
                    List<StdLegalDataStructuredTemp> ptypeEqDatas = groupByPtypeEntry.getValue();
                    // ptype存在并且一致
                    if (ptypeEqDatas.size() > 1 && StringUtils.isNotBlank(ptypeEqDatas.get(0).getPtype())) {
                        List<StdLegalDataStructuredTemp> docuClassIsNullData = v.stream().filter(stdLegalDataStructuredTemp -> Objects.isNull(stdLegalDataStructuredTemp.getDocuClass())).collect(Collectors.toList());
                        // v.size()>docuClassIsNullData.size() 全为空时不清除，认为一致
                        if (!CollectionUtils.isEmpty(docuClassIsNullData) && v.size()>docuClassIsNullData.size()) {
                            copyS1.removeAll(docuClassIsNullData);
                            v.removeAll(docuClassIsNullData);
                        }
                    }
                }
                //3 if 【PTYPE】一致 & 案件性质/案件类型【DOCUCLASS】一致，优先保留立案时间/开庭时间/案件日期/裁定时间【CASEDATE】不为空的案件记录
                Map<String, List<StdLegalDataStructuredTemp>> groupByPtypeAndDocuclass = v.stream().collect(Collectors.groupingBy(stdLegalDataStructuredTemp -> stdLegalDataStructuredTemp.getPtype() + stdLegalDataStructuredTemp.getDocuClass()));
                for (Map.Entry<String, List<StdLegalDataStructuredTemp>> groupByPtypeAndDocuclassEntry : groupByPtypeAndDocuclass.entrySet()) {
                    List<StdLegalDataStructuredTemp> ptypeAndDocuclassEqData = groupByPtypeAndDocuclassEntry.getValue();
                    // ptype 存在并且一致
                    if (ptypeAndDocuclassEqData.size() > 1 && StringUtils.isNotBlank(ptypeAndDocuclassEqData.get(0).getPtype())) {
                        List<StdLegalDataStructuredTemp> caseDateIsNullDatas = ptypeAndDocuclassEqData.stream().filter(stdLegalDataStructuredTemp -> Objects.isNull(stdLegalDataStructuredTemp.getCaseDate())).collect(Collectors.toList());
                        // 全为空代表一致，不清除
                        if (!CollectionUtils.isEmpty(caseDateIsNullDatas) && v.size()>caseDateIsNullDatas.size()) {
                            copyS1.removeAll(caseDateIsNullDatas);
                            v.removeAll(caseDateIsNullDatas);
                        }
                    }
                }
                //4 if 【PTYPE】一致 & 案件性质/案件类型【DOCUCLASS】 & 立案时间/开庭时间/案件日期/裁定时间【CASEDATE】，随机保留一条案件记录
                Map<String, List<StdLegalDataStructuredTemp>> groupByPtypeAndDocuclassAndCaseDate = v.stream().collect(Collectors.groupingBy(stdLegalDataStructuredTemp -> stdLegalDataStructuredTemp.getPtype() + stdLegalDataStructuredTemp.getDocuClass() + stdLegalDataStructuredTemp.getCaseDate()));
                for (Map.Entry<String, List<StdLegalDataStructuredTemp>> groupByPtypeAndDocuclassAndCaseDateEntry : groupByPtypeAndDocuclassAndCaseDate.entrySet()) {
                    List<StdLegalDataStructuredTemp> ptypeAndDocuclassAndCaseDateEqData = groupByPtypeAndDocuclassAndCaseDateEntry.getValue();
                    // ptype docuclass casedate 存在并且一致
                    if (ptypeAndDocuclassAndCaseDateEqData.size() > 1 && StringUtils.isNotBlank(ptypeAndDocuclassAndCaseDateEqData.get(0).getPtype())) {
                        ptypeAndDocuclassAndCaseDateEqData.remove(ptypeAndDocuclassAndCaseDateEqData.get(RandomUtils.nextInt(ptypeAndDocuclassAndCaseDateEqData.size())));
                        if (!CollectionUtils.isEmpty(ptypeAndDocuclassAndCaseDateEqData) && v.size()>1) {
                            copyS1.removeAll(ptypeAndDocuclassAndCaseDateEqData);
                            v.removeAll(ptypeAndDocuclassAndCaseDateEqData);
                        }
                    }
                }
            }
        }
    }

    public void cleanRepeat3(List<StdLegalDataStructuredTemp> copyS1, List<StdLegalEnterpriseExecutedTemp> copyS2, List<StdLegalEntUnexecutedTemp> copyS3) {
        //按照案号分组
        Map<String, List<StdLegalDataStructuredTemp>> copyS1GroupByCaseNoMap = copyS1.stream().collect(Collectors.groupingBy(StdLegalDataStructuredTemp::getCaseNo));
        Map<String, List<StdLegalEnterpriseExecutedTemp>> copyS2GroupByCaseNoMap = copyS2.stream().collect(Collectors.groupingBy(StdLegalEnterpriseExecutedTemp::getCaseCode));
        Map<String, List<StdLegalEntUnexecutedTemp>> copyS3GroupByCaseNoMap = copyS3.stream().collect(Collectors.groupingBy(StdLegalEntUnexecutedTemp::getCaseCode));

        //每个案号组保留最高风险等级的记录
        List<StdLegalDataStructuredTemp> copyS1FilterRiskLevel = Lists.newArrayList();
        copyS1GroupByCaseNoMap.forEach((k, v) -> {
            String maxCaseRiskLevel = v.stream().map(StdLegalDataStructuredTemp::getCaseRiskLevel).max(Comparator.comparingInt(CASE_RISK_LEVEL_STAGE::indexOf)).get();
            copyS1FilterRiskLevel.addAll(v.stream().filter(stdLegalDataStructuredTemp -> !stdLegalDataStructuredTemp.getCaseRiskLevel().equalsIgnoreCase(maxCaseRiskLevel)).collect(Collectors.toList()));
        });
        copyS1.removeAll(copyS1FilterRiskLevel);

        List<StdLegalEnterpriseExecutedTemp> copyS2FilterRiskLevel = Lists.newArrayList();
        copyS2GroupByCaseNoMap.forEach((k, v) -> {
            String maxCaseRiskLevel = v.stream().map(StdLegalEnterpriseExecutedTemp::getCaseRiskLevel).max(Comparator.comparingInt(CASE_RISK_LEVEL_STAGE::indexOf)).get();
            copyS2FilterRiskLevel.addAll(v.stream().filter(stdLegalEnterpriseExecutedTemp -> !stdLegalEnterpriseExecutedTemp.getCaseRiskLevel().equalsIgnoreCase(maxCaseRiskLevel)).collect(Collectors.toList()));
        });
        copyS2.removeAll(copyS2FilterRiskLevel);

        List<StdLegalEntUnexecutedTemp> copyS3FilterRiskLevel = Lists.newArrayList();
        copyS3GroupByCaseNoMap.forEach((k, v) -> {
            String maxCaseRiskLevel = v.stream().map(StdLegalEntUnexecutedTemp::getCaseRiskLevel).max(Comparator.comparingInt(CASE_RISK_LEVEL_STAGE::indexOf)).get();
            copyS3FilterRiskLevel.addAll(v.stream().filter(stdLegalEntUnexecutedTemp -> !stdLegalEntUnexecutedTemp.getCaseRiskLevel().equalsIgnoreCase(maxCaseRiskLevel)).collect(Collectors.toList()));
        });
        copyS3.removeAll(copyS3FilterRiskLevel);
    }

    public void cleanRepeat2(List<StdLegalDataStructuredTemp> copyS1, List<StdLegalEnterpriseExecutedTemp> copyS2, List<StdLegalEntUnexecutedTemp> copyS3) {
        final Set<String> caseNoInS1 = copyS1.stream().map(StdLegalDataStructuredTemp::getCaseNo).collect(Collectors.toSet());
        List<StdLegalEnterpriseExecutedTemp> copyS2CaseCodeInCopyS1 = copyS2.stream().filter(stdLegalEnterpriseExecutedTemp -> caseNoInS1.contains(stdLegalEnterpriseExecutedTemp.getCaseCode())).collect(Collectors.toList());
        copyS2.removeAll(copyS2CaseCodeInCopyS1);

        final Set<String> caseNoInS3 = copyS3.stream().map(StdLegalEntUnexecutedTemp::getCaseCode).collect(Collectors.toSet());
        copyS1.removeAll(copyS1.stream().filter(stdLegalDataStructuredTemp -> caseNoInS3.contains(stdLegalDataStructuredTemp.getCaseNo())).collect(Collectors.toList()));
        copyS2.removeAll(copyS2.stream().filter(stdLegalEnterpriseExecutedTemp -> caseNoInS3.contains(stdLegalEnterpriseExecutedTemp.getCaseCode())).collect(Collectors.toList()));
    }

    private boolean condition1(List<StdLegalDataStructuredTemp> s) {
        //if 存在【PTYPE】=14 or 15 or 16的 至少两条案件记录
        List<StdLegalDataStructuredTemp> collect = s.stream().filter(stdLegalDataStructuredTemp -> P_TYPES.contains(stdLegalDataStructuredTemp.getPtype())).collect(Collectors.toList());
        return !CollectionUtils.isEmpty(collect) && collect.size() > 1;
    }

    private boolean condition0(List<StdLegalDataStructuredTemp> copyS1) {
        //若set1存在多个非空【案号】且【案件风险等级】均一致案件记录
        Map<String, List<StdLegalDataStructuredTemp>> collect = copyS1.stream().collect(Collectors.groupingBy(StdLegalDataStructuredTemp::getCaseRiskLevel));
        return copyS1.size() > collect.size();
    }

    private void calcRiskLevelOfS3(List<StdLegalEntUnexecutedTemp> copyS3) {
        /**
         * "def【失信被执行案件风险等级】
         * STD_LEGAL_ENT_UNEXECUTED的案件记录，均默认【失信被执行案件风险等级】='H'"
         **/
        copyS3.forEach(stdLegalEntUnexecutedTemp -> stdLegalEntUnexecutedTemp.setCaseRiskLevel(CASERISKLEVEL_H));
    }

    private void calcRiskLevelOfS2(List<StdLegalEnterpriseExecutedTemp> copyS2) {
        /**
         * "def【被执行案件风险等级】
         * if 【间隔年数】>=1, then【被执行案件风险等级】='L'
         * else if 【间隔年数】>=0.5 or 为空, then 【被执行案件风险等级】='M3'
         * else 【被执行案件风险等级】='M6'
         *
         * 说明：def【间隔年数】
         * if STD_LEGAL_ENTERPRISE_EXECUTED.立案时间【CASECREATETIME】不为空
         * then 【间隔年数】 =(【@当前时间】-立案时间【CASECREATETIME】)/365
         * else 【间隔年数】为空"
         */
        copyS2.forEach(stdLegalEnterpriseExecutedTemp -> {
            String caseRiskLevel = CASERISKLEVEL_M6;
            // 间隔年数
            Double numberOfYear = null;
            LocalDate caseCreateTime = stdLegalEnterpriseExecutedTemp.getCaseCreateTime();
            if (Objects.nonNull(caseCreateTime)) {
                numberOfYear = (double) (LocalDate.now().toEpochDay() - caseCreateTime.toEpochDay()) / 365;
            }

            // 风险等级
            if (Objects.nonNull(numberOfYear) && numberOfYear >= 1) {
                caseRiskLevel = CASERISKLEVEL_L;
            } else if (Objects.isNull(numberOfYear) || (Objects.nonNull(numberOfYear) && numberOfYear >= 0.5)) {
                caseRiskLevel = CASERISKLEVEL_M3;
            }

            stdLegalEnterpriseExecutedTemp.setCaseRiskLevel(caseRiskLevel);
        });
    }

    private void calcRiskLevelOfS1(String reqId, List<StdLegalDataStructuredTemp> copyS1) {
        Set<String> serialNos = copyS1.stream().map(StdLegalDataStructuredTemp::getSerialno).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(serialNos)){
            return ;
        }
        LambdaQueryWrapper<StdLegalCasemedian> queryWrapper = new LambdaQueryWrapper<StdLegalCasemedian>()
                .eq(StdLegalCasemedian::getReqId, reqId)
                .in(StdLegalCasemedian::getSerialNo, serialNos);
        List<StdLegalCasemedian> stdLegalCasemedians = stdLegalCasemedianMapper.selectList(queryWrapper);
        Map<String, String> map = stdLegalCasemedians.stream().collect(Collectors.toMap(StdLegalCasemedian::getSerialNo, StdLegalCasemedian::getCaseRiskLevel));
        copyS1.forEach(stdLegalDataStructuredTemp -> stdLegalDataStructuredTemp.setCaseRiskLevel(map.get(stdLegalDataStructuredTemp.getSerialno())));
    }
}
