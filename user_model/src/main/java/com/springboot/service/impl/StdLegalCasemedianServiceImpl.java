package com.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.risk.StdLegalCasemedian;
import com.springboot.mapper.StdLegalCasemedianMapper;
import com.springboot.service.StdLegalCasemedianService;
import org.springframework.stereotype.Service;

@Service
public class StdLegalCasemedianServiceImpl extends ServiceImpl<StdLegalCasemedianMapper, StdLegalCasemedian> implements StdLegalCasemedianService {
}
