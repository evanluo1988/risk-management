package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.CloudInfoTimeliness;
import com.springboot.mapper.CloudInfoTimelinessMapper;
import com.springboot.service.CloudInfoTimelinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CloudInfoTimelinessServiceImpl extends ServiceImpl<CloudInfoTimelinessMapper, CloudInfoTimeliness> implements CloudInfoTimelinessService {
    @Autowired
    private CloudInfoTimelinessMapper cloudInfoTimelinessMapper;

    @Override
    public boolean checkTimeliness(String entName) {
        //没找到或时间过期，则时效性过期
        CloudInfoTimeliness cloudInfoTimeliness = getCloudInfoTimelinessByEntName(entName);
        return checkTimeliness(cloudInfoTimeliness);
    }

    @Override
    public boolean checkTimeliness(CloudInfoTimeliness cloudInfoTimeliness) {
        if(cloudInfoTimeliness == null){
            return Boolean.FALSE;
        }
        if(LocalDateTime.now().isAfter(cloudInfoTimeliness.getExpireTime())){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public CloudInfoTimeliness getCloudInfoTimelinessByEntName(String entName) {
        LambdaQueryWrapper<CloudInfoTimeliness> queryWrapper = new LambdaQueryWrapper<CloudInfoTimeliness>()
                .eq(CloudInfoTimeliness::getEntName, entName)
                .eq(CloudInfoTimeliness::getStatus, 'Y');
        return getOne(queryWrapper,false);
    }

    /**
     * 更新时效性
     * @param entName
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTimeLiness(String entName, String reqId) {
        CloudInfoTimeliness cloudInfoTimeliness = getCloudInfoTimelinessByEntName(entName);
        if(cloudInfoTimeliness != null) {
            cloudInfoTimeliness.setStatus("N");
            cloudInfoTimelinessMapper.updateById(cloudInfoTimeliness);
        }

        cloudInfoTimeliness = new CloudInfoTimeliness();
        cloudInfoTimeliness.setReqId(reqId);
        cloudInfoTimeliness.setEntName(entName);
        cloudInfoTimeliness.setStatus("Y");
        cloudInfoTimeliness.setExpireTime(LocalDateTime.now().plusDays(30));
        cloudInfoTimelinessMapper.insert(cloudInfoTimeliness);
    }

}
