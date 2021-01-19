package com.springboot.service.impl;

import com.google.common.collect.Lists;
import com.springboot.enums.CheckStatusEnum;
import com.springboot.model.*;
import com.springboot.service.*;
import com.springboot.utils.ConvertUtils;
import com.springboot.utils.ServerCacheUtils;
import com.springboot.utils.UserAuthInfoContext;
import com.springboot.utils.Utils;
import com.springboot.vo.GraphItemVo;
import com.springboot.vo.InformTop10Vo;
import com.springboot.vo.PendingListVo;
import org.springframework.beans.BeanUtils;
import com.springboot.service.InformService;
import com.springboot.service.ReportService;
import com.springboot.vo.GraphVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private InformService informService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AreaService areaService;

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
        List<GraphItemVo> graphItemList = Lists.newArrayList();
        graphVo.setGraphItemList(graphItemList);
        List<Long> areaIds = areaService.findAreaIdsById(UserAuthInfoContext.getAreaId(), Boolean.FALSE);
        List<InformGraphModel> informGraphModelList = informService.getInformGraphList();
        List<TaskGraphModel> taskGraphModelList = taskService.getInformGraphList();
        for(Long areaId : areaIds) {
            GraphItemVo graphItemVo = new GraphItemVo();
            graphItemVo.setAreaName(ServerCacheUtils.getAreaById(areaId).getAreaName());
            List<InformGraphModel> informGraphModels = Utils.getList(informGraphModelList).stream().filter(item -> item.getAreaId().equals(areaId)).collect(Collectors.toList());
            for(InformGraphModel informGraphModel : Utils.getList(informGraphModels)) {
                if(CheckStatusEnum.CHECKED.getCode().equals(informGraphModel.getCheckStatus())) {
                    graphItemVo.setInformChecked(informGraphModel.getCount());
                }else {
                    graphItemVo.setInformUncheck(informGraphModel.getCount());
                }
            }

            List<TaskGraphModel> taskGraphModels = Utils.getList(taskGraphModelList).stream().filter(item -> item.getAreaId().equals(areaId)).collect(Collectors.toList());
            for(TaskGraphModel taskGraphModel : taskGraphModels) {
                if(CheckStatusEnum.CHECKED.getCode().equals(taskGraphModel.getCheckStatus())) {
                    graphItemVo.setTaskChecked(taskGraphModel.getCount());
                } else {
                    graphItemVo.setTaskUncheck(taskGraphModel.getCount());
                }
            }
            graphItemList.add(graphItemVo);
        }
        return graphVo;
    }
}
