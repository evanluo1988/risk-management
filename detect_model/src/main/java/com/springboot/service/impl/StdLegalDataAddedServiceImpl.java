package com.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.StdLegalDataAdded;
import com.springboot.mapper.StdLegalDataAddedMapper;
import com.springboot.service.StdLegalDataAddedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class StdLegalDataAddedServiceImpl extends ServiceImpl<StdLegalDataAddedMapper, StdLegalDataAdded> implements StdLegalDataAddedService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveStdLegalDataAddedValues(List<StdLegalDataAdded> stdLegalDataAddedList) {
        if(CollectionUtils.isEmpty(stdLegalDataAddedList)) {
            return;
        }
        saveBatch(stdLegalDataAddedList);
    }
}
