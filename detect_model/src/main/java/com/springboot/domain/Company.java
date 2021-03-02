package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lhf
 * @date 2021/3/1 3:31 下午
 **/
@Data
@TableName("companies")
public class Company extends BaseDomain{
    /**
     * 编号
     */
    private String no;
    /**
     * 企业名称
     */
    private String entName;
    /**
     * 信用代码
     */
    private String creditCode;
    /**
     * 注册年月
     */
    private String regDate;
    /**
     * 区
     */
    private String zone;
    /**
     * 街道
     */
    private String street;
    /**
     * 注册地址
     */
    private String regAddress;
    /**
     * 高新区
     */
    private String highTechZone;
    /**
     * 经度
     */
    private String lon;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 最新数据reqId
     */
    private String reqId;
}
