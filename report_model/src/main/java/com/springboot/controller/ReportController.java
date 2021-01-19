package com.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("report")
public class ReportController {

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
    public void statisticalGraph() {

    }


}
