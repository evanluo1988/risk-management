package com.springboot.component;

import com.google.common.collect.Maps;
import com.springboot.domain.risk.StdLegalDataStructuredTemp;
import com.springboot.domain.risk.StdLegalEntUnexecutedTemp;
import com.springboot.domain.risk.StdLegalEnterpriseExecutedTemp;
import com.springboot.mapper.StdLegalDataStructuredTempMapper;
import com.springboot.mapper.StdLegalEntUnexecutedTempMapper;
import com.springboot.mapper.StdLegalEnterpriseExecutedTempMapper;
import com.springboot.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 涉诉频率
 */
@Component("LitigaFrequency")
public class LitigaFrequency implements QuotaComponent{

    private static final String REG_PATTERN = "(（\\d{4}）)";

    @Autowired
    private StdLegalDataStructuredTempMapper stdLegalDataStructuredTempMapper;
    @Autowired
    private StdLegalEnterpriseExecutedTempMapper stdLegalEnterpriseExecutedTempMapper;
    @Autowired
    private StdLegalEntUnexecutedTempMapper stdLegalEntUnexecutedTempMapper;

    @Override
    public String execQuota(String reqId) {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("req_id", reqId);
        List<StdLegalDataStructuredTemp> structuredList = stdLegalDataStructuredTempMapper.selectByMap(paramMap);
        List<StdLegalEnterpriseExecutedTemp> enterpriseExecutedList = stdLegalEnterpriseExecutedTempMapper.selectByMap(paramMap);
        List<StdLegalEntUnexecutedTemp> entUnexecutedList = stdLegalEntUnexecutedTempMapper.selectByMap(paramMap);

        //<案件年份，案件数量>
        Map<Integer, Integer> caseMap = Maps.newHashMap();
        for(StdLegalDataStructuredTemp structured : Utils.getList(structuredList)) {
            initCaseMap(structured.getCaseDate(), structured.getPdate(), structured.getCaseNo(), caseMap);
        }
        for(StdLegalEnterpriseExecutedTemp enterpriseExecuted: Utils.getList(enterpriseExecutedList)) {
            initCaseMap(enterpriseExecuted.getCaseCreateTime(), null, enterpriseExecuted.getCaseCode(), caseMap);
        }
        for(StdLegalEntUnexecutedTemp entUnexecuted : Utils.getList(entUnexecutedList)) {
            initCaseMap(entUnexecuted.getRegDate(), entUnexecuted.getPublishDate(), entUnexecuted.getCaseCode(), caseMap);
        }
        //获取当前时间
        LocalDate dateNow = LocalDate.now();
        int yearNow = dateNow.getYear();
        int monthNow = dateNow.getMonthValue();
        if(monthNow > 6){
            if(caseMap.get(yearNow) != null &&
                    caseMap.get(yearNow-1) != null && caseMap.get(yearNow-2) != null) {
                return "频繁";
            }else{
                return "偶尔";
            }
        }else{
            if(caseMap.get(yearNow) != null && caseMap.get(yearNow-1) != null
                    && caseMap.get(yearNow-2) != null && caseMap.get(yearNow-3) != null) {
                return "频繁";
            }else{
                return "偶尔";
            }
        }
    }

    private void initCaseMap(LocalDate caseDate, LocalDate pdate, String caseno, Map<Integer, Integer> caseMap) {
        Integer caseYear = getCaseDate(caseDate, pdate, caseno);
        if(caseYear != null) {
            if(caseMap.containsKey(caseYear)) {
                caseMap.put(caseYear, caseMap.get(caseYear)+1);
            }else{
                caseMap.put(caseYear, 1);
            }
        }
    }

    /**
     * 获取案件年份
     * 若【立案时间】为空，则取【发布时间】；若【发布时间】为空或无该字段，则取从【案号】解析出的年份；若【案号】为空，则该案件的【案件年份】视为空。
     * @return
     */
    private Integer getCaseDate(LocalDate caseDate, LocalDate pdate, String caseno) {
        if(caseDate != null) {
            return caseDate.getYear();
        }
        if(pdate != null) {
            return pdate.getYear();
        }
        if(caseno != null) {
            Pattern p = Pattern.compile(REG_PATTERN);
            Matcher m = p.matcher(caseno);
            if (m.find()) {
                String year = m.group(0).trim();
                return Integer.valueOf(year.substring(1, year.length() -1));
            } else {
                return null;
            }
        }
        return null;
    }

}
