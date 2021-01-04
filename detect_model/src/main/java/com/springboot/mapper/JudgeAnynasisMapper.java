package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.domain.risk.StdLegalDataAdded;

import java.util.List;
import java.util.Map;

/**
 *
 * @description: 司法诉讼解析mapper
 * @author: ZZX
 * @date: 2017-7-26 下午8:04:14
 * @fileName:com.v.eds.mapper.JudgeAnynasisMapper.java
 */
public interface JudgeAnynasisMapper extends BaseMapper<StdLegalDataAdded> {
    /**
     * @description:司法诉讼新增字段入库
     */
    //public int insertJudgeNewAddedColumn(List<StdLegalDataAdded> list);

    /**
     *
     * @description:通过案由或者案号获取对应 取值代码和取值文字描述
     * @Autor: ZZX
     * @param caseInfo
     * @return
     * @time:2017-7-27 上午11:26:10
     */
    public Map<String,Object> caseReason(String caseInfo);


    /**
     *
     * @description:案由分类A表
     * @Autor: ZZX
     * @return
     * @time:2017-7-26 下午4:25:54
     */
    public List<Map<String,Object>> getAYFLTabA();

    /**
     *
     * @description:案由分类B表
     * @Autor: ZZX
     * @return
     * @time:2017-7-26 下午4:26:04
     */
    public List<Map<String,Object>> getAYFLTabB();

    /**
     *
     * @description:案件结果对客户影响
     * @Autor: ZZX
     * @param map
     * @return
     * @time:2017-7-26 下午11:00:30
     */
    public Map<String,Object> resultImpact(Map<String,Object> map);


    /**
     *
     * @description:涉案金额 正则基础配置
     * @Autor: ZZX
     * @return
     * @time:2017-7-28 下午3:21:05
     */
    public List<Map<String,Object>> CaseMoneyBasic();

    /**
     *
     * @description:涉案金额 正则匹配式
     * @Autor: ZZX
     * @return
     * @time:2017-7-28 下午3:21:39
     */
    public List<Map<String,Object>> CaseMoneyRegex();

    /**
     * @description:审理结果 正则基础表
     */
    public List<Map<String, Object>> JudgeResultBasic();


    /**
     * @description:审理结果 正则匹配式
     */
    public List<Map<String, Object>> JudgeResultRegex();


    /**
     * @description:根据优先级获取除优先级最高的审理结果
     */
    public List<Map<String,String>> judgeResultFinalMap(List<String> JudgeResultList);

    /**
     * @description:根据参数名获取对应的参数
     */
    public String getEDSParamByName(String name);


    /**
     * @description:客户角色准确性校验  正则匹配式
     */
    public List<Map<String, String>> JudgeRoleCheckRegex();

    /**
     * @description:客户角色准确性校验  正则基础表
     */
    public List<Map<String, Object>> JudgeRoleCheckBasic();

    /**
     * @description:客户角色准确性校验  结果映射表
     */
    public String JudgeRoleCheckMapping(String value);
}

