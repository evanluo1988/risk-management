package com.springboot.service.impl;

import com.google.common.collect.Maps;
import com.springboot.domain.StdLegalDataAdded;
import com.springboot.domain.StdLegalDataStructured;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.JudgeAnynasisMapper;
import com.springboot.service.LegalDataAddColumnService;
import com.springboot.utils.CNNMFilter;
import com.springboot.utils.CommonUtil;
import com.springboot.utils.StrUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 司法解析引擎【司法诉讼新增字段解析入库】
 * @author: ZZX
 * @date: 2017-7-26 下午2:56:35
 * @fileName:com.v.util.AnalysisJudgeAddColumnServiceImpl.java
 */
@Service
public class LegalDataAddColumnServiceImpl implements LegalDataAddColumnService {
    @Autowired
    private JudgeAnynasisMapper jaMapper;

    private static Logger logger = LoggerFactory.getLogger(LegalDataAddColumnServiceImpl.class);
    private static final String ANYNASIS_BLANK = null;// 文档描述的”空“，暂定null
    private static Map<String, String> REGEXMAP_SAJE = new HashMap<String, String>(); // 涉案金额
    private static Map<String, String> REGEXMAP_SLJG = new HashMap<String, String>(); // 审理结果
    private static Map<String, String> REGEXMAP_ROLE = new HashMap<String, String>(); // 客户角色准确性校验
    private static String SAJE_TARGET = null;// 涉案金额-金额基础正则
    private static String SAJE_UNIT = null;// 涉案金额-金额单位基础正则

    private static Pattern NUMPATTERN = Pattern.compile("[0-9零一二三四五六七八九十百千万亿]");

    /**
     * @description:初始化正则Map
     * @注意：此方法随系统启动执行一次
     * @Autor: ZZX
     * @time:2017-11-1 下午11:54:07
     */
    @Override
    public void initAnalysisJudicialEngine() {
        Map<String, Object> dataMap = Maps.newHashMap();
        // 1,REGEXMAP_SAJE 涉案金额
        List<Map<String, Object>> basicList_saje = jaMapper.CaseMoneyBasic();// 正则基础表
        List<Map<String, Object>> regexList_saje = jaMapper.CaseMoneyRegex();// 正则式表
        String name = "";
        String value = "";
        for (Map<String, Object> singleMap : regexList_saje) {
            name = (String) singleMap.get("name");
            value = (String) singleMap.get("value");
            REGEXMAP_SAJE.put(name, initRegex(value, basicList_saje));
        }

        for (Map<String, Object> map : basicList_saje) {
            if (map.get("param_name") != null) {
                if ("target".equals((String) map.get("param_name"))) {
                    SAJE_TARGET = (String) map.get("value");// (?:\d*[,，]*)*\d*\.?\d*[零一二三四五六七八九十百千万亿]*
                    continue;
                }
            }
            if (map.get("param_name") != null) {
                if ("unit".equals((String) map.get("param_name"))) {
                    SAJE_UNIT = (String) map.get("value");
                }
            }
        }

        // 2,审理结果
        List<Map<String, Object>> basicList_sljg = jaMapper.JudgeResultBasic();// 正则基础表
        List<Map<String, Object>> regexList_sljg = jaMapper.JudgeResultRegex();// 正则式表
        for (Map<String, Object> singleMap : regexList_sljg) {
            name = (String) singleMap.get("name");
            value = (String) singleMap.get("value");
            REGEXMAP_SLJG.put(name, initRegex(value, basicList_sljg));
        }

        // 3,客户角色准确性校验
        List<Map<String, Object>> listRoleBasic = jaMapper.JudgeRoleCheckBasic();
        List<Map<String, String>> listRoleRegex = jaMapper.JudgeRoleCheckRegex();
        for (Map<String, String> singleMap : listRoleRegex) {
            name = singleMap.get("name");
            value = singleMap.get("regex");
            REGEXMAP_ROLE.put(name, initRegex(value, listRoleBasic));
        }
    }

    /**
     * @param regex     需要被初始化的正则表达式
     * @param basicList 基础配置表
     * @return
     * @description:实例化正则表达式，将对应的配置取值放入正则表达式
     * @Autor: ZZX
     * @time:2017-7-28 上午12:42:06
     */
    public static String initRegex(String regex, List<Map<String, Object>> basicList) {

        if (CollectionUtils.isEmpty(basicList)) {
            return regex;
        }
        String beReplace = "";
        String toReplace = "";
        for (Map<String, Object> map : basicList) {
            if (map.get("description") != null) {
                beReplace = "【" + (String) map.get("description") + "】";
                if (regex.contains(beReplace)) {
                    if (map.get("value") != null) {
                        toReplace = (String) map.get("value");
                        regex = regex.replace(beReplace, "(?:" + toReplace + ")");// 非获取捕获
                    }
                }
            }
        }
        return regex;
    }



    /**
     * @param judgeData
     * @param judgeData
     * @description:异步处理司法诉讼新增字段解析入库--改为同步
     * @Autor: ZZX
     * @time:2017-7-26 下午2:56:08
     */
    public List<StdLegalDataAdded> anynasisAndInsert(StdLegalDataStructured judgeData, String reqID, String uuid)
            throws Exception {

        List<StdLegalDataAdded> list = new ArrayList<>();// 入库List
        /** ==============================数据解析=============================== */
        // 1,案由分类 【参数：SerialNo:序列号，PType：公告类型， CaseNo：案号，CaseReason：案由】
        StdLegalDataAdded caseReason = getCaseReason(judgeData, reqID, uuid);
        // 2,涉案金额【参数：SerialNo:序列号，EntName：企业名称，Plaintiff：原告，Party：被告，JudgementResult：判决结果】
        // 3，涉案金额单位【参数：SerialNo:序列号，EntName：企业名称，Plaintiff：原告，Party：被告，JudgementResult：判决结果】
        //优化：涉案金额及涉案金额单位2个字段可以在一个流程中处理，改为一次性获取 	---修改日期：2019年5月24日
        StdLegalDataAdded[] involvedMoneyAndUnit = getInvolvedMoneyorUnity(judgeData, reqID, uuid);
        StdLegalDataAdded involvedMoney = involvedMoneyAndUnit[0];
        StdLegalDataAdded involvedMoneyUinty = involvedMoneyAndUnit[1];
        // 4，审理结果【参数：SerialNo:序列号，EntName：企业名称，Plaintiff：原告，Party：被告，JudgementResult：判决结果】
        StdLegalDataAdded judgementResult = getJudgementResult(judgeData, reqID, uuid);
        // 5，审理程序【参数：SerialNo:序列号，CaseNo：案号】
        StdLegalDataAdded judgementProcedure = getJudgementProcedure(judgeData, reqID, uuid);
        // 6，法院等级【参数：SerialNo:序列号，Court：公告法院】
        StdLegalDataAdded courtLevel = getCourtLevel(judgeData, reqID, uuid);
        // 7，案件影响力【参数：SerialNo:序列号，CourtLevel：已解析的新增字段-法院等级，Phase：已解析的新增字段-审理程序】
        StdLegalDataAdded caseInfluence = getCaseInfluence(judgeData, courtLevel, judgementProcedure, reqID, uuid);
        // 8，案件结果对客户的影响【参数：SerialNo:序列号，EntName：企业名称，Plaintiff：原告/上诉人/申请人，Party：被告/被上诉人/被申请人，SentenceBrief：已解析新增字段-审理结果】
        StdLegalDataAdded impactCaseResultOnCustomer = getImpactCaseResultOnCustomer(judgeData, judgementResult, reqID, uuid);
        // 9，客户诉讼角色准确性校验【参数：serialNo序列号，entName企业名称
        StdLegalDataAdded customerRoleCheck = getCustomerRoleCheck(judgeData, reqID, uuid);

        // 返回记录
        list.add(caseReason);
        list.add(involvedMoney);
        list.add(involvedMoneyUinty);
        list.add(judgementResult);
        list.add(judgementProcedure);
        list.add(courtLevel);
        list.add(caseInfluence);
        list.add(impactCaseResultOnCustomer);
        list.add(customerRoleCheck);
        return list;
    }


