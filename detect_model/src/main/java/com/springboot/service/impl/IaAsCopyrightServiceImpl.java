package com.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.risk.IaAsCopyright;
import com.springboot.mapper.IaAsCopyrightMapper;
import com.springboot.service.IaAsCopyrightService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class IaAsCopyrightServiceImpl extends ServiceImpl<IaAsCopyrightMapper, IaAsCopyright> implements IaAsCopyrightService {
    @Override
    public void saveIaAsCopyrights(List<IaAsCopyright> iaAsCopyrightList) {
        if(CollectionUtils.isEmpty(iaAsCopyrightList)){
            return;
        }
        saveBatch(iaAsCopyrightList, 50);
    }
}
