package com.springboot.component;

import com.springboot.mapper.StdLegalDataStructuredTempMapper;
import com.springboot.model.VerdictResultModel;
import com.springboot.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 判决结果倾向
 */
@Component("TendencyJudgResult")
public class TendencyJudgResult implements QuotaComponent{
    @Autowired
    private StdLegalDataStructuredTempMapper stdLegalDataStructuredTempMapper;

    @Override
    public String execQuota(String reqId) {
        List<VerdictResultModel> verdictResultModelList = stdLegalDataStructuredTempMapper.getVerdictResult(reqId);
        if(CollectionUtils.isEmpty(verdictResultModelList)) {
            return null;
        }
        Map<String,List<VerdictResultModel>> verdictResultModelMap = Utils.getList(verdictResultModelList).stream().collect(Collectors.groupingBy(VerdictResultModel::getSentenceEffect));
        int count1 = Utils.getList(verdictResultModelMap.get("99")).size();
        int count2 = Utils.getList(verdictResultModelMap.get("0")).size();
        int count3 = Utils.getList(verdictResultModelMap.get("1")).size();

        if(count3 > count2 && count3 > count1) {
            return "败诉";
        }
        return "else";
    }
}