    //1,案由分类
    private StdLegalDataAdded getCaseReason(StdLegalDataStructured judgeData, String reqID, String uuid) throws ServiceException {
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno();// 序列号
        String Caseno = judgeData.getCaseNo();// 案号
        String Casereason = judgeData.getCaseReason();// 案由

        // 注意：正则匹配使用清洗了的数据
        String EntName = judgeData.getEntName() == null ? "" : judgeData.getEntName();// 企业名称
        StdLegalDataAdded caseReason = new StdLegalDataAdded();
        String[] ayflArr = {"14", "15", "16", "17", "18", "22"};
        SetParamCommon(caseReason, Serialno, "caseClass", "案由分类", EntName, reqID, uuid);// 设置7项数据
        Map<String, Object> result = new HashMap<>();
        try {


            if (CommonUtil.equals(ayflArr, Ptype)) {
                if (!CommonUtil.hasChar(Caseno) && !CommonUtil.hasChar(Casereason)) {
                    caseReason.setLegalValue(ANYNASIS_BLANK);// 案号、案由为空
                    caseReason.setValueLabel(ANYNASIS_BLANK);
                } else {
                    boolean stop = false;
                    if (CommonUtil.hasChar(Caseno)) {
                        result = jaMapper.caseReason(Caseno);
                        if (!CommonUtil.mapIsEmptyOrNull(result)) {
                            stop = true;
                        }
                    }

                    if (!stop && !CommonUtil.contains(new String[]{"刑", "行"}, Caseno) && Casereason != null) {
                        result = jaMapper.caseReason(Casereason);
                        if (!CommonUtil.mapIsEmptyOrNull(result)) {
                            stop = true;
                        }
                    }

                    if (!stop) {
                        caseReason.setLegalValue("99");
                        caseReason.setValueLabel("其他案由");
                    }
                }
                if (!CommonUtil.mapIsEmptyOrNull(result)) {
                    caseReason.setLegalValue((String) result.get("code"));
                    caseReason.setValueLabel((String) result.get("description"));
                }
            } else {
                caseReason.setLegalValue(ANYNASIS_BLANK);
                caseReason.setValueLabel(ANYNASIS_BLANK);
            }
            caseReason.setNeedingVerify(ANYNASIS_BLANK);//是否需要人工审核
            return caseReason;
        } catch (Exception e) {
            // throw new ServiceException("处理案由分类异常");
            logger.error("处理案由分类异常",e);
            return null;
        }

    }

