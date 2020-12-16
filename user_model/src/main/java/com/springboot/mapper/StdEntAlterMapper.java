package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.domain.risk.StdEntAlter;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 15:48
 * @Version 1.0
 */
public interface StdEntAlterMapper extends BaseMapper<StdEntAlter> {
    public List<StdEntAlter> findInvestorWithdrawList(String reqId);
}
