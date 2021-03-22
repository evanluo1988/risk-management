package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.CompanyScore;

/**
 * @author lhf
 * @date 2021/3/3 4:48 下午
 **/
public interface CompanyScoreService extends IService<CompanyScore> {
    /**
     * 根据REQID获取得分
     * @param reqId
     * @return
     */
    CompanyScore getByReqId(String reqId);
}
