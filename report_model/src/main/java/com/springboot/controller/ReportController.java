package com.springboot.controller;

import com.springboot.ret.ReturnT;
import com.springboot.service.ReportService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.InformTop10Vo;
import com.springboot.vo.PendingListVo;
import com.springboot.vo.GraphVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 获取举报对象top10的记录
     */
    @GetMapping("/informstop")
    public ReturnT informsTop10() {
        List<InformTop10Vo> informTop10Vos = reportService.informsTop10();
        return ReturnTUtils.getReturnT(informTop10Vos);
    }

    /**
     * 待办事项
     */
    @GetMapping("/pendinglist")
    public ReturnT pendingList() {
        PendingListVo pendingListVo = reportService.pendingList();
        return ReturnTUtils.getReturnT(pendingListVo);
    }

    /**
     * 统计图
     */
    @GetMapping("/statisticalgraph")
    public ReturnT statisticalGraph(@RequestParam(required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate startDate,
                                    @RequestParam(required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate endDate) {
        GraphVo graphVo = reportService.getStatisticalGraph(startDate, endDate);
        return ReturnTUtils.getReturnT(graphVo);
    }
}
