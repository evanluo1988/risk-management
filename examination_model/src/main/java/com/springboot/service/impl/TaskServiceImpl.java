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
import com.springboot.model.TaskExportModel;
import com.springboot.model.TaskGraphModel;
import com.springboot.model.TaskModel;
import com.springboot.model.TaskPendingListModel;
import com.springboot.page.PageIn;
import com.springboot.page.Pagination;
import com.springboot.service.*;
import com.springboot.utils.ConvertUtils;
import com.springboot.utils.DateUtils;
import com.springboot.utils.Utils;
import com.springboot.utils.HttpServletLocalThread;
import com.springboot.utils.ServerCacheUtils;
import com.springboot.utils.UserAuthInfoContext;
import com.springboot.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    private AreaService areaService;


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
            if (!CollectionUtils.isEmpty(tasks)) {
                throw new ServiceException("任务编号数据库已经存在！" + Arrays.toString(tasks.stream().map(Task::getTaskNumber).collect(Collectors.toSet()).toArray()));
            }

            checkRegionNonNull(data);
            checkStartTimeNonNull(data);
            taskService.importTasks0(data);
        }

        private void checkRegionNonNull(List<TaskImportVo> data) {
            boolean hasNull = false;
            for (TaskImportVo taskImportVo : data) {
                if (StringUtils.isEmpty(taskImportVo.getCheckRegion())) {
                    hasNull = true;
                    break;
                }
            }

            if (hasNull) {
                throw new ServiceException("核查区域不能为空");
            }
        }

        private void checkStartTimeNonNull(List<TaskImportVo> data) {
            boolean hasNull = false;
            for (TaskImportVo taskImportVo : data) {
                if (StringUtils.isEmpty(taskImportVo.getStartTimeStr())) {
                    hasNull = true;
                    break;
                }
            }

            if (hasNull) {
                throw new ServiceException("创建时间不能为空");
            }
        }
    }

