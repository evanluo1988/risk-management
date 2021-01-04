package com.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.risk.IaAsBrand;
import com.springboot.mapper.IaAsBrandMapper;
import com.springboot.service.IaAsBrandService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class IaAsBrandServiceImpl extends ServiceImpl<IaAsBrandMapper, IaAsBrand> implements IaAsBrandService {
    @Override
    public void saveIaAsBrands(List<IaAsBrand> iaAsBrandList) {
        if(CollectionUtils.isEmpty(iaAsBrandList)) {
            return;
        }
        saveBatch(iaAsBrandList, 50);
    }
}
