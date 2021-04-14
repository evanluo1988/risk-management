package com.springboot.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.springboot.domain.*;
import com.springboot.enums.EnableEnum;
import com.springboot.enums.AssignmentEnum;
import com.springboot.enums.CheckStatusEnum;
import com.springboot.enums.ProcessTypeEnum;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.InformDao;
import com.springboot.model.InformExportModel;
import com.springboot.model.InformGraphModel;
import com.springboot.model.InformPageModel;
import com.springboot.model.InformTop10Model;
import com.springboot.model.InfromPendingListModel;
import com.springboot.page.Pagination;
import com.springboot.service.*;
import com.springboot.utils.ConvertUtils;
import com.springboot.utils.HttpServletLocalThread;
import com.springboot.utils.UserAuthInfoContext;
import com.springboot.utils.ServerCacheUtils;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
@Slf4j
@Service
public class InformServiceImpl extends ServiceImpl<InformDao, Inform> implements InformService {

    @Autowired
    private InformPersonService informPersonService;
    @Autowired
    private InformCheckService informCheckService;
    @Autowired
    private InformRewardService informRewardService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private InformRefundService informRefundService;
    @Autowired
    private InformDao informDao;
    @Autowired
    private UserService userService;
    @Autowired
    private InformProcessService informProcessService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importInforms(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), InformImportVo.class, new InformsUploadDataListener(this)).sheet().headRowNumber(2).doRead();
        } catch (IOException e) {
            log.error("导入举报信息异常：{}", e);
            throw new ServiceException("导入举报信息错误");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importInforms0(Collection<InformImportVo> informImportVos) {
        for (InformImportVo informImportVo : informImportVos) {
            Inform inform = informImportVo.toInform();
            InformCheck informCheck = informImportVo.toInformCheck();
            InformPerson informPerson = informImportVo.toInformPerson();
            InformReward informReward = informImportVo.toInformReward();
            //store informPerson
            informPersonService.save(informPerson);
            //store inform
            inform.setInformPersonId(informPerson.getId());
            save(inform);
            //store informCheck
            informCheck.setInformId(inform.getId());
            informCheckService.save(informCheck);
            //store informReward
            informReward.setInformId(inform.getId());
            informRewardService.save(informReward);

            inform.setLastRewardId(informReward.getId());
            inform.setLastCheckId(informCheck.getId());
            updateById(inform);
        }
    }

    @Override
    public void export(String checkStatus, String assignment, LocalDate informTimeStart, LocalDate informTimeEnd, String rewardStatus, String informName, String verification, Boolean overdue, LocalDate checkTimeStart, LocalDate checkTimeEnd, Long areaId) throws IOException {
        if(Objects.isNull(areaId)) {
            areaId = UserAuthInfoContext.getAreaId();
        }

        int currentPage = 1;
        long totalPage = 1L;
        List<Long> areaIds = areaService.findAreaIdsById(areaId);

        HttpServletResponse response = HttpServletLocalThread.getResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("举报任务情况"+LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), InformExportVo.class).build();

        while (currentPage<=totalPage){
            Page<InformPageModel> informPage = informDao.informPage(checkStatus,assignment,informTimeStart,informTimeEnd,rewardStatus,informName,verification,overdue,checkTimeStart,checkTimeEnd,areaIds,new Page<>(currentPage, 10000));
            //如果当前页码是默认页码1  需要计算一次总页码
            if (currentPage == 1){
                totalPage = new Double(Math.ceil((double)informPage.getTotal()/10000)).longValue();
            }

            List<Long> ids = informPage.getRecords().stream().map(InformPageModel::getId).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(ids)){
                continue ;
            }

            List<InformExportModel> informs = informDao.listInformByIds(ids);
            List<InformExportVo> informExportVos = ConvertUtils.sourceToTarget(informs, InformExportVo.class);

            WriteSheet sheet = EasyExcel.writerSheet(currentPage).build();
            excelWriter.write(informExportVos,sheet);
            currentPage++;
        }

        excelWriter.finish();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String dispatcher(Long id, Long areaId, String opMessage) {
        Inform inform = getInformById(id);
        if (Objects.isNull(inform)) {
            throw new ServiceException("举报信息不存在");
        }

        if (AssignmentEnum.ASSIGNED.getCode().equalsIgnoreCase(inform.getAssignment())){
            throw new ServiceException("已分派的不能分派");
        }

        Area area = null;
        if(Objects.isNull(areaId)) {
            try{
                area = areaService.getArea(inform.getInformName());
            }catch(ServiceException e) {
                log.error("安硕查询区域异常：{}",e);
                throw new ServiceException("举报Id【"+id+"】下发失败");
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
            inform.setAssignment(AssignmentEnum.ASSIGNED_FAIL.getCode());
            inform.setUpdateTime(new Date());
            inform.setUpdateBy(UserAuthInfoContext.getUserName());
        }else {
            inform.setAreaId(area.getId())
                    .setAssignment(AssignmentEnum.ASSIGNED.getCode())
                    .setCheckStatus(CheckStatusEnum.WAITING_CHECK.getCode());
            inform.setUpdateTime(new Date());
            inform.setUpdateBy(UserAuthInfoContext.getUserName());

            addInformProcess(inform.getId(), ProcessTypeEnum.DISPATCHER,opMessage);
        }

        updateById(inform);

        if(AssignmentEnum.ASSIGNED_FAIL.getCode().equals(inform.getAssignment())) {
            return "举报Id【"+id+"】下发失败";
        }else{
            return null;
        }
    }

    private InformProcess addInformProcess(Long informId, ProcessTypeEnum processTypeEnum, String opMessage) {
        return informProcessService.addProcess(informId,processTypeEnum,opMessage,UserAuthInfoContext.getUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void goBack(Long id, String refundReason) {
        Inform informById = getInformById(id);
        if (Objects.isNull(informById)) {
            throw new ServiceException("举报信息不存在");
        }

        if (informById.getCheckStatus().equalsIgnoreCase(CheckStatusEnum.CHECKED.getCode())){
            log.error("已经核查的举报不允许撤回");
            throw new ServiceException("已核查不允许退回");
        }

        Area parentId = ServerCacheUtils.getAreaById(ServerCacheUtils.getAreaById(informById.getAreaId()).getParentId());
        if (Objects.isNull(parentId)){
            throw new ServiceException("找不到父级区域，撤回失败");
        }

        final InformProcess process = addInformProcess(id, ProcessTypeEnum.RETURN, refundReason);

        //store inform refund
        InformRefund informRefund = new InformRefund();
        informRefund.setProcessId(process.getId());
        informRefund.setInformId(informById.getId());
        informRefund.setReason(refundReason);
        informRefund.setCreateTime(new Date());
        informRefund.setCreateBy(UserAuthInfoContext.getUserName());
        informRefundService.save(informRefund);

        LambdaUpdateWrapper<Inform> updateWrapper = new LambdaUpdateWrapper<Inform>()
                .set(Inform::getAssignment, AssignmentEnum.RETURNED.getCode())
                .set(Inform::getAreaId, parentId.getId())
                .set(Inform::getUpdateBy, UserAuthInfoContext.getUserName())
                .set(Inform::getUpdateTime, LocalDateTime.now())
                .set(Inform::getLastRefundId,informRefund.getId())
                .eq(Inform::getId, id)
                .eq(Inform::getAssignment, AssignmentEnum.ASSIGNED.getCode());
        update(updateWrapper);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void check(Long id, InformCheckReqVo informVo) {
        Inform informById = getInformById(id);
        if (Objects.isNull(informById)) {
            throw new ServiceException("举报信息不存在");
        }

        if (!informById.getCheckStatus().equalsIgnoreCase(CheckStatusEnum.WAITING_CHECK.getCode())){
            throw new ServiceException("任务不是待核查状态，不能核查");
        }

        InformProcess process = addInformProcess(id,ProcessTypeEnum.PROCESS,null);
        //store informCheck
        InformCheck informCheck = ConvertUtils.sourceToTarget(informVo, InformCheck.class);
        informCheck.setProcessId(process.getId());
        informCheck.setCheckTime(LocalDateTime.of(informVo.getCheckTime(), LocalTime.of(0,0)));
        informCheck.setInformId(informById.getId());
        informCheck.setCreateBy(UserAuthInfoContext.getUserName());
        informCheck.setCreateTime(new Date());
        informCheckService.save(informCheck);

        // reward
        InformReward informReward = ConvertUtils.sourceToTarget(informVo, InformReward.class);
        informReward.setProcessId(process.getId());
        informReward.setCreateTime(new Date());
        informReward.setCreateBy(UserAuthInfoContext.getUserName());
        informRewardService.save(informReward);

        //update checkStatus
        informById.setCheckStatus(CheckStatusEnum.CHECKED.getCode());
        informById.setUpdateTime(new Date());
        informById.setUpdateBy(UserAuthInfoContext.getUserName());
        informById.setLastCheckId(informCheck.getId());
        informById.setLastRewardId(informReward.getId());
        updateById(informById);
    }

    @Override
    public Pagination<InformPageVo> informPage(String checkStatus, String assignment,
                                               LocalDate informTimeStart, LocalDate informTimeEnd,
                                               String rewardStatus, String informName,
                                               String verification, Boolean overdue,
                                               LocalDate checkTimeStart, LocalDate checkTimeEnd,
                                               Long areaId, Integer pageNo, Integer pageSize) {

        if(Objects.isNull(areaId)) {
            areaId = UserAuthInfoContext.getAreaId();
        }
        List<Long> areaIds = areaService.findAreaIdsById(areaId);
        Page<InformPageModel> page = informDao.informPage(checkStatus, assignment, informTimeStart, informTimeEnd, rewardStatus, informName, verification, overdue, checkTimeStart, checkTimeEnd, areaIds, new Page(pageNo, pageSize));
        final List<InformPageVo> informPageVos = ConvertUtils.sourceToTarget(page.getRecords(), InformPageVo.class);
        additionAreaContact(informPageVos);
        return Pagination.of(informPageVos, page.getTotal());
    }

    private void additionAreaContact(List<InformPageVo> informPageVos) {
        final List<Long> areaIds = informPageVos.stream().map(InformPageVo::getAreaId).collect(Collectors.toList());
        Map<Long,List<User>> areaUsers = userService.groupUserByAreaIds(areaIds);
        for (InformPageVo informPageVo : informPageVos) {
            final Long areaId = informPageVo.getAreaId();
            if (Objects.nonNull(areaId)&&areaUsers.containsKey(areaId)){
                informPageVo.setAreaContact(areaUsers.get(areaId));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(Long id) {
        Inform informById = getInformById(id);
        if (Objects.isNull(informById)){
            throw new ServiceException("举报信息不存在");
        }

        if (CheckStatusEnum.CHECKED.getCode().equalsIgnoreCase(informById.getCheckStatus())){
            throw new ServiceException("已核查任务不能删除");
        }

        LambdaUpdateWrapper<Inform> updateWrapper = new LambdaUpdateWrapper<Inform>()
                .set(Inform::getEnable, EnableEnum.N.getCode())
                .set(Inform::getUpdateTime,new Date())
                .set(Inform::getUpdateBy,UserAuthInfoContext.getUserName())
                .eq(Inform::getId, id);
        this.update(updateWrapper);
    }

    @Override
    public InformViewVo view(Long id) {
        Inform informById = getInformById(id);
        InformViewVo informViewVo = new InformViewVo();

        InformInfoVo informInfoVo = ConvertUtils.sourceToTarget(informById, InformInfoVo.class);
        Optional.ofNullable(informInfoVo).ifPresent((informInfoVoTemp)->{
            informInfoVo.setAreaName(ServerCacheUtils.getAreaById(informInfoVoTemp.getAreaId()).getAreaName());
        });
        informViewVo.setInform(informInfoVo);
        if (Objects.nonNull(informById)){
            InformCheck informCheck = informCheckService.getByInformId(id);
            informViewVo.setInformCheck(ConvertUtils.sourceToTarget(informCheck,InformCheckVo.class));

            InformReward informReward = informRewardService.getByInformId(id);
            informViewVo.setInformReward(ConvertUtils.sourceToTarget(informReward,InformRewardVo.class));
        }

        return informViewVo;
    }

    @Override
    public InformViewVo viewOnProcess(Long id, Long processId) {
        Inform informById = getInformById(id);
        InformViewVo informViewVo = new InformViewVo();

        InformInfoVo informInfoVo = ConvertUtils.sourceToTarget(informById, InformInfoVo.class);
        Optional.ofNullable(informInfoVo).ifPresent((informInfoVoTemp)->{
            informInfoVo.setAreaName(ServerCacheUtils.getAreaById(informInfoVoTemp.getAreaId()).getAreaName());
        });
        informViewVo.setInform(informInfoVo);
        if (Objects.nonNull(informById)){
            InformCheck informCheck = informCheckService.getByInformIdAndProcessId(id,processId);
            informViewVo.setInformCheck(ConvertUtils.sourceToTarget(informCheck,InformCheckVo.class));

            InformReward informReward = informRewardService.getByInformIdAndProcessId(id,processId);
            informViewVo.setInformReward(ConvertUtils.sourceToTarget(informReward,InformRewardVo.class));
        }

        return informViewVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void revoke(Long id) {
        Inform informById = getInformById(id);
        if (Objects.isNull(informById)){
            throw new ServiceException("举报信息不存在");
        }

        if(!AssignmentEnum.ASSIGNED.getCode().equals(informById.getAssignment())) {
            throw new ServiceException("未分派不能撤回");
        }

        if (CheckStatusEnum.CHECKED.getCode().equalsIgnoreCase(informById.getCheckStatus())){
            throw new ServiceException("已核查不能撤回");
        }

        Area parentArea = ServerCacheUtils.getAreaById(ServerCacheUtils.getAreaById(informById.getAreaId()).getParentId());
        if (Objects.isNull(parentArea)){
            throw new ServiceException("找不到父级区域，撤回失败");
        }

        addInformProcess(id,ProcessTypeEnum.REVOKE,null);
        LambdaUpdateWrapper<Inform> updateWrapper = new LambdaUpdateWrapper<Inform>()
                .set(Inform::getAssignment, AssignmentEnum.REVOKE.getCode())
                .set(Inform::getAreaId, parentArea.getId())
                .set(Inform::getUpdateBy, UserAuthInfoContext.getUserName())
                .set(Inform::getUpdateTime, new Date())
                .eq(Inform::getId, informById.getId());
        this.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recheck(Long id) {
        Inform informById = getInformById(id);
        if (Objects.isNull(informById)){
            throw new ServiceException("举报信息不存在");
        }
        if (!CheckStatusEnum.CHECKED.getCode().equals(informById.getCheckStatus())) {
            throw new ServiceException("该条举报信息还未核查");
        }

        addInformProcess(id,ProcessTypeEnum.RECHECK,null);
        informById.setCheckStatus(CheckStatusEnum.WAITING_CHECK.getCode());
        informById.setUpdateBy(UserAuthInfoContext.getUserName());
        informById.setUpdateTime(new Date());
        updateById(informById);
    }

    @Override
    public List<InformTop10Model> informsTop10() {
        List<Long> areaIds = areaService.findAreaIdsById(UserAuthInfoContext.getAreaId());
        return informDao.informsTop10(areaIds);
    }

    @Override
    public InfromPendingListModel pendingList() {
        List<Long> areaIds = areaService.findAreaIdsById(UserAuthInfoContext.getAreaId());
        Integer overdueNum = informDao.pendingOverdueList(areaIds);
        Integer toCheckNum = informDao.pendingToCheckList(areaIds);
        return new InfromPendingListModel(overdueNum, toCheckNum);
    }

    @Override
    public List<InformGraphModel> getInformGraphList(LocalDate startDate, LocalDate endDate) {
        List<Long> areaIds = areaService.findAreaIdsById(UserAuthInfoContext.getAreaId());
        return informDao.getInformGraphList(areaIds, startDate, endDate);
    }

    private Inform getInformById(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }

        LambdaQueryWrapper<Inform> queryWrapper = new LambdaQueryWrapper<Inform>()
                .eq(Inform::getEnable, EnableEnum.Y.getCode())
                .eq(Inform::getId, id);
        return getOne(queryWrapper, false);
    }

    @Slf4j
    private static class InformsUploadDataListener extends AnalysisEventListener<InformImportVo> {
        private InformService informService;

        private Collection<InformImportVo> data = Lists.newArrayList();

        public InformsUploadDataListener(InformService informService) {
            this.informService = informService;
        }

        @Override
        public void invoke(InformImportVo informImportVo, AnalysisContext analysisContext) {
            data.add(informImportVo);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            // 已核查的不倒入
            data = data.stream().filter(item -> !CheckStatusEnum.CHECKED.getCode().equalsIgnoreCase(item.getCheckStatus())).collect(Collectors.toList());
            log.debug("import doAfter");
            //校验线索编号不能重复
            Set<String> clueNumbers = getClueNumbers(data);
            //校验编号数据库不存在
            Collection<Inform> informs = listInformByClueNumbers(clueNumbers);
            if (informs.size() > 0) {
                Set<String> collect = informs.stream().map(Inform::getClueNumber).collect(Collectors.toSet());
                throw new ServiceException("线索编号数据库已经存在：" + Arrays.toString(collect.toArray()));
            }
            //检查核查单位是否合法
            checkUnitCorrect(data);
            checkInformTimeNonNull(data);
            informService.importInforms0(data);
        }

        private void checkInformTimeNonNull(Collection<InformImportVo> data) {
            boolean hasNull = false;
            for (InformImportVo informImportVo : data) {
                if (StringUtils.isEmpty(informImportVo.getInformTimeStr())){
                    hasNull = true;
                    break;
                }
            }

            if (hasNull){
                throw new ServiceException("举报时间不能为空");
            }
        }

        private void checkUnitCorrect(Collection<InformImportVo> data) {
            data.stream().filter(informImportVo -> !StringUtils.isEmpty(informImportVo.getCheckUnitStr())).forEach(informImportVo -> {
                Area areaByName = ServerCacheUtils.getAreaByName(informImportVo.getCheckUnitStr());
                if (Objects.isNull(areaByName)){
                    throw new ServiceException("核查单位错误，线索编号："+informImportVo.getClueNumber());
                }
            });
        }

        private Collection<Inform> listInformByClueNumbers(Set<String> clueNumbers) {
            if (CollectionUtils.isEmpty(clueNumbers)){
                return Collections.emptySet();
            }

            LambdaQueryWrapper<Inform> queryWrapper = new LambdaQueryWrapper<Inform>()
                    .eq(Inform::getEnable, EnableEnum.Y.getCode())
                    .in(Inform::getClueNumber, clueNumbers);
            return informService.list(queryWrapper);
        }

        private Set<String> getClueNumbers(Collection<InformImportVo> data) {
            Set<String> clueNumbers = new HashSet<>(data.size());
            data.forEach(informImportVo -> {
                String clueNumber;
                if (clueNumbers.contains(clueNumber = informImportVo.getClueNumber())) {
                    throw new ServiceException("Excel中线索编号重复：" + clueNumber);
                } else {
                    clueNumbers.add(clueNumber);
                }
            });
            return clueNumbers;
        }
    }
}
