package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.StdIaBrand;
import com.springboot.vo.risk.BrandVarietyVo;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/29 11:08
 * @Version 1.0
 */
public interface StdIaBrandService extends IService<StdIaBrand> {

    /**
     * 根据ReqId查询
     * @param reqId
     * @return
     */
    List<StdIaBrand> findByReqId(String reqId);

    /**
     * 商标种类和数量
     */
    List<BrandVarietyVo> getBrandVariety(String reqId, boolean valid);
}
