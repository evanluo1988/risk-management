package com.springboot.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.springboot.domain.*;
import com.springboot.enums.AssignmentEnum;
import com.springboot.enums.CheckStatusEnum;
import com.springboot.enums.EnableEnum;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.TaskMapper;
import com.springboot.model.TaskModel;
import com.springboot.page.PageIn;
import com.springboot.page.Pagination;
import com.springboot.service.*;
import com.springboot.util.ConvertUtils;
import com.springboot.utils.ServerCacheUtils;
import com.springboot.utils.UserAuthInfoContext;
import com.springboot.vo.TaskDetailVo;
import com.springboot.vo.TaskImportVo;
import com.springboot.vo.TaskVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private TaskCheckService taskCheckService;
    @Autowired
    private TaskDispositionService taskDispositionService;
    @Autowired
    private TaskRefundService taskRefundService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importTasks(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), TaskImportVo.class, new TasksUploadDataListener(this)).sheet().headRowNumber(1).doRead();
        } catch (IOException e) {
            log.error("导入举报信息异常：{}", e);
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
            if (CollectionUtils.isEmpty(data)) {
                return;
            }
            //校验编号不能重复
            Set<String> taskNumbers = data.stream().map(TaskImportVo::getTaskNumber).collect(Collectors.toSet());
            Collection<Task> tasks = taskService.listTaskByTaskNumbers(taskNumbers);
            if (!CollectionUtils.isEmpty(tasks)){
                throw new ServiceException("任务编号数据库已经存在！"+Arrays.toString(tasks.stream().map(Task::getTaskNumber).collect(Collectors.toSet()).toArray()));
            }
            taskService.importTasks0(data);
        }
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importTasks0(List<TaskImportVo> taskImportVos) {
        Map<String, List<TaskImportVo>> taskImportMap = taskImportVos.stream().collect(Collectors.groupingBy(TaskImportVo::getTaskNumber));
        taskImportMap.forEach((k, v) -> {
            Task task = v.get(0).toTask();
            this.save(task);

            for (TaskImportVo taskImportVo : v) {
                TaskCheck taskCheck = taskImportVo.toTaskCheck();
                TaskDisposition taskDisposition = taskImportVo.toTaskDisposition();
                Enterprise enterprise = taskImportVo.toEnterprise();
                enterpriseService.save(enterprise);

                taskCheck.setTaskId(task.getId());
                taskCheck.setEnterpriseId(enterprise.getId());
                taskCheckService.save(taskCheck);

                taskDisposition.setTaskCheckId(taskCheck.getId());
                taskDispositionService.save(taskDisposition);
            }
        });
    }

    @Override
    public Pagination<TaskVo> pageTasks(String enterpriseName, String checkStatus, String disposalStage,
                                        String assignment, String checkRegion, Integer pageNo, Integer pageSize) {
        Page<TaskModel> page = taskMapper.pageTasks(enterpriseName, checkStatus, disposalStage, assignment, checkRegion, new Page<>(pageNo, pageSize));
        return Pagination.of(ConvertUtils.sourceToTarget(page.getRecords(), TaskVo.class), page.getTotal());
    }

    @Override
    public TaskDetailVo detail(Long id) {
        TaskCheck taskCheck = taskCheckService.getTaskCheckById(id);
        Task task = getTaskById(taskCheck.getTaskId());
        TaskDisposition taskDisposition = taskDispositionService.getDispositionByTaskCheckId(taskCheck.getId());
        Enterprise enterprise = enterpriseService.getEnterpriseById(taskCheck.getEnterpriseId());
        return new TaskDetailVo(task, taskCheck, taskDisposition, enterprise);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(Long id) {
        TaskCheck taskCheckById = taskCheckService.getTaskCheckById(id);
        if (Objects.isNull(taskCheckById)) {
            throw new ServiceException("任务不存在");
        }

        if (CheckStatusEnum.CHECKED.getCode().equalsIgnoreCase(taskCheckById.getCheckStatus())) {
            throw new ServiceException("已核查任务不可删除");
        }

        taskCheckById.setEnable(EnableEnum.N.getCode());
        taskCheckById.setUpdateBy(UserAuthInfoContext.getUserName());
        taskCheckById.setUpdateTime(new Date());
        taskCheckService.updateById(taskCheckById);
    }

    @Override
    public void dispatcher(Long id, Long areaId) {
        Area area = ServerCacheUtils.getAreaById(areaId);
        if (Objects.isNull(area)) {
            throw new ServiceException("区域信息不存在");
        }

        TaskCheck taskCheckById = taskCheckService.getTaskCheckById(id);
        if (Objects.isNull(taskCheckById)) {
            throw new ServiceException("任务不存在");
        }

        if (AssignmentEnum.ASSIGNED.getCode().equalsIgnoreCase(taskCheckById.getAssignment())) {
            throw new ServiceException("已分派的不能分派");
        }

        taskCheckById.setAssignment(AssignmentEnum.ASSIGNED.getCode())
                .setAreaId(areaId);
        taskCheckById.setUpdateTime(new Date());
        taskCheckById.setUpdateBy(UserAuthInfoContext.getUserName());
        taskCheckService.updateById(taskCheckById);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void goBack(Long id, String refundReason) {
        TaskCheck taskCheckById = taskCheckService.getTaskCheckById(id);
        if (Objects.isNull(taskCheckById)) {
            throw new ServiceException("核查数据不存在");
        }

        if (taskCheckById.getCheckStatus().equalsIgnoreCase(CheckStatusEnum.CHECKED.getCode())) {
            log.error("已经核查的核查任务不允许撤回");
            throw new ServiceException("已核查不允许退回");
        }

        taskCheckService.goBack(id);
        taskRefundService.refund(id, refundReason);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void revoke(Long id) {
        TaskCheck taskCheckById = taskCheckService.getById(id);
        if (Objects.isNull(taskCheckById)) {
            throw new ServiceException("任务不存在");
        }

        if (CheckStatusEnum.CHECKED.getCode().equalsIgnoreCase(taskCheckById.getCheckStatus())) {
            throw new ServiceException("已核查不能撤回");
        }

        taskCheckService.revoke(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void check(Long id, TaskVo taskVo) {
        TaskCheck taskCheckById = taskCheckService.getTaskCheckById(id);
        if (Objects.isNull(taskCheckById)) {
            throw new ServiceException("任务不存在");
        }

        TaskDisposition dispositionByTaskCheckId = taskDispositionService.getDispositionByTaskCheckId(id);
        if (Objects.isNull(dispositionByTaskCheckId)){
            TaskDisposition taskDisposition = ConvertUtils.sourceToTarget(taskVo, TaskDisposition.class);
            taskDisposition.setTaskCheckId(id);
            taskDisposition.setCreateBy(UserAuthInfoContext.getUserName());
            taskDisposition.setCreateTime(new Date());
            taskDispositionService.save(taskDisposition);
        }else {
            taskVo.setId(dispositionByTaskCheckId.getId());
            BeanUtils.copyProperties(taskVo,dispositionByTaskCheckId);
            dispositionByTaskCheckId.setUpdateBy(UserAuthInfoContext.getUserName());
            dispositionByTaskCheckId.setUpdateTime(new Date());
            taskDispositionService.updateById(dispositionByTaskCheckId);
        }

        taskCheckById.setCheckStatus(taskVo.getCheckStatus());
        taskCheckById.setUpdateBy(UserAuthInfoContext.getUserName());
        taskCheckById.setUpdateTime(new Date());
        taskCheckService.updateById(taskCheckById);
    }

    @Override
    public void recheck(Long id) {
        TaskCheck taskCheckById = taskCheckService.getTaskCheckById(id);
        if (Objects.isNull(taskCheckById)) {
            throw new ServiceException("核查不存在");
        }

        taskCheckById.setCheckStatus(CheckStatusEnum.WAITING_CHECK.getCode());
        taskCheckById.setUpdateBy(UserAuthInfoContext.getUserName());
        taskCheckById.setUpdateTime(new Date());
        taskCheckService.updateById(taskCheckById);
    }

    @Override
    public Collection<Task> listTaskByTaskNumbers(Set<String> taskNumbers) {
        if (CollectionUtils.isEmpty(taskNumbers)){
            return Collections.emptySet();
        }

        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<Task>()
                .in(Task::getTaskNumber, taskNumbers);
        return this.list(queryWrapper);
    }

    private Task getTaskById(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<Task>()
                .eq(Task::getId, id);
        return getOne(queryWrapper, false);
    }
}