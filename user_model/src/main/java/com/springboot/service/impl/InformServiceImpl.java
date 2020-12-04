package com.springboot.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.springboot.domain.*;
import com.springboot.enums.EnableEnum;
import com.springboot.enums.InformAssignmentEnum;
import com.springboot.enums.InformCheckStatusEnum;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.InformDao;
import com.springboot.model.InformPageModel;
import com.springboot.page.Pagination;
import com.springboot.service.*;
import com.springboot.util.ConvertUtils;
import com.springboot.utils.ServerCacheUtils;
import com.springboot.utils.UserAuthInfoContext;
import com.springboot.vo.InformImportVo;
import com.springboot.vo.InformPageVo;
import com.springboot.vo.InformViewVo;
import com.springboot.vo.InformVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Transactional(rollbackFor = Exception.class)
    public void dispatcher(Long id, Long areaId) {
        Area area = areaService.getAreaById(areaId);
        if (Objects.isNull(area)) {
            throw new ServiceException("区域信息不存在");
        }

        Inform inform = getInformById(id);
        if (Objects.isNull(inform)) {
            throw new ServiceException("举报信息不存在");
        }

        inform.setAreaId(areaId)
                .setAssignment(InformAssignmentEnum.ASSIGNED.getCode());
        inform.setUpdateTime(new Date());
        inform.setUpdateBy(UserAuthInfoContext.getUserName());
        updateById(inform);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void goBack(Long id, String refundReason) {
        Inform informById = getInformById(id);
        if (Objects.isNull(informById)) {
            throw new ServiceException("举报信息不存在");
        }

        if (!informById.getAssignment().equalsIgnoreCase(InformAssignmentEnum.ASSIGNED.getCode())) {
            log.error("当前状态不是已分派状态，不允许退回");
            throw new ServiceException("当前状态不允许退回");
        }

        if (informById.getCheckStatus().equalsIgnoreCase(InformCheckStatusEnum.CHECKED.getCode())){
            log.error("已经核查的举报不允许撤回");
            throw new ServiceException("已核查不允许退回");
        }

        LambdaUpdateWrapper<Inform> updateWrapper = new LambdaUpdateWrapper<Inform>()
                .set(Inform::getAssignment, InformAssignmentEnum.RETURNED.getCode())
                .set(Inform::getAreaId, null)
                .set(Inform::getUpdateBy, UserAuthInfoContext.getUserName())
                .set(Inform::getUpdateTime, LocalDateTime.now())
                .eq(Inform::getId, id)
                .eq(Inform::getAssignment, InformAssignmentEnum.ASSIGNED.getCode());
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
    public void check(Long id, InformVo informVo) {
        Inform informById = getInformById(id);
        if (Objects.isNull(informById)) {
            throw new ServiceException("举报信息不存在");
        }
        //store informCheck
        InformCheck informCheck = ConvertUtils.sourceToTarget(informVo, InformCheck.class);
        informCheck.setInformId(informById.getId());
        informCheck.setCreateBy(UserAuthInfoContext.getUserName());
        informCheck.setCreateTime(new Date());
        informCheckService.save(informCheck);

        //update checkStatus
        informById.setCheckStatus(informVo.getCheckStatus());
        informById.setUpdateTime(new Date());
        informById.setUpdateBy(UserAuthInfoContext.getUserName());
        updateById(informById);
    }

    @Override
    public Pagination<InformPageVo> informPage(String source, String checkStatus,
                                               LocalDate informTimeStart, LocalDate informTimeEnd,
                                               String rewardContent, String informName,
                                               String verification, Boolean overdue,
                                               LocalDate checkTimeStart, LocalDate checkTimeEnd,
                                               Long areaId, Integer pageNo, Integer pageSize) {

        InformCheckStatusEnum informCheckStatusEnum = InformCheckStatusEnum.descOf(checkStatus);
        Page<InformPageModel> page = informDao.informPage(source, Objects.isNull(informCheckStatusEnum) ? null : informCheckStatusEnum.getCode(), informTimeStart, informTimeEnd, rewardContent, informName, verification, overdue, checkTimeStart, checkTimeEnd, areaId, new Page(pageNo, pageSize));
        return Pagination.of(ConvertUtils.sourceToTarget(page.getRecords(), InformPageVo.class), page.getTotal());
    }

    @Override
    public void del(Long id) {
        Inform informById = getInformById(id);
        if (Objects.isNull(informById)){
            throw new ServiceException("举报信息不存在");
        }

        if (informById.getAssignment().equalsIgnoreCase(InformAssignmentEnum.ASSIGNED.getCode())){
            throw new ServiceException("已分派的不能删除");
        }

        LambdaUpdateWrapper<Inform> updateWrapper = new LambdaUpdateWrapper<Inform>()
                .set(Inform::getEnable, EnableEnum.N.getCode())
                .eq(Inform::getId, id);
        this.update(updateWrapper);
    }

    @Override
    public InformViewVo view(Long id) {
        Inform informById = getInformById(id);
        InformViewVo informViewVo = new InformViewVo();
        informViewVo.setInform(informById);
        if (Objects.nonNull(informById)){
            InformCheck informCheck = informCheckService.getByInformId(id);
            informViewVo.setInformCheck(informCheck);
        }
        return informViewVo;
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
            log.debug("解析到一条数据:{}", JSON.toJSONString(informImportVo));
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
            informService.importInforms0(data);
        }

        private void checkUnitCorrect(Collection<InformImportVo> data) {
            data.forEach(informImportVo -> {
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
