package com.springboot.service;

import com.springboot.domain.StdLegalDataAdded;
import com.springboot.domain.StdLegalDataStructured;

import java.util.List;

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
