package com.springboot.service;

import com.springboot.domain.CloudQueryLog;

public interface CloudQueryLogService {
    public void create(CloudQueryLog cloudQueryLog);
    public CloudQueryLog getByReqId(String reqId);
    public void update(CloudQueryLog cloudQueryLog);
}
