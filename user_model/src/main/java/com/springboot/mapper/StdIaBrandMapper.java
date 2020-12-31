package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.domain.risk.StdIaBrand;
import com.springboot.vo.risk.BrandVarietyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author 刘宏飞
 * @Date 2020/12/29 11:07
 * @Version 1.0
 */
public interface StdIaBrandMapper extends BaseMapper<StdIaBrand> {
    public List<BrandVarietyVo> findBrandVarietyList(@Param("reqId")String reqId, @Param("valid")boolean valid, @Param("entname")String entName);
}