//    @Override
//    public Pagination<TaskPageVo> findTasks(PageIn pageIn) {
//        IPage<TaskModel> taskPage = taskMapper.findPageTasks(pageIn.convertPage());
//
//        List<TaskPageVo> taskVos = new ArrayList<>();
//        for (TaskModel taskModel : taskPage.getRecords()) {
//            taskVos.add(taskModel.convertVo());
//        }
//
//        return Pagination.of(taskVos, taskPage.getTotal());
//    }

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
    public Pagination<TaskCheckPageVo> pageTasks(String disposalStage, LocalDate taskTimeStart, LocalDate taskTimeEnd, Boolean overdue,
                                                 LocalDate taskExpireStart, LocalDate taskExpireEnd, String enterpriseName,
                                                 String checkStatus, String assignment, Long areaId, Integer pageNo, Integer pageSize) {

        if (Objects.isNull(areaId)) {
            areaId = UserAuthInfoContext.getAreaId();
        }

        List<Long> areaIds = areaService.findAreaIdsById(areaId);
        Page<TaskModel> page = taskMapper.pageTasks(disposalStage, taskTimeStart, taskTimeEnd, overdue,
                taskExpireStart, taskExpireEnd, enterpriseName,
                checkStatus, assignment, areaIds, new Page<>(pageNo, pageSize));
        List<TaskCheckPageVo> taskPageVoList = Lists.newArrayList();
        for (TaskModel taskModel : Utils.getList(page.getRecords())) {
            TaskCheckPageVo taskPageVo = new TaskCheckPageVo();
            BeanUtils.copyProperties(taskModel, taskPageVo);
            taskPageVo.setStartTime(DateUtils.toLocalDate(taskModel.getStartTime()));
            taskPageVoList.add(taskPageVo);
        }
        return Pagination.of(taskPageVoList, page.getTotal());
    }

    @Override
    public TaskDetailVo detail(Long id) {
        TaskCheck taskCheck = taskCheckService.getTaskCheckById(id);
        Task task = getTaskById(taskCheck.getTaskId());
        TaskDisposition taskDisposition = taskDispositionService.getDispositionByTaskCheckId(taskCheck.getId());
        Enterprise enterprise = enterpriseService.getEnterpriseById(taskCheck.getEnterpriseId());
        return new TaskDetailVo(ConvertUtils.sourceToTarget(task, TaskInfoVo.class), ConvertUtils.sourceToTarget(taskCheck, TaskCheckInfoVo.class)
                , ConvertUtils.sourceToTarget(taskDisposition, TaskDispositionVo.class), ConvertUtils.sourceToTarget(enterprise, EnterpriseVo.class));
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
    public String dispatcher(Long id, Long areaId) {
        TaskCheck taskCheckById = taskCheckService.getTaskCheckById(id);
        if (Objects.isNull(taskCheckById)) {
            throw new ServiceException("任务不存在");
        }

        if (AssignmentEnum.ASSIGNED.getCode().equalsIgnoreCase(taskCheckById.getAssignment())) {
            throw new ServiceException("已分派的不能分派");
        }
        Area area = null;
        if (Objects.isNull(areaId)) {
            Enterprise enterpriseById = enterpriseService.getEnterpriseById(taskCheckById.getEnterpriseId());
            try{
                area = areaService.getArea(enterpriseById.getEnterpriseName());
            }catch(ServiceException e){
                log.error("安硕查询区域异常：{}",e);
                throw new ServiceException("任务Id【"+id+"】下发失败");
            }
        } else {
            List<Long> areaIdsById = areaService.findAreaIdsById(UserAuthInfoContext.getAreaId(), false);
            if (!areaIdsById.contains(areaId)){
                throw new ServiceException("分派区域异常");
            }
            area = ServerCacheUtils.getAreaById(areaId);
        }

        if (Objects.isNull(area)) {
            log.info("未查询到区域信息，下发失败，id:{},areaId:{}",id,areaId);
            taskCheckById.setAssignment(AssignmentEnum.ASSIGNED_FAIL.getCode());
            taskCheckById.setUpdateTime(new Date());
            taskCheckById.setUpdateBy(UserAuthInfoContext.getUserName());
        } else {
            taskCheckById.setAssignment(AssignmentEnum.ASSIGNED.getCode())
                    .setAreaId(area.getId())
                    .setCheckRegion(area.getAreaName());
            taskCheckById.setUpdateTime(new Date());
            taskCheckById.setUpdateBy(UserAuthInfoContext.getUserName());
        }

        taskCheckService.updateById(taskCheckById);

        if(AssignmentEnum.ASSIGNED_FAIL.getCode().equals(taskCheckById.getAssignment())) {
            return "任务Id【"+id+"】下发失败";
        }else{
            return null;
        }
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

        if (!AssignmentEnum.ASSIGNED.getCode().equals(taskCheckById.getAssignment())) {
            throw new ServiceException("未分派不能撤回");
        }

        if (CheckStatusEnum.CHECKED.getCode().equalsIgnoreCase(taskCheckById.getCheckStatus())) {
            throw new ServiceException("已核查不能撤回");
        }

        taskCheckService.revoke(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void check(Long id, TaskCheckVo taskVo) {
        TaskCheck taskCheckById = taskCheckService.getTaskCheckById(id);
        if (Objects.isNull(taskCheckById)) {
            throw new ServiceException("任务不存在");
        }

        TaskDisposition dispositionByTaskCheckId = taskDispositionService.getDispositionByTaskCheckId(id);
        if (Objects.isNull(dispositionByTaskCheckId)) {
            TaskDisposition taskDisposition = ConvertUtils.sourceToTarget(taskVo, TaskDisposition.class);
            taskDisposition.setTaskCheckId(id);
            taskDisposition.setCreateBy(UserAuthInfoContext.getUserName());
            taskDisposition.setCreateTime(new Date());
            taskDispositionService.save(taskDisposition);
        } else {
            //taskVo.setId(dispositionByTaskCheckId.getId());
            BeanUtils.copyProperties(taskVo, dispositionByTaskCheckId, "id");
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
    @Transactional(rollbackFor = Exception.class)
    public void recheck(Long id) {
        TaskCheck taskCheckById = taskCheckService.getTaskCheckById(id);
        if (Objects.isNull(taskCheckById)) {
            throw new ServiceException("核查不存在");
        }
        if (!CheckStatusEnum.CHECKED.getCode().equals(taskCheckById.getCheckStatus())) {
            throw new ServiceException("该条任务信息还未核查");
        }

        taskCheckById.setCheckStatus(CheckStatusEnum.WAITING_CHECK.getCode());
        taskCheckById.setUpdateBy(UserAuthInfoContext.getUserName());
        taskCheckById.setUpdateTime(new Date());
        taskCheckService.updateById(taskCheckById);
    }

    @Override
    public Collection<Task> listTaskByTaskNumbers(Set<String> taskNumbers) {
        if (CollectionUtils.isEmpty(taskNumbers)) {
            return Collections.emptySet();
        }

        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<Task>()
                .in(Task::getTaskNumber, taskNumbers);
        return this.list(queryWrapper);
    }

    @Override
    public void export(String disposalStage, LocalDate taskTimeStart, LocalDate taskTimeEnd, Boolean overdue, LocalDate taskExpireStart, LocalDate taskExpireEnd, String enterpriseName, String checkStatus, String assignment, Long areaId) throws IOException {
        if (Objects.isNull(areaId)) {
            areaId = UserAuthInfoContext.getAreaId();
        }

        List<Long> areaIds = areaService.findAreaIdsById(areaId);
        Page<TaskModel> page = taskMapper.pageTasks(disposalStage, taskTimeStart, taskTimeEnd, overdue,
                taskExpireStart, taskExpireEnd, enterpriseName,
                checkStatus, assignment, areaIds, new Page<>(1, 10000));

        List<Long> ids = page.getRecords().stream().map(TaskModel::getId).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        List<TaskExportModel> exportModelList = taskMapper.listTaskByIds(ids);
        List<TaskExportVo> taskExportVos = ConvertUtils.sourceToTarget(exportModelList, TaskExportVo.class);
        HttpServletResponse response = HttpServletLocalThread.getResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("核查处置任务情况" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), TaskExportVo.class).sheet("1").doWrite(taskExportVos);
    }

    @Override
    public TaskPendingListModel pendingList() {
        List<Long> areaIds = areaService.findAreaIdsById(UserAuthInfoContext.getAreaId());
        Integer overdueNum = taskMapper.pendingOverdueList(areaIds);
        Integer toCheckNum = taskMapper.pendingToCheckList(areaIds);
        return new TaskPendingListModel(overdueNum, toCheckNum);
    }

    @Override
    public List<TaskGraphModel> getInformGraphList(LocalDate startDate, LocalDate endDate) {
        List<Long> areaIds = areaService.findAreaIdsById(UserAuthInfoContext.getAreaId());
        return taskMapper.getInformGraphList(areaIds, startDate, endDate);
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
