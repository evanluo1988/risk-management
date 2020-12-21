package com.springboot.service;

import com.springboot.domain.risk.StdLegalDataAdded;
import com.springboot.domain.risk.StdLegalDataStructured;

import java.util.List;
import java.util.Map;

public interface LegalDataAddColumnService {
    /**
     * 初始化解析引擎
     * @return
     */
    public void initAnalysisJudicialEngine();

    /**
     * 生成StdLegalDataAdded
     * @param judgeData
     * @param reqID
     * @param uuid
     * @return
     */
    public List<StdLegalDataAdded> anynasisAndInsert(StdLegalDataStructured judgeData, String reqID, String uuid) throws Exception;
}
