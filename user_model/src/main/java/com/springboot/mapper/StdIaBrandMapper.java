package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.domain.risk.StdIaBrand;
import com.springboot.vo.risk.BrandVarietyVo;

import java.util.List;
import java.util.Map;

/**
 * @Author 刘宏飞
 * @Date 2020/12/29 11:07
 * @Version 1.0
 */
public interface StdIaBrandMapper extends BaseMapper<StdIaBrand> {
    public List<BrandVarietyVo> findBrandVarietyList(Map map);
}
