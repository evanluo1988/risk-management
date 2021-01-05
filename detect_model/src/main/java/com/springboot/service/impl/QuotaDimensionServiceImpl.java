package com.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.QuotaDimension;
import com.springboot.mapper.QuotaDimensionMapper;
import com.springboot.service.QuotaDimensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuotaDimensionServiceImpl extends ServiceImpl<QuotaDimensionMapper, QuotaDimension>  implements QuotaDimensionService {
    @Autowired
    private QuotaDimensionMapper quotaDimensionMapper;

    @Override
    public List<QuotaDimension> getAllQuotaDimensions() {
        return list();
    }
}
