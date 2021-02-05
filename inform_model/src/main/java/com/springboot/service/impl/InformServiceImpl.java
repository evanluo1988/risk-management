package com.springboot.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.springboot.domain.*;
import com.springboot.enums.EnableEnum;
import com.springboot.enums.AssignmentEnum;
import com.springboot.enums.CheckStatusEnum;
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
        }
    }

    @Override
    public void export(String checkStatus, String assignment, LocalDate informTimeStart, LocalDate informTimeEnd, String rewardStatus, String informName, String verification, Boolean overdue, LocalDate checkTimeStart, LocalDate checkTimeEnd, Long areaId) throws IOException {
        if(Objects.isNull(areaId)) {
            areaId = UserAuthInfoContext.getAreaId();
        }
        List<Long> areaIds = areaService.findAreaIdsById(areaId);
        Page<InformPageModel> page = informDao.informPage(checkStatus, assignment, informTimeStart, informTimeEnd, rewardStatus, informName, verification, overdue, checkTimeStart, checkTimeEnd, areaIds, new Page(1, 10000));

        List<Long> ids = page.getRecords().stream().map(InformPageModel::getId).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(ids)){
            return ;
        }

        List<InformExportModel> informs = informDao.listInformByIds(ids);
        List<InformExportVo> informExportVos = ConvertUtils.sourceToTarget(informs, InformExportVo.class);

        HttpServletResponse response = HttpServletLocalThread.getResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("举报任务情况"+LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(),InformExportVo.class).sheet("1").doWrite(informExportVos);
    }

    @Override
    public void dispatcher(Long id, Long areaId) {
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
            inform.setAssignment(AssignmentEnum.ASSIGNED_FAIL.getCode());
            inform.setUpdateTime(new Date());
            inform.setUpdateBy(UserAuthInfoContext.getUserName());
        }else {
            inform.setAreaId(area.getId())
                    .setAssignment(AssignmentEnum.ASSIGNED.getCode());
            inform.setUpdateTime(new Date());
            inform.setUpdateBy(UserAuthInfoContext.getUserName());
        }

        updateById(inform);
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

        LambdaUpdateWrapper<Inform> updateWrapper = new LambdaUpdateWrapper<Inform>()
                .set(Inform::getAssignment, AssignmentEnum.RETURNED.getCode())
                .set(Inform::getAreaId, parentId.getId())
                .set(Inform::getUpdateBy, UserAuthInfoContext.getUserName())
                .set(Inform::getUpdateTime, LocalDateTime.now())
                .eq(Inform::getId, id)
                .eq(Inform::getAssignment, AssignmentEnum.ASSIGNED.getCode());
        update(updateWrapper);

        //store inform refund
        InformRefund informRefund = new InformRefund();
        informRefund.setInformId(informById.getId());
        informRefund.setReason(refundReason);
        informRefund.setCreateTime(new Date());
        informRefund.setCreateBy(UserAuthInfoContext.getUserName());
        informRefundService.save(informRefund);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void check(Long id, InformCheckReqVo informVo) {
        Inform informById = getInformById(id);
        if (Objects.isNull(informById)) {
            throw new ServiceException("举报信息不存在");
        }

        InformCheck informCheckByInformId = informCheckService.getByInformId(id);
        if (Objects.isNull(informCheckByInformId)){
            //store informCheck
            InformCheck informCheck = ConvertUtils.sourceToTarget(informVo, InformCheck.class);
            informCheck.setCheckTime(LocalDateTime.of(informVo.getCheckTime(), LocalTime.of(0,0)));
            informCheck.setInformId(informById.getId());
            informCheck.setCreateBy(UserAuthInfoContext.getUserName());
            informCheck.setCreateTime(new Date());
            informCheckService.save(informCheck);
        }else {
            BeanUtils.copyProperties(informVo,informCheckByInformId,"id");
            informCheckByInformId.setCheckTime(LocalDateTime.of(informVo.getCheckTime(), LocalTime.of(0,0)));
            informCheckByInformId.setUpdateBy(UserAuthInfoContext.getUserName());
            informCheckByInformId.setUpdateTime(new Date());
            informCheckService.updateById(informCheckByInformId);
        }

        // reward
        InformReward rewardByInformId = informRewardService.getByInformId(id);
        if (Objects.isNull(rewardByInformId)){
            InformReward informReward = ConvertUtils.sourceToTarget(informVo, InformReward.class);
            informReward.setCreateTime(new Date());
            informReward.setCreateBy(UserAuthInfoContext.getUserName());
            informRewardService.save(informReward);
        }else{
            BeanUtils.copyProperties(informVo,rewardByInformId,"id");
            rewardByInformId.setUpdateTime(new Date());
            rewardByInformId.setUpdateBy(UserAuthInfoContext.getUserName());
            informRewardService.updateById(rewardByInformId);
        }

        //update checkStatus
        informById.setCheckStatus(informVo.getCheckStatus());
        informById.setUpdateTime(new Date());
        informById.setUpdateBy(UserAuthInfoContext.getUserName());
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
        return Pagination.of(ConvertUtils.sourceToTarget(page.getRecords(), InformPageVo.class), page.getTotal());
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
