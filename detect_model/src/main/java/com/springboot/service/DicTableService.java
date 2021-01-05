package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.DicTable;

import java.util.List;

public interface DicTableService extends IService<DicTable> {
    public List<DicTable> getDicTableList();
}
