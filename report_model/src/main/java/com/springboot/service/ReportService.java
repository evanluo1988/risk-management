package com.springboot.service;

import com.springboot.vo.GraphVo;
import com.springboot.vo.InformTop10Vo;
import com.springboot.vo.PendingListVo;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    /**
     * 举报top10
     * @return
     */
    List<InformTop10Vo> informsTop10();

    /**
     * 代办事项
     * @return
     */
    PendingListVo pendingList();

    public GraphVo getStatisticalGraph(LocalDate startDate, LocalDate endDate);

}
