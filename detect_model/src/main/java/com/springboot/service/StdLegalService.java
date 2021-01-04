package com.springboot.service;

import com.springboot.vo.risk.LitigaCaseVo;

import java.util.List;

public interface StdLegalService {
    /**
     * 生成司法中间表
     * @param reqId
     */
    public void createStdLegalMidTable(String reqId);

    /**
     * 预处理标准司法数据
     * @param reqId
     */
    public void preStdSsDatas(String reqId);

    /**
     * 查询pdf所需要的
     * @param reqId
     * @return
     */
    List<LitigaCaseVo> getLitigaCase(String reqId);
}
