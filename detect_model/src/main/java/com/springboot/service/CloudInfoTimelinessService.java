package com.springboot.service;

import com.springboot.domain.CloudInfoTimeliness;

public interface CloudInfoTimelinessService {
    /**
     * 检测时效性
     * @param entName 企业名称
     * @return
     */
    public boolean checkTimeliness(String entName);

    public boolean checkTimeliness(CloudInfoTimeliness cloudInfoTimeliness);

    public CloudInfoTimeliness getCloudInfoTimelinessByEntName(String entName);

    public void updateTimeLiness(String entName, String reqId);

}
