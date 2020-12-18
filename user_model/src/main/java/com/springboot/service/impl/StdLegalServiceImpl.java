package com.springboot.service.impl;

import com.google.common.collect.Lists;
import com.springboot.domain.risk.StdLegalCasemedian;
import com.springboot.domain.risk.StdLegalDataStructured;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.StdLegalDataStructuredMapper;
import com.springboot.service.StdLegalDataStructuredService;
import com.springboot.service.StdLegalService;
import com.springboot.util.DateUtils;
import com.springboot.utils.CalculatUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class StdLegalServiceImpl implements StdLegalService {

    @Autowired
    private StdLegalDataStructuredService stdLegalDataStructuredService;

    @Autowired
    private StdLegalDataStructuredMapper stdLegalDataStructuredMapper;

    //案件风险等级判断
    private static final String CASERISKLEVEL_N = "N";
    private static final String CASERISKLEVEL_L = "L";
    private static final String CASERISKLEVEL_M1 = "M1";
    private static final String CASERISKLEVEL_M2 = "M2";
    private static final String CASERISKLEVEL_M3 = "M3";
    private static final String CASERISKLEVEL_M4 = "M4";
    private static final String CASERISKLEVEL_M5 = "M5";
    private static final String CASERISKLEVEL_M6 = "M6";
    private static final String CASERISKLEVEL_H = "H";

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
        if(CollectionUtils.isEmpty(stdLegalDataStructuredList)) {
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
        for(StdLegalDataStructured stdLegalDataStructured : stdLegalDataStructuredList) {
            StdLegalCasemedian stdLegalCasemedian = new StdLegalCasemedian();

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

            String createTime = DateUtils.convertDateStr(stdLegalDataStructured.getCreateTime());
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
//                if ("16".equals(pType) || StringUtils.isEmpty(pType)) {
//                    caseriskLevel = getCaseriskLevelByPtype16orNull(caseriskLevel, stdLegalDataStructured, intervalYear,
//                            caseResult, caseLevel, lawStatus, pDesc, sentenceBrief,
//                            phase, addSentenceEffects, sentenceEffect, referMoney, paymentScale);
//                }
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
        if ("执行人".equals(lawStatus) || "申请人".equals(lawStatus) || "原告".equals(lawStatus)|| "申请执行人".equals(lawStatus)) {
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
     * @param paymentScale
     * @return
     */
    private String getCaseriskLevelByPtype16orNull(String caseriskLevel, StdLegalDataStructured legalData, Double intervalYear,
                                                   String caseResult, String caseLevel, String lawStatus, String pDesc, String sentenceBrief,
                                                   String phase, String[] addSentenceEffects, String sentenceEffect, BigDecimal referMoney, BigDecimal paymentScale) {
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
                    sentenceEffect, referMoney, paymentScale);
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
     * @param paymentScale
     * @return
     */
    private String getPtype16orNullThreeLevel(String caseriskLevel, Double intervalYear, String caseLevel, String lawStatus, String sentenceEffect, BigDecimal referMoney, BigDecimal paymentScale) {
        if (CalculatUtil.myEquals("0", sentenceEffect)) {
            if (referMoney != null && paymentScale != null
                    && ((0 <= referMoney.doubleValue() && referMoney.doubleValue() < 500000)
                    || (0 <= paymentScale.doubleValue() && paymentScale.doubleValue() < 0.05))) {
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
        } else if (StringUtils.isEmpty(sentenceEffect)|| CalculatUtil.myEquals("1", sentenceEffect) || CalculatUtil.myEquals("99", sentenceEffect)) {
            if (referMoney != null && paymentScale != null
                    && ((0 <= referMoney.doubleValue() && referMoney.doubleValue() < 300000)
                    || (0 <= paymentScale.doubleValue() && paymentScale.doubleValue() < 0.03))) {
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
            } else if (referMoney != null && paymentScale != null
                    && (referMoney.doubleValue() <= 500000 || paymentScale.doubleValue() < 0.05)) {
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
            } else if (referMoney != null && paymentScale != null
                    && (referMoney.doubleValue() <= 800000 || paymentScale.doubleValue() < 0.08)) {
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
            } else if (referMoney != null && paymentScale != null
                    && (referMoney.doubleValue() > 800000 && paymentScale.doubleValue() >= 0.08)) {
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
}
