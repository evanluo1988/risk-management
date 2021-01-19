package com.springboot.service.impl;

import com.springboot.model.InformTop10Model;
import com.springboot.model.InfromPendingListModel;
import com.springboot.model.TaskPendingListModel;
import com.springboot.service.InformService;
import com.springboot.service.ReportService;
import com.springboot.service.TaskService;
import com.springboot.utils.ConvertUtils;
import com.springboot.vo.InformTop10Vo;
import com.springboot.vo.PendingListVo;
import org.springframework.beans.BeanUtils;
import com.springboot.model.InformGraphModel;
import com.springboot.service.InformService;
import com.springboot.service.ReportService;
import com.springboot.vo.GraphVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private InformService informService;
    @Autowired
    private TaskService taskService;

    @Override
    public List<InformTop10Vo> informsTop10() {
        List<InformTop10Model> informTop10Models = informService.informsTop10();
        return ConvertUtils.sourceToTarget(informTop10Models,InformTop10Vo.class);
    }

    @Override
    public PendingListVo pendingList() {
        InfromPendingListModel informPendingListModel = informService.pendingList();
        TaskPendingListModel taskPendingListModel = taskService.pendingList();

        PendingListVo pendingListVo = new PendingListVo();
        BeanUtils.copyProperties(informPendingListModel, pendingListVo);
        BeanUtils.copyProperties(taskPendingListModel, pendingListVo);
        return pendingListVo;
    }


    @Override
    public GraphVo getStatisticalGraph() {
        GraphVo graphVo = new GraphVo();
        List<InformGraphModel> informGraphModelList = informService.getInformGraphList();
        return graphVo;
    }
}
