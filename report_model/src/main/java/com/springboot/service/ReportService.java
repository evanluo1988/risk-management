package com.springboot.service;

import com.springboot.vo.InformTop10Vo;
import com.springboot.vo.PendingListVo;

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
}
