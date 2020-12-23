package com.springboot.component;

import com.springboot.mapper.StdLegalDataStructuredTempMapper;
import com.springboot.model.LitigaInitiativeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 涉诉主动性
 */
@Component("LitigaInitiative")
public class LitigaInitiative implements QuotaComponent{
    @Autowired
    private StdLegalDataStructuredTempMapper stdLegalDataStructuredTempMapper;

    @Override
    public String execQuota(String reqId) {
        List<LitigaInitiativeModel> litigaInitiativeModelList = stdLegalDataStructuredTempMapper.getLitigaInitiativeResult(reqId);
        if(CollectionUtils.isEmpty(litigaInitiativeModelList)) {
            return null;
        }
        List<LitigaInitiativeModel> plaintiff = litigaInitiativeModelList.stream().filter(item -> item.getPlaintiff() != null).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(plaintiff)) {
            return "else";
        }
        float m = (float) plaintiff.size()/litigaInitiativeModelList.size();
        return m > 0.5 ? "else" : "偏被动";
    }
}
