package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.InformCheck;
import com.springboot.mapper.InformCheckDao;
import com.springboot.service.InformCheckService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
@Service
public class InformCheckServiceImpl extends ServiceImpl<InformCheckDao, InformCheck> implements InformCheckService {

    @Override
    public InformCheck getByInformId(Long informId) {
        if (Objects.isNull(informId)){
            return null;
        }

        LambdaQueryWrapper<InformCheck> queryWrapper = new LambdaQueryWrapper<InformCheck>()
                .eq(InformCheck::getInformId, informId)
                .orderByDesc(InformCheck::getCreateTime);
        return getOne(queryWrapper,false);
    }

    @Override
    public InformCheck getByInformIdAndProcessId(Long informId, Long processId) {
        if (Objects.isNull(informId)){
            return null;
        }

        LambdaQueryWrapper<InformCheck> queryWrapper = new LambdaQueryWrapper<InformCheck>()
                .eq(InformCheck::getInformId, informId)
                .eq(InformCheck::getProcessId,processId)
                .orderByDesc(InformCheck::getCreateTime);
        return getOne(queryWrapper,false);
    }
}
