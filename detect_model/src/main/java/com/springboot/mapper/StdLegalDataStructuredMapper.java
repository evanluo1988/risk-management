package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.domain.StdLegalDataStructured;

import java.util.Map;

public interface StdLegalDataStructuredMapper extends BaseMapper<StdLegalDataStructured> {
    public Map getvarname(Map map);
}
