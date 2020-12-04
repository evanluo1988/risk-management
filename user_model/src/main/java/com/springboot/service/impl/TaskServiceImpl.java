package com.springboot.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.springboot.domain.*;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.InformDao;
import com.springboot.mapper.TaskMapper;
import com.springboot.model.TaskImportModel;
import com.springboot.model.TaskModel;
import com.springboot.page.PageIn;
import com.springboot.page.Pagination;
import com.springboot.service.TaskService;
import com.springboot.util.ConvertUtils;
import com.springboot.vo.TaskImportVo;
import com.springboot.vo.TaskVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public void importTasks(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), TaskImportVo.class, new TasksUploadDataListener(this)).sheet().headRowNumber(1).doRead();
        } catch (IOException e) {
            log.error("导入举报信息异常：{}",e);
            throw new ServiceException("导入举报信息错误");
        }
    }

    @Slf4j
    private static class TasksUploadDataListener extends AnalysisEventListener<TaskImportVo> {

        private TaskService taskService;

        private List<TaskImportVo> data = Lists.newArrayList();
        private List<String> errors = Lists.newArrayList();

        public TasksUploadDataListener(TaskService taskService) {
            this.taskService = taskService;
        }


        @Override
        public void invoke(TaskImportVo taskImportVo, AnalysisContext analysisContext) {
            log.debug("解析到一条数据:{}", JSON.toJSONString(taskImportVo));
            data.add(taskImportVo);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            log.debug("import doAfter");
            if(CollectionUtils.isEmpty(data)) {
                return;
            }
            Map<String, List<TaskImportVo>> taskImportMap = data.stream().collect(Collectors.groupingBy(TaskImportVo::getTaskNumber));

            List<TaskImportModel> taskImportModelList = Lists.newArrayList();
            for(Map.Entry<String, List<TaskImportVo>> entry : taskImportMap.entrySet()) {
                List<TaskImportVo> taskImportVoList = entry.getValue();
                //set task
                Task task = taskImportVoList.get(0).toTask();
                TaskImportModel taskImportModel = new TaskImportModel();
                taskImportModel.setTask(task);

                List<TaskCheck> taskCheckList = Lists.newArrayList();
                List<Enterprise> enterpriseList = Lists.newArrayList();
                List<EnterpriseDetail> enterpriseDetailList = Lists.newArrayList();
                List<TaskDisposition> taskDispositionList = Lists.newArrayList();
                taskImportModel.setTaskCheckList(taskCheckList);
                taskImportModel.setEnterpriseList(enterpriseList);
                taskImportModel.setTaskDispositionList(taskDispositionList);
                for(TaskImportVo taskImportVo : taskImportVoList){
                    taskCheckList.add(taskImportVo.toTaskCheck());
                    enterpriseList.add(taskImportVo.toEnterprise());
                    taskDispositionList.add(taskImportVo.toTaskDisposition());
                }
                taskImportModelList.add(taskImportModel);
            }

            for (TaskImportModel taskImportModel : taskImportModelList) {
                try {
                    taskService.importTask(taskImportModel);
                }catch (Exception e){
                    log.error("导入数据出错：{}",e);
                    errors.add(taskImportModel.getTask().getTaskName());
                }
            }

            if (!CollectionUtils.isEmpty(errors)){
                throw new ServiceException("导入失败的线索编号："+ Arrays.toString(errors.toArray()));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importTask(TaskImportModel data) {
        Task task = data.getTask();
        //save(task);

        List<Enterprise> enterpriseList = data.getEnterpriseList();
        List<TaskCheck> taskCheckList = data.getTaskCheckList();
        List<TaskDisposition> taskDispositionList = data.getTaskDispositionList();

        //store enterprise
        //enterpriseService.save(enterprise);
        //store enterprise detail
        //inform.setInformPersonId(informPerson.getId());


        //store TaskCheck
        //informCheck.setInformId(inform.getId());
        //informCheckService.save(informCheck);
        //store TaskDisposition
        //informReward.setInformId(inform.getId());
        //informRewardService.save(informReward);
    }

    @Override
    public Pagination<TaskVo> findTasks(PageIn pageIn) {
        IPage<TaskModel> taskPage = taskMapper.findPageTasks(pageIn.convertPage());

        List<TaskVo> taskVos = new ArrayList<>();
        for (TaskModel taskModel : taskPage.getRecords()) {
            taskVos.add(taskModel.convertVo());
        }

        return Pagination.of(taskVos, taskPage.getTotal());
    }

}
