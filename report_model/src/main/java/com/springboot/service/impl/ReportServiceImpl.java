package com.springboot.service.impl;

import com.springboot.service.ReportService;
import com.springboot.vo.InformTop10Vo;
import com.springboot.vo.PendingListVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Override
    public List<InformTop10Vo> informsTop10() {
        return null;
    }

    @Override
    public PendingListVo pendingList() {
        return null;
    }
}
