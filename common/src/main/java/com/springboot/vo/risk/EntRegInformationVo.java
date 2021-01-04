package com.springboot.vo.risk;

import lombok.Data;

import java.util.List;

/**
 * 企业注册信息
 */
@Data
public class EntRegInformationVo {
    /**
     * 企业基本信息
     */
    private EntBasicInformationVo entBasicInformation;
    /**
     * 主要人员
     */
    private List<PersonnelVo> personnelList;
    /**
     * 股东信息
     */
    private List<ShareholderVo> shareholderList;
    //todo 分支机构
}
