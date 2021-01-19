package com.springboot.controller;

import com.springboot.ret.ReturnT;
import com.springboot.service.ReportService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.GraphVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 获取举报对象top10的记录
     */
    @GetMapping("/informstop")
    public void informsTop10() {

    }

    /**
     * 待办事项
     */
    @GetMapping("/pendinglist")
    public void pendingList() {

    }

    /**
     * 统计图
     */
    @GetMapping("/statisticalgraph")
    public ReturnT statisticalGraph() {
        GraphVo graphVo = reportService.getStatisticalGraph();
        return ReturnTUtils.getReturnT(graphVo);
    }
}