    // 2,涉案金额
    // 3，涉案金额单位
    private StdLegalDataAdded[] getInvolvedMoneyorUnity(StdLegalDataStructured judgeData, String reqID, String uuid)throws ServiceException{

        /** ==========================使用到的安硕原始数据项=========================== */
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno();// 序列号

        // 注意：正则匹配使用清洗了的数据
        String plaintiffWithoutKuoHao = clearContent(judgeData.getPlaintiff());// 原告:清除各种括号及括号内的内容,以及例外正则式
        String partyWithoutKuoHao = clearContentSpecialFields(judgeData.getParty());// 被告:清除各种括号及括号内的内容
        String judgementresult = judgeData.getJudgementResult() == null ? "" : judgeData.getJudgementResult();// 判决结果
        String judgeWithoutKuoHao = clearContent(judgementresult);// 判决结果:清除各种括号及括号内的内容
        String entName = judgeData.getEntName() == null ? "" : judgeData.getEntName();// 企业名称
        String entNameWithoutKuoHao = clearContent(entName);// 企业名称:清除各种括号的EntName//正则运算中，与清洗了结构一致，不带括号等等
        String pdesc = judgeData.getPdesc() == null ? "" : judgeData.getPdesc();// 公告内容


        StdLegalDataAdded[] resultLegalDataAdded = new StdLegalDataAdded[2];

        StdLegalDataAdded involvedMoney = new StdLegalDataAdded();
        StdLegalDataAdded involvedMoneyorUnity = new StdLegalDataAdded();
        String[] sajeArr = { "16" };
        SetParamCommon(involvedMoney, Serialno, "payment", "涉案金额", entName, reqID, uuid);// 设置7项数据
        SetParamCommon(involvedMoneyorUnity, Serialno, "payUnit", "涉案金额单位", entName, reqID, uuid);// 设置7项数据
        try {
            if (CommonUtil.equals(sajeArr, Ptype)) {
                if (!CommonUtil.hasChar(pdesc)) {
                    SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                    SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                } else {
                    if (!CommonUtil.hasChar(judgementresult)) {
                        SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, "1");
                        SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, "1");
                    } else {
                        if (containsNumber(judgeWithoutKuoHao)) {// 含有数字
                            judgeWithoutKuoHao = removeExpectNumAndReg(judgeWithoutKuoHao);// 清除例外不取的正则表达式
                            String regexSplit = "[;；。]";// 按；;。分割判决结果
                            String results[] = judgeWithoutKuoHao.split(regexSplit);

                            List<String> sonModeList = new ArrayList<>();// 子模式结果List
                            List<String> sonModeListStep3 = new ArrayList<>();//只能选取前三项
                            /************************************** 开始处理每个子句 *****************************/
                            for (int i = 0; i < results.length; i++) {
                                String singleResult = results[i];
                                // if(!containsNumber(singleResult)){continue;}//不用处理单句，否则会将连带责任过滤掉
                                // 判断三个步骤是否往下进行的标志，若是step(i)获取到金额，修改该标志的值，该子句处理完毕
                                boolean executeFlag = Boolean.TRUE;
                                // ***step1***/
                                String step1[] = { "r11", /* 动态：企业名称 */
                                        "r12", /* 动态：企业名称 */
                                        "r13", /* 动态：企业名称 */
                                        "r21", /* 动态：企业名称 */
                                        "r22", /* 动态：企业名称 */
                                        "r31", /* 动态：企业名称 */
                                        "r32", /* 动态：企业名称 */
                                        "r33"/* 动态：企业名称 */
                                };
                                for (String regexName : step1) {
                                    String sourceRegex = REGEXMAP_SAJE.get(regexName);// 原始正则式
                                    sourceRegex = sourceRegex.replaceAll("【企业名称】", entNameWithoutKuoHao);// 替换企业名称
                                    Pattern pattern = Pattern.compile(sourceRegex);
                                    Matcher matcher = pattern.matcher(singleResult);
                                    if (matcher.find()) {
                                        sonModeList.add(matcher.group(1));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                                        executeFlag = Boolean.FALSE;// 修改状态值，step2，step3不执行
                                        logger.info("Serialno:{}【涉案金额step1】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, regexName);
                                        break;
                                    }
                                }

                                // ***step2***/
                                if (executeFlag) {
                                    boolean MultPlaintiff = CommonUtil
                                            .contains(new String[] { "、", "；", "，", ",", ";" }, plaintiffWithoutKuoHao);// 原告是否只有一个
                                    boolean MultParty = CommonUtil.contains(new String[] { "、", "；", "，", ",", ";" },
                                            partyWithoutKuoHao);// 被告是否只有一个
                                    if (!MultPlaintiff && !MultParty) {// 如果均等于1处理step2
                                        String step2[] = { "r011", "r012", "r013", "r021", "r022", "r031", "r032",
                                                "r033" };
                                        // PS：在step2的正则表达式中，没有动态替换的内容
                                        for (String regexName : step2) {
                                            String sourceRegex = REGEXMAP_SAJE.get(regexName);// 原始正则式
                                            Pattern pattern = Pattern.compile(sourceRegex);
                                            Matcher matcher = pattern.matcher(singleResult);

                                            if (matcher.find()) {
                                                sonModeList.add(StrUtils.removeComma(matcher.group(1)));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                                                executeFlag = Boolean.FALSE;// 修修改状态值，step3不执行
                                                logger.info("Serialno:{}【涉案金额step2】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, regexName);

                                                break;
                                            }

                                        }

                                    }

                                }

                                // ***step3***/
                                if (executeFlag) {
                                    String sourceRegex = REGEXMAP_SAJE.get("r43");// 原始正则式
                                    sourceRegex = sourceRegex.replaceAll("【企业名称】", entNameWithoutKuoHao);// 替换企业名称

                                    String sourceRegexR44 = REGEXMAP_SAJE.get("r44");// 原始正则式
                                    sourceRegexR44 = sourceRegexR44.replaceAll("【企业名称】", entNameWithoutKuoHao);// 替换企业名称

                                    if (Pattern.compile(sourceRegex).matcher(singleResult).matches()) {// r43
                                        // r401，r402,r403,3个正则式式子无需替换，均为静态字符串
                                        if (Pattern.compile(REGEXMAP_SAJE.get("r401")).matcher(singleResult)
                                                .matches()) {// r401
                                            String step3_401[] = { "r011", "r012", "r013" };
                                            // PS：在step3_401的正则表达式中，没有动态替换的内容
                                            for (String regexName : step3_401) {
                                                sourceRegex = REGEXMAP_SAJE.get(regexName);// 原始正则式
                                                Pattern pattern = Pattern.compile(sourceRegex);
                                                Matcher matcher = pattern.matcher(singleResult);

                                                if (matcher.find()) {
                                                    sonModeListStep3.add(matcher.group(1));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                                                    logger.info("Serialno:{}【涉案金额step3】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, regexName);

                                                    break;
                                                }

                                            }
                                        } else if (Pattern.compile(REGEXMAP_SAJE.get("r402")).matcher(singleResult)
                                                .matches()) {// r402
                                            String step3_402[] = { "r011", "r012", "r013" };
                                            // PS：在step3_401的正则表达式中，没有动态替换的内容
                                            for (String regexName : step3_402) {
                                                sourceRegex = REGEXMAP_SAJE.get(regexName);// 原始正则式
                                                Pattern pattern = Pattern.compile(sourceRegex);
                                                Matcher matcher = pattern.matcher(singleResult);

                                                if (matcher.find()) {
                                                    sonModeListStep3.add(matcher.group(1));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                                                    logger.info("Serialno:{}【涉案金额step3】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, regexName);

                                                    break;
                                                }

                                            }

                                        } else if (Pattern.compile(REGEXMAP_SAJE.get("r403")).matcher(singleResult)
                                                .matches()) {// r403
                                            String step3_403[] = { "r011", "r012", "r013" };
                                            // PS：在step3_401的正则表达式中，没有动态替换的内容
                                            for (String regexName : step3_403) {
                                                sourceRegex = REGEXMAP_SAJE.get(regexName);// 原始正则式
                                                Pattern pattern = Pattern.compile(sourceRegex);
                                                Matcher matcher = pattern.matcher(singleResult);

                                                if (matcher.find()) {
                                                    sonModeListStep3.add(matcher.group(1));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                                                    logger.info("Serialno:{}【涉案金额step3】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, regexName);

                                                    break;
                                                }

                                            }

                                        } else if (Pattern.compile(sourceRegexR44).matcher(singleResult).matches()) {// r44
                                            // 循环子句0到i:i是当前匹配成功r44的子句下标
                                            // 匹配到金额，停止循环标志
                                            for (int j = 0; j <= i; j++) {
                                                String singleResultR44 = results[j];// 从0循环到i
                                                String step3_44[] = { "r011", "r012", "r013" };
                                                // PS：在step3_401的正则表达式中，没有动态替换的内容
                                                for (String regexName : step3_44) {
                                                    sourceRegex = REGEXMAP_SAJE.get(regexName);// 原始正则式
                                                    Pattern pattern = Pattern.compile(sourceRegex);
                                                    Matcher matcher = pattern.matcher(singleResultR44);

                                                    if (matcher.find()) {
                                                        sonModeListStep3.add(matcher.group(1));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                                                        logger.info("Serialno:{}【涉案金额step3】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, regexName);

                                                        break;
                                                    }

                                                }
                                            }
                                        } else if (CommonUtil.contains(new String[] { ",", ";", "，", "；", "、" },
                                                partyWithoutKuoHao)) {// 被告人不止一个
                                            String PartyList[] = partyWithoutKuoHao.split("[、，；,;]");
                                            for (String PartyName : PartyList) {
                                                String regexR405 = REGEXMAP_SAJE.get("r405").replaceAll("【被告名称】",
                                                        PartyName);
                                                if (Pattern.compile(regexR405).matcher(singleResult).matches()
                                                        && !PartyName.equals(entNameWithoutKuoHao)) {// 匹配r405并且被告人！=
                                                    // 企业名称
                                                    String regex3_405[] = { // 被告名称
                                                            "r406", "r407", "r408" };
                                                    // 循环匹配子句：0到i
                                                    for (int j = 0; j <= i; j++) {
                                                        String singleResultThis = results[j];// 从0循环到i
                                                        for (String regexName : regex3_405) {
                                                            String regex = REGEXMAP_SAJE.get(regexName)
                                                                    .replaceAll("【被告名称】", PartyName);
                                                            Pattern pattern = Pattern.compile(regex);
                                                            Matcher matcher = pattern.matcher(singleResultThis);

                                                            if (matcher.matches()) {
                                                                sonModeListStep3.add(matcher.group(1));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                                                                logger.info("Serialno:{}【涉案金额step3】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, regexName);
                                                                break;
                                                            }
                                                        }
                                                    }

                                                }

                                            }

                                        }
                                    }

                                }

                            }
                            /************************************** 结束处理每个子句 *****************************/
                            // 处理汇总的子模式结果:sonModeList
                            if(sonModeListStep3.size() > 3){
                                //只取前三项
                                sonModeList.addAll(sonModeListStep3.subList(0, 3));
                            }else{
                                sonModeList.addAll(sonModeListStep3);
                            }

                            if (sonModeList != null && sonModeList.size() == 0) {
                                SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, "1");
                                SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, "1");
                            } else {
                                double finalMoney = 0;
                                String finalUnit = "元";// 最终单位以“元”开始
                                for (String moneyUnit : sonModeList) {
                                    String moneyAndUnit[] = MoneyAndUnit(moneyUnit);// 相加，处理千分位和中文，单位问题
                                    String money = moneyAndUnit[0];
                                    String unit = moneyAndUnit[1];
                                    // 获取统一单位【1，有外币以外币为准，除去人民币；2，多种外币，以第一种为准】
                                    if (!"0".equals(money)) {
                                        if (finalUnit.equals(unit)) {
                                            finalMoney += Double.parseDouble(money);
                                        } else {// 当前与最终单位不同
                                            if (finalUnit.equals("元")) {// 当前最终单位是元，新的单位不是元》》》取代
                                                finalMoney = Double.parseDouble(money);
                                                finalUnit = unit;
                                            }
                                            // 当前最终单位不是元》》》不可取代,不处理（取第一个外币）
                                        }
                                    }
                                }

                                String moneyString = CommonUtil.getDoubleString(finalMoney);
                                SetParamSpecial(involvedMoney, moneyString, moneyString, "0");
                                SetParamSpecial(involvedMoneyorUnity, finalUnit, finalUnit, "0");
                            }

                        } else {
                            SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                            SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                        }

                    }
                }
            } else {// pType != 16
                SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, ANYNASIS_BLANK);
                SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, ANYNASIS_BLANK);
            }
        } catch (Exception e) {
            logger.error("处理涉案金额+涉案金额单位异常:" , e);
            // throw new ServiceException("处理涉案金额+涉案金额单位异常:" + e);
            return null;
        }

        resultLegalDataAdded[0] = involvedMoney;
        resultLegalDataAdded[1] = involvedMoneyorUnity;

        return resultLegalDataAdded;
    }

    //4、审理结果
    private StdLegalDataAdded getJudgementResult(StdLegalDataStructured judgeData, String reqID, String uuid)throws ServiceException{

        /** ==========================使用到的安硕原始数据项=========================== */
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno();// 序列号

        // 注意：正则匹配使用清洗了的数据
        String PlaintiffWithoutKuoHao = clearContent(judgeData.getPlaintiff());// 原告:清除各种括号及括号内的内容,以及例外正则式
        String PartyWithoutKuoHao = clearContentSpecialFields(judgeData.getParty());// 被告:清除各种括号及括号内的内容
        String Judgementresult = judgeData.getJudgementResult() == null ? "" : judgeData.getJudgementResult();// 判决结果
        String JudgeWithoutKuoHao = clearContent(Judgementresult);// 判决结果:清除各种括号及括号内的内容
        String EntName = judgeData.getEntName() == null ? "" : judgeData.getEntName();// 企业名称
        String EntNameWithoutKuoHao = clearContent(EntName);// 企业名称:清除各种括号的EntName//正则运算中，与清洗了结构一致，不带括号等等
        String Pdesc = judgeData.getPdesc() == null ? "" : judgeData.getPdesc();// 公告内容

        StdLegalDataAdded J4_SLJG = new StdLegalDataAdded();
        SetParamCommon(J4_SLJG, Serialno, "sentenceBrief", "审理结果", EntName, reqID, uuid);// 设置7项数据
        String[] sljgArr = { "16" };
        try {
            if (CommonUtil.equals(sljgArr, Ptype)) {

                if (!CommonUtil.hasChar(Pdesc)) {

                    SetParamSpecial(J4_SLJG, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");

                } else if(Pdesc.contains("以调解方式结案")){
                    //新增需求
                    SetParamSpecial(J4_SLJG, "08", "双方达成调解协议", "0");

                } else if (!CommonUtil.hasChar(JudgeWithoutKuoHao)) {

                    SetParamSpecial(J4_SLJG, "99", "其他结果", "1");

                } else if (CommonUtil.hasChar(JudgeWithoutKuoHao)) {
                    String judgement = JudgeWithoutKuoHao.replaceAll("反诉原告", "被告").replaceAll("反诉被告", "原告");
                    String regexSplit = "[;；。]";// 按；;。分割判决结果
                    String results[] = judgement.split(regexSplit);
                    List<String> JudgeResultList = new ArrayList<>();// 符合的正则式名称列表

                    // 循环每个子句
                    loop4: for (String singleResult : results) {

                        {
                            // 合并文档内多个描述，此处按顺序执行，数组元素次序不可更改
                            String regexArray[] = { "r0201", "r0301", "r0302", "r0401", "r0402", "r0403", "r0404", // r0404含有【被告】
                                    "r0501", "r0601", "r0602", "r0603", "r0701" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                    if ("r0404".equals(singleArray)) {
                                    regex = regex.replaceAll("【被告】", PartyWithoutKuoHao);
                                }
                                logger.info("regex= [" + regex +"]  singleResult = [" +  singleResult+"]");
                                if (!regex.contains("***") && Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }

                        {

                            String regexArray[] = { "r0901", "r0902", "r0903", "r0904", "r0905", "r0906", "r0907" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }
                        if (EntNameWithoutKuoHao.equals(PartyWithoutKuoHao)) {
                            String regexArray[] = { "r0908", "r0909", "r0910", "r0911", "r0912" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }
                        if (EntNameWithoutKuoHao.equals(PlaintiffWithoutKuoHao)) {
                            String regexArray[] = { "r0913", "r0914", "r0915", "r0916", "r0917" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }
                        {
                            String regexArray[] = { "r1001", "r1002", "r1003", "r1004", "r1005", "r1006", "r1007",
                                    "r1008", "r1009" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }

                        if (EntNameWithoutKuoHao.equals(PartyWithoutKuoHao)) {
                            String regexArray[] = { "r1010", "r1011", "r1012", "r1013", "r1014", "r1015", "r1016",
                                    "r1017" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }
                        if (EntNameWithoutKuoHao.equals(PlaintiffWithoutKuoHao)) {
                            String regexArray[] = { "r1018", "r1019", "r1020", "r1021", "r1022", "r1023", "r1024",
                                    "r1025" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }

                        {
                            String regexArray[] = { "r1101", "r1102" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }

                        if (EntNameWithoutKuoHao.equals(PartyWithoutKuoHao)) {
                            String regexArray[] = { "r1103", "r1104" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }
                        if (EntNameWithoutKuoHao.equals(PlaintiffWithoutKuoHao)) {
                            String regexArray[] = { "r1105", "r1106" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }

                        {
                            String regexArray[] = { "r1201", "r1202", "r1203" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }
                        if (EntNameWithoutKuoHao.equals(PartyWithoutKuoHao)) {
                            String regexArray[] = { "r1204", "r1205", "r1206" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }

                        if (EntNameWithoutKuoHao.equals(PlaintiffWithoutKuoHao)) {
                            String regexArray[] = { "r1207", "r1208", "r1209" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }

                        String partyArray[] = PartyWithoutKuoHao.split("[、;；,，]");
                        if (CommonUtil.equals(partyArray, EntNameWithoutKuoHao)) {
                            String regexArray[] = { "r1301", "r1401" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }
                        {
                            // 合并文档内多个描述，此处按顺序执行，数组元素次序不可更改
                            String regexArray[] = { "r1501", "r1601", "r1602", "r1701", "r1702", "r1801", "r1802",
                                    "r1901", "r2001", "r2002", "r2003", "r2004", "r2201", "r2202" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
//									logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }
                        {
                            String regexArray[] = new String[1];
                            if (JudgeWithoutKuoHao.contains(EntNameWithoutKuoHao)) {
                                regexArray[0] = "r2601";
                            } else {
                                regexArray[0] = "r2602";// 自添加
                            }
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }
                        {
                            // 合并文档内多个描述，此处按顺序执行，数组元素次序不可更改
                            String regexArray[] = { "r2301", "r2302", "r2303", "r2501", "r2502", "r2503", "r2401",
                                    "r2402", "r2403", "r2404", "r2405", "r2406", "r2101", "r0801", "r0802", "r0803",
                                    "r0101" };
                            for (String singleArray : regexArray) {
                                String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】",
                                        EntNameWithoutKuoHao);
                                if (Pattern.compile(regex).matcher(singleResult).matches()) {
                                    JudgeResultList.add(singleArray);
                                    logger.info("Serialno:{}【审理结果】***【{}】:正则【{}】匹配到子串！！！！！", Serialno, singleResult, singleArray);
                                    continue loop4;
                                }
                            }
                        }

                        {
                            JudgeResultList.add("r0001");// 其他结果
                        }

                    } // 循环结束，获取到所有符合的正则式名称list：JudgeResultList
                    // 根据优先级，查询出优先级最高的审理结果
                    List<Map<String, String>> finalList = jaMapper.judgeResultFinalMap(JudgeResultList);
                    if (finalList == null || finalList.size() == 0) {
                        logger.info("审理结果未获取到取值映射表数据");
                        SetParamSpecial(J4_SLJG, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                    } else {
                        if (finalList.size() == 1) {

                            if("99".equals(finalList.get(0).get("code"))){
                                SetParamSpecial(J4_SLJG, finalList.get(0).get("code"), finalList.get(0).get("description"),"1");
                            }else{
                                SetParamSpecial(J4_SLJG, finalList.get(0).get("code"), finalList.get(0).get("description"),"0");
                            }

                        } else {
                            SetParamSpecial(J4_SLJG, joinResult(finalList, "code"),
                                    joinResult(finalList, "description"), "0");
                        }
                    }
                }
            } else {
                SetParamSpecial(J4_SLJG, ANYNASIS_BLANK, ANYNASIS_BLANK, ANYNASIS_BLANK);
            }
        } catch (Exception e) {
            logger.error("处理审理结果异常", e);
            return null;
            // throw new ServiceException("处理审理结果异常");
        }
        return J4_SLJG;
    }

    //5、审理程序
    private StdLegalDataAdded getJudgementProcedure(StdLegalDataStructured judgeData, String reqID, String uuid)throws ServiceException{
        /** ==========================使用到的安硕原始数据项=========================== */
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno();// 序列号
        String Caseno = judgeData.getCaseNo();// 案号
        // 注意：正则匹配使用清洗了的数据
        String EntName = judgeData.getEntName() == null ? "" : judgeData.getEntName();// 企业名称

        StdLegalDataAdded J5_SLCX = new StdLegalDataAdded();
        SetParamCommon(J5_SLCX, Serialno, "phase", "审理程序", EntName, reqID, uuid);// 设置7项数据
        String[] slcxArr = { "14", "15", "16" };
        try {

            if (CommonUtil.equals(slcxArr, Ptype)) {
                if (CommonUtil.hasChar(Caseno)) {
                    if (Caseno.contains("初")) {
                        J5_SLCX.setLegalValue("一审");
                        J5_SLCX.setNeedingVerify("0");
                        J5_SLCX.setValueLabel("一审");
                    } else if (Caseno.contains("终")) {
                        J5_SLCX.setLegalValue("二审");
                        J5_SLCX.setNeedingVerify("0");
                        J5_SLCX.setValueLabel("二审");
                    } else if (CommonUtil.contains(new String[] { "监", "再", "申", "提", "抗" }, Caseno)) {
                        J5_SLCX.setLegalValue("再审");
                        J5_SLCX.setNeedingVerify("0");
                        J5_SLCX.setValueLabel("再审");
                    } else {
                        J5_SLCX.setLegalValue("其他");
                        J5_SLCX.setNeedingVerify("1");
                        J5_SLCX.setValueLabel("其他");
                    }

                } else {
                    J5_SLCX.setLegalValue(ANYNASIS_BLANK);
                    J5_SLCX.setValueLabel(ANYNASIS_BLANK);
                    J5_SLCX.setNeedingVerify("0");
                }

            } else {
                J5_SLCX.setLegalValue(ANYNASIS_BLANK);
                J5_SLCX.setValueLabel(ANYNASIS_BLANK);
                J5_SLCX.setNeedingVerify(ANYNASIS_BLANK);
            }
        } catch (Exception e) {
            logger.error("处理审理程序异常", e);
            // throw new ServiceException("处理审理程序异常");
            return null;
        }
        return J5_SLCX;
    }

    //6、法院等级
    private StdLegalDataAdded getCourtLevel(StdLegalDataStructured judgeData, String reqID, String uuid) throws ServiceException{

        /** ==========================使用到的安硕原始数据项=========================== */
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno();// 序列号
        String Court = judgeData.getCourt();// 法院

        // 注意：正则匹配使用清洗了的数据
        String EntName = judgeData.getEntName() == null ? "" : judgeData.getEntName();// 企业名称
        StdLegalDataAdded J6_FYDJ = new StdLegalDataAdded();
        SetParamCommon(J6_FYDJ, Serialno, "courtLevel", "法院等级", EntName, reqID, uuid);// 设置7项数据
        String[] fydjArr = { "14", "15", "16" };
        try {

            if (CommonUtil.equals(fydjArr, Ptype)) {
                if (!CommonUtil.hasChar(Court)) {
                    J6_FYDJ.setLegalValue(ANYNASIS_BLANK);
                    J6_FYDJ.setValueLabel(ANYNASIS_BLANK);
                    J6_FYDJ.setNeedingVerify("0");
                } else if (Court.contains("最高")) {
                    J6_FYDJ.setLegalValue("4");
                    J6_FYDJ.setValueLabel("最高");
                    J6_FYDJ.setNeedingVerify("0");
                } else if (Court.contains("高级")) {
                    J6_FYDJ.setLegalValue("3");
                    J6_FYDJ.setValueLabel("高级");
                    J6_FYDJ.setNeedingVerify("0");
                } else if (Court.contains("中级")) {
                    J6_FYDJ.setLegalValue("2");
                    J6_FYDJ.setValueLabel("中级");
                    J6_FYDJ.setNeedingVerify("0");
                } else if (CommonUtil.contains(new String[] { "区", "县", "市" }, Court)) {
                    J6_FYDJ.setLegalValue("1");
                    J6_FYDJ.setValueLabel("基层");
                    J6_FYDJ.setNeedingVerify("0");
                } else {
                    J6_FYDJ.setLegalValue("99");
                    J6_FYDJ.setValueLabel("其他");
                    J6_FYDJ.setNeedingVerify("1");
                }

            } else {
                J6_FYDJ.setLegalValue(ANYNASIS_BLANK);
                J6_FYDJ.setValueLabel(ANYNASIS_BLANK);
                J6_FYDJ.setNeedingVerify(ANYNASIS_BLANK);

            }

        } catch (Exception e) {
            logger.error("处理法院等级异常", e);
            // throw new ServiceException("处理法院等级异常");
            return null;
        }
        return J6_FYDJ;
    }

    //7、案件影响力
    private StdLegalDataAdded getCaseInfluence(StdLegalDataStructured judgeData,
                                               StdLegalDataAdded J6_FYDJ,
                                               StdLegalDataAdded J5_SLCX,
                                            String reqID, String uuid)throws ServiceException{

        /** ==========================使用到的安硕原始数据项=========================== */
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno();// 序列号
        // 注意：正则匹配使用清洗了的数据
        String EntName = judgeData.getEntName() == null ? "" : judgeData.getEntName();// 企业名称


        StdLegalDataAdded J7_AJYXL = new StdLegalDataAdded();
        SetParamCommon(J7_AJYXL, Serialno, "caseImpact", "案件影响力", EntName, reqID, uuid);// 设置7项数据
        String[] ajyxlArr = { "14", "15", "16" };
        try {

            if (CommonUtil.equals(ajyxlArr, Ptype)) {
                String CourtLevel = J6_FYDJ.getLegalValue();
                String Phase = J5_SLCX.getLegalValue();
                if (CourtLevel == ANYNASIS_BLANK || Phase == ANYNASIS_BLANK) {
                    J7_AJYXL.setLegalValue(ANYNASIS_BLANK);
                    J7_AJYXL.setValueLabel(ANYNASIS_BLANK);
                    J7_AJYXL.setNeedingVerify("1");
                } else {
                    if (CourtLevel.equals("4") && Phase.equals("一审")) {
                        J7_AJYXL.setLegalValue("4");
                        J7_AJYXL.setValueLabel("4");
                        J7_AJYXL.setNeedingVerify("0");
                    } else if (CourtLevel.equals("3") && Phase.equals("一审")) {
                        J7_AJYXL.setLegalValue("3");
                        J7_AJYXL.setValueLabel("3");
                        J7_AJYXL.setNeedingVerify("0");
                    } else if (CourtLevel.equals("2") && Phase.equals("一审")) {
                        J7_AJYXL.setLegalValue("2");
                        J7_AJYXL.setValueLabel("2");
                        J7_AJYXL.setNeedingVerify("0");
                    } else if (CourtLevel.equals("1") && Phase.equals("一审")) {
                        J7_AJYXL.setLegalValue("1");
                        J7_AJYXL.setValueLabel("1");
                        J7_AJYXL.setNeedingVerify("0");
                    } else {
                        J7_AJYXL.setLegalValue("0");
                        J7_AJYXL.setValueLabel("0");
                        J7_AJYXL.setNeedingVerify("1");
                    }
                }
            } else {
                J7_AJYXL.setLegalValue(ANYNASIS_BLANK);
                J7_AJYXL.setValueLabel(ANYNASIS_BLANK);
                J7_AJYXL.setNeedingVerify(ANYNASIS_BLANK);
            }
        } catch (Exception e) {
            logger.error("处理案件影响力异常", e);
            // throw new ServiceException("处理案件影响力异常");
            return null;
        }
        return J7_AJYXL;
    }


    //8、案件结果对客户的影响
    private StdLegalDataAdded getImpactCaseResultOnCustomer(StdLegalDataStructured judgeData,
                                                            StdLegalDataAdded J4_SLJG,
                                                         String reqID, String uuid)throws ServiceException{
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno();// 序列号

        // 注意：正则匹配使用清洗了的数据
        String PlaintiffWithoutKuoHao = clearContent(judgeData.getPlaintiff());// 原告:清除各种括号及括号内的内容,以及例外正则式
        String PartyWithoutKuoHao = clearContentSpecialFields(judgeData.getParty());// 被告:清除各种括号及括号内的内容
        String EntName = judgeData.getEntName() == null ? "" : judgeData.getEntName();// 企业名称
        String EntNameWithoutKuoHao = clearContent(EntName);// 企业名称:清除各种括号的EntName//正则运算中，与清洗了结构一致，不带括号等等

        StdLegalDataAdded J8_AJJGDKHYX = new StdLegalDataAdded();
        SetParamCommon(J8_AJJGDKHYX, Serialno, "sentenceEffect", "案件结果对客户的影响", EntName, reqID, uuid);// 设置7项数据
        String[] ajdkeyxArr = { "16" };
        try {

            if (CommonUtil.equals(ajdkeyxArr, Ptype)) {
                String SentenceBrief = J4_SLJG.getLegalValue();
                if (SentenceBrief == ANYNASIS_BLANK) {
                    J8_AJJGDKHYX.setLegalValue(ANYNASIS_BLANK);
                    J8_AJJGDKHYX.setValueLabel(ANYNASIS_BLANK);
                    J8_AJJGDKHYX.setNeedingVerify("0");

                } else if (SentenceBrief != null && !SentenceBrief.contains("、")) {
                    Map<String, Object> paramMap = new HashMap<>();
                    if (impactOfResultUtil(PlaintiffWithoutKuoHao, EntNameWithoutKuoHao)) {
                        paramMap.put("role", "原告");
                    } else if (impactOfResultUtil(PartyWithoutKuoHao, EntNameWithoutKuoHao)) {
                        paramMap.put("role", "被告");
                    } else {
                        paramMap.put("role", "其他");
                    }
                    paramMap.put("resultCode", SentenceBrief);
                    Map<String, Object> resultMap = jaMapper.resultImpact(paramMap);
                    if (resultMap != null) {
                        J8_AJJGDKHYX.setValueLabel((String) resultMap.get("impact"));
                        String code = (String) resultMap.get("code");
                        J8_AJJGDKHYX.setLegalValue(code);
                        if ("99".equals(code)) {
                            J8_AJJGDKHYX.setNeedingVerify("1");
                            J8_AJJGDKHYX.setValueLabel("无法确定");
                        } else {
                            J8_AJJGDKHYX.setNeedingVerify("0");
                        }
                    } else {
                        logger.info("***************“EXCEPTION：数据库没有对应的”案件结果对客户的影响“**************");
                        J8_AJJGDKHYX.setLegalValue(ANYNASIS_BLANK);
                        J8_AJJGDKHYX.setValueLabel(ANYNASIS_BLANK);
                        J8_AJJGDKHYX.setNeedingVerify(ANYNASIS_BLANK);
                    }
                } else {
                    J8_AJJGDKHYX.setLegalValue("99");
                    J8_AJJGDKHYX.setValueLabel("无法确定");
                    J8_AJJGDKHYX.setNeedingVerify("1");
                }

            } else {
                J8_AJJGDKHYX.setLegalValue(ANYNASIS_BLANK);
                J8_AJJGDKHYX.setValueLabel(ANYNASIS_BLANK);
                J8_AJJGDKHYX.setNeedingVerify(ANYNASIS_BLANK);
            }

        } catch (Exception e) {
            // throw new ServiceException("处理案件结果对客户的影响异常:" + e);
            logger.error("处理案件结果对客户的影响异常:",e);
            return null;
        }
        return J8_AJJGDKHYX;


    }

    //9、客户诉讼角色准确性校验
    private StdLegalDataAdded getCustomerRoleCheck(StdLegalDataStructured judgeData, String reqID, String uuid)throws ServiceException{
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno();// 序列号

        // 注意：正则匹配使用清洗了的数据
        String PlaintiffWithoutKuoHao = clearContent(judgeData.getPlaintiff());// 原告:清除各种括号及括号内的内容,以及例外正则式
        String PartyWithoutKuoHao = clearContentSpecialFields(judgeData.getParty());// 被告:清除各种括号及括号内的内容
        String EntName = judgeData.getEntName() == null ? "" : judgeData.getEntName();// 企业名称
        String EntNameWithoutKuoHao = clearContent(EntName);// 企业名称:清除各种括号的EntName//正则运算中，与清洗了结构一致，不带括号等等
        String Pdesc = judgeData.getPdesc() == null ? "" : judgeData.getPdesc();// 公告内容
        String PdescWithoutKuoHao = clearContent(Pdesc);// 清除各种括号的公告内容
        String Importstaff = clearContent(judgeData.getImportStaff());// 重要第三方

        StdLegalDataAdded J9_KHSSJSZQXJY = new StdLegalDataAdded();
        try {
            SetParamCommon(J9_KHSSJSZQXJY, Serialno, "rolesVerify", "客户诉讼角色准确性校验", EntName, reqID, uuid);// 设置7项数据
            String[] khssjszqxjyArr = { "14", "15", "16", "17", "18", "22" };

            if (CommonUtil.equals(khssjszqxjyArr, Ptype)) {

                if (CommonUtil.hasChar(Pdesc)) {
                    // 三类正则式列表
                    String regex_BG[] = null;// 被告正则式
                    String regex_YG[] = null;// 原告正则式
                    String regex_ZYDSF[] = null;// 重要第三方正则式
                    if ("16".equals(Ptype)) {
                        regex_BG = new String[] { "r10" };
                        regex_YG = new String[] { "r20" };
                        regex_ZYDSF = new String[] { "r30" };

                    } else if (CommonUtil.equals(new String[] { "15", "18" }, Ptype)) {
                        regex_BG = new String[] { "r11", "r12", "r13", "r14" };
                        regex_YG = new String[] { "r21", "r22" };
                        regex_ZYDSF = new String[] { "r30" };

                    } else if (CommonUtil.equals(new String[] { "14", "17", "22" }, Ptype)) {
                        regex_BG = new String[] { "r11" };
                        regex_YG = new String[] { "r21" };
                        regex_ZYDSF = new String[] { "r30" };
                    }
                    /*** 规则开始 ****/
                    int count = inStrCount(EntNameWithoutKuoHao,
                            new String[] { PlaintiffWithoutKuoHao, PartyWithoutKuoHao, Importstaff });
                    if (count == 0) {
                        J9_KHSSJSZQXJY.setLegalValue("2");
                    } else if (count >= 2) {
                        J9_KHSSJSZQXJY.setLegalValue("3");
                    } else if (CommonUtil.inStr(EntNameWithoutKuoHao, PartyWithoutKuoHao) && regex_BG != null) {// 被告包含企业名称
                        for (String BG : regex_BG) {
                            String regex = REGEXMAP_ROLE.get(BG).replace("【企业名称】", EntNameWithoutKuoHao);
                            if (Pattern.matches(regex, PdescWithoutKuoHao)) {

                                J9_KHSSJSZQXJY.setLegalValue("0");
                                break;
                            } else {
                                J9_KHSSJSZQXJY.setLegalValue("1");
                            }
                        }
                    } else if (CommonUtil.inStr(EntNameWithoutKuoHao, PlaintiffWithoutKuoHao) && regex_YG != null) {// 原告包含企业名称
                        for (String YG : regex_YG) {
                            String regex = REGEXMAP_ROLE.get(YG).replace("【企业名称】", EntNameWithoutKuoHao);
                            if (Pattern.matches(regex, PdescWithoutKuoHao)) {
                                J9_KHSSJSZQXJY.setLegalValue("0");
                                break;
                            } else {
                                J9_KHSSJSZQXJY.setLegalValue("1");
                            }
                        }
                    } else if (CommonUtil.inStr(EntNameWithoutKuoHao, Importstaff) && regex_ZYDSF != null) {// 重要第三方包含企业名称
                        for (String ZYDSF : regex_ZYDSF) {
                            String regex = REGEXMAP_ROLE.get(ZYDSF).replace("【企业名称】", EntNameWithoutKuoHao);
                            if (Pattern.matches(regex, PdescWithoutKuoHao)) {

                                J9_KHSSJSZQXJY.setLegalValue("0");
                                break;
                            } else {
                                J9_KHSSJSZQXJY.setLegalValue("1");
                            }
                        }
                    }
                    /*** 规则结束 ****/
                    // 匹配对应的valueLabel和needingVerify
                    String value = J9_KHSSJSZQXJY.getLegalValue();
                    String valueLabel = "";
                    try {
                        valueLabel =  jaMapper.JudgeRoleCheckMapping(value);
                    }catch (Exception e){
                    }

                    J9_KHSSJSZQXJY.setValueLabel(valueLabel);
                    if ("0".equals(value)) {
                        J9_KHSSJSZQXJY.setNeedingVerify("0");
                    } else {
                        J9_KHSSJSZQXJY.setNeedingVerify("1");
                    }

                } else {
                    SetParamSpecial(J9_KHSSJSZQXJY, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                }
            } else {
                J9_KHSSJSZQXJY.setLegalValue(ANYNASIS_BLANK);
                J9_KHSSJSZQXJY.setValueLabel(ANYNASIS_BLANK);
                J9_KHSSJSZQXJY.setNeedingVerify(ANYNASIS_BLANK);
            }
        } catch (Exception e) {
            logger.error("客户诉讼角色准确性校验", e);
            // throw new ServiceException("客户诉讼角色准确性校验");
            return null;
        }
        return J9_KHSSJSZQXJY;

    }


    /**
     *
     * @description:设置统一的字段
     * @Autor: ZZX
     * @time:2017-7-26 下午4:02:28
     */
    public void SetParamCommon(StdLegalDataAdded legalDataAdded, String serialno, String VarName, String VarLabel,
                               String EntName, String reqId, String uuid) {

        legalDataAdded.setReqId(reqId);
        //legalDataAdded.setUuid(uuid);
        legalDataAdded.setEntName(EntName);
        legalDataAdded.setSerialNo(serialno);
        legalDataAdded.setVarName(VarName);
        legalDataAdded.setVarLabel(VarLabel);
    }

    /**
     *
     * @description:涉案金额：内容替换
     * @Autor: ZZX
     * @param result
     * @return
     * @time:2017-7-28 上午9:21:41
     */
    public static String clearContent(String result) {

        if (result == null || result.equals("null")) {
            return "";
        }

        if (!CommonUtil.hasChar(result)) {
            return "";
        }

        // 去空白
        result = result.replaceAll("\\s", "");// 匹配任何空白字符，包括空格、制表符、换页符等。与 [
        // \f\n\r\t\v] 等效。

        List<char[]> list = new ArrayList<char[]>() {
            private static final long serialVersionUID = 1L;

            {
                // 英文
                add(new char[] { '(', ')' });
                add(new char[] { '[', ']' });
                // add(new char[]{'{','}'});//防止清除必要内容
                // add(new char[]{'<','>'});//防止是大于小于号
                // 中文
                add(new char[] { '（', '）' });
                add(new char[] { '【', '】' });
                // add(new char[]{'{','}'});//防止清除必要内容
                add(new char[] { '《', '》' });
            }
        };
        String regex = "";
        for (char[] cs : list) {
            regex = "\\char1[^\\char1\\char2]*\\char2";
            regex = regex.replaceAll("char1", String.valueOf(cs[0]));
            regex = regex.replaceAll("char2", String.valueOf(cs[1]));

            // 循环清除对应匹配项
            while (Pattern.compile(regex).matcher(result).find()) {
                result = result.replaceAll(regex, "");
            }
        }
        // 例外不取的正则式--优化，遇到长数字速度奇慢,我们采取先去除无关数字再清除该正则的方式
        // String regexExcept = "(?:\\d*[,，]*)*\\d+\\.?\\d*万*元为(?:基数|本金)";
        // result = result.replaceAll(regexExcept, "");
        return result;
    }

    //针对party字段单边括号处理方法
    public static String clearContentSpecialFields(String result) {

        if (result == null || result == "null") {
            return "";
        }

        if (!CommonUtil.hasChar(result)) {
            return "";
        }
        // 去空白
        result = result.replaceAll("\\s", "").replace("?", "").replace("？", "");// 匹配任何空白字符，包括空格、制表符、换页符等。与 [
        // \f\n\r\t\v] 等效。

        List<char[]> list = new ArrayList<char[]>() {
            private static final long serialVersionUID = 1L;

            {
                // 对称符号处理
                //英文
                add(new char[] { '(', ')' });
                add(new char[] { '[', ']' });
                // 中文
                add(new char[] { '（', '）' });
                add(new char[] { '【', '】' });
                add(new char[] { '《', '》' });

            }
        };
        String regex = "";
        for (char[] cs : list) {
            regex = "\\char1[^\\char1\\char2]*\\char2";
            regex = regex.replaceAll("char1", String.valueOf(cs[0]));
            regex = regex.replaceAll("char2", String.valueOf(cs[1]));

            while (Pattern.compile(regex).matcher(result).find()) {
                result = result.replaceAll(regex, "");
            }
        }
        //去除单边括号
        result = result.replaceAll("\\(", ",").replaceAll("\\（",",")
                .replaceAll("\\)", ",").replaceAll("\\）",",")
                .replaceAll("\\[", ",").replaceAll("\\【",",")
                .replaceAll("\\]", ",").replaceAll("\\】",",")
                .replaceAll("\\《", ",").replaceAll("\\<",",")
                .replaceAll("\\》", ",").replaceAll("\\>",",");
        return result;
    }

    /**
     *
     * @description:设置每种新增字段各自的值（非统一）
     * @Autor: ZZX
     * @param legalDataAdded
     * @param value
     * @param valuelabel
     * @param flag
     * @time:2017-7-27 下午10:57:12
     */
    public void SetParamSpecial(StdLegalDataAdded legalDataAdded, String value, String valuelabel, String flag) {
        legalDataAdded.setLegalValue(value);
        legalDataAdded.setValueLabel(valuelabel);
        legalDataAdded.setNeedingVerify(flag);
    }

    /**
     *
     * @description:字符串中是否包含数字
     * @Autor: ZZX
     * @param s
     * @return
     * @time:2017-7-28 上午12:19:45
     */
    public static boolean containsNumber(String s) {

        if (s == null || s.equals("")) {
            return Boolean.FALSE;
        }

        if (CommonUtil.hasChar(s)) {
            return NUMPATTERN.matcher(s).find();
        }

        return Boolean.FALSE;

    }

    /**
     *
     * @description:涉案金额--判决结果 = 清除例外不取的正则表达式
     * @Autor: ZZX
     * @param result
     * @return
     * @time:2017-10-17 下午3:20:30
     */
    private String removeExpectNumAndReg(String result) {
        // 2个正则清除顺序不可乱
        String badNum = "([0-9\\.,，]+)(?!([0-9\\.,，]+|([零一二三四五六七八九十百千万亿]*(元|美元|港元|澳门元|日元|韩元|缅元|马元|新加坡元|欧元|英镑|马克|法郎|卢布|加元|新西兰元|澳元|澳大利亚元))))";
        String badReg = "(?:\\d*[,，]*)*\\d+\\.?\\d*万*元为(?:基数|本金)";// 清除无关金额长数字
        result = result.replaceAll(badNum, "*").replaceAll(badReg, "#");// 清除无关金额
        return result;
    }

    /**
     *
     * @description:分解金额和单位
     * @Autor: ZZX
     * @param moneyUnit：子模式，即：子模式
     *            = "金额数值+金额单位"的形式（如"2000美元"）
     * @return string[0] 金额； string[1] 单位
     * @time:2017-7-29 上午5:47:09
     */
    public static String[] MoneyAndUnit(String moneyUnit) {

        String moneyAndUnit[] = new String[2];
        String regex = "(" + SAJE_TARGET + ")(" + SAJE_UNIT + ")";// (金额)(单位)格式
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(moneyUnit);
        if (matcher.matches()) {// ((?:\d*[,，]*)*\d*\.?\d*[零一二三四五六七八九十百千万亿]*)(元|美元|港元|澳门元|日元|韩元|缅元|马元|新加坡元|欧元|英镑|马克|法郎|卢布|加元|新西兰元|澳元|澳大利亚元)
            moneyAndUnit[0] = matcher.group(1);// 金额
            moneyAndUnit[1] = matcher.group(2);// 单位
        }
        // 处理金额格式：全部转化为数字（无千分位）
            moneyAndUnit[0] = CNNMFilter.getAllNumber(moneyAndUnit[0]);
        return moneyAndUnit;
    }

    /**
     *
     * @description:拼接审理结果
     * @Autor: ZZX
     * @param list
     * @param code
     * @throws Exception
     * @time:2017-10-23 下午7:21:10
     */
    public static String joinResult(List<Map<String, String>> list, String code) {

        StringBuffer s = new StringBuffer();
        for (int i = 0, leng = list.size(); i < leng; i++) {
            s.append(list.get(i).get(code));
            if (i != leng - 1) {
                s.append("、");
            }
        }
        return s.toString();

    }

    /**
     *
     * @description:
     * @Autor: ZZX
     * @param column：Plaintiff原告
     *            Party被告
     * @param EntName:企业名称
     * @return
     * @time:2017-7-27 下午7:51:20
     */
    public static boolean impactOfResultUtil(String column, String EntName) {

        boolean flag = Boolean.FALSE;
        if (StringUtils.isEmpty(EntName) || StringUtils.isEmpty(column)) {
            return flag;
        }

        String arr[] = column.split("[、，,;；]");// \ 和 、全角半角
        if (arr.length > 0) {
            if (CommonUtil.equals(arr, EntName)) {
                flag = Boolean.TRUE;
            }

        }
        return flag;

    }

    /**
     *
     * @description:Sor数组中含有test字段的对象个数
     * @Autor: ZZX
     * @param test
     * @param sor
     * @return
     * @time:2017-10-25 下午3:45:48
     */
    public static int inStrCount(String test, String sor[]) {

        int count = 0;
        if (StringUtils.isEmpty(test) || sor == null || sor.length == 0) {
            return count;
        }

        for (String ss : sor) {
            if (ss.contains(test)) {
                count++;
            }
        }
        return count;
    }


}
