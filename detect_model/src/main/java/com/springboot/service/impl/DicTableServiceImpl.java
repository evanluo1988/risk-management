package com.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.risk.DicTable;
import com.springboot.mapper.DicTableMapper;
import com.springboot.service.DicTableService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicTableServiceImpl extends ServiceImpl<DicTableMapper, DicTable> implements DicTableService {
    @Override
    public List<DicTable> getDicTableList() {
        return list();
    }
}
