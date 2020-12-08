package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.Enterprise;

public interface EnterpriseService extends IService<Enterprise> {
    public void create(Enterprise enterprise);

    Enterprise getEnterpriseById(Long id);
}
