package com.springboot.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.springboot.domain.*;
import com.springboot.enums.EnableEnum;
import com.springboot.enums.InformAssignmentEnum;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.InformDao;
import com.springboot.service.*;
import com.springboot.utils.UserAuthInfoContext;
import com.springboot.vo.InformImportVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
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

    @Override
    public void importInforms(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), InformImportVo.class, new InformsUploadDataListener(this)).sheet().headRowNumber(2).doRead();
        } catch (IOException e) {
            log.error("导入举报信息异常：{}",e);
            throw new ServiceException("导入举报信息错误");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importInforms0(InformImportVo data) {
        Inform inform = data.toInform();
        InformCheck informCheck = data.toInformCheck();
        InformPerson informPerson = data.toInformPerson();
        InformReward informReward = data.toInformReward();
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dispatcher(Long id, Long areaId) {
        Area area = areaService.getAreaById(areaId);
        if (Objects.isNull(area)){
            throw new ServiceException("区域信息不存在");
        }

        Inform inform = getInformById(id);
        if (Objects.isNull(inform)){
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
    public void goBack(Long id) {
        Inform informById = getInformById(id);
        if (Objects.isNull(informById)){
            throw new ServiceException("举报信息不存在");
        }

        if (!informById.getAssignment().equalsIgnoreCase(InformAssignmentEnum.ASSIGNED.getCode())){
            throw new ServiceException("当前状态不允许撤回");
        }

        LambdaUpdateWrapper<Inform> updateWrapper = new LambdaUpdateWrapper<Inform>()
                .set(Inform::getAssignment, InformAssignmentEnum.RETURNED.getCode())
                .set(Inform::getAreaId, null)
                .set(Inform::getUpdateBy,UserAuthInfoContext.getUserName())
                .set(Inform::getUpdateTime, LocalDateTime.now())
                .eq(Inform::getId,id)
                .eq(Inform::getAssignment,InformAssignmentEnum.ASSIGNED.getCode());
        update(updateWrapper);
    }

    private Inform getInformById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }

        LambdaQueryWrapper<Inform> queryWrapper = new LambdaQueryWrapper<Inform>()
                .eq(Inform::getEnable, EnableEnum.Y.getCode())
                .eq(Inform::getId, id);
        return getOne(queryWrapper,false);
    }

    @Slf4j
    private static class InformsUploadDataListener extends AnalysisEventListener<InformImportVo> {

        private InformService informService;

        private List<InformImportVo> data = Lists.newArrayList();
        private List<String> errors = Lists.newArrayList();

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
            for (InformImportVo datum : data) {
                try {
                    informService.importInforms0(datum);
                }catch (Exception e){
                    log.error("导入数据出错：{}",e);
                    errors.add(datum.getClueNumber());
                }
            }

            if (!CollectionUtils.isEmpty(errors)){
                throw new ServiceException("导入失败的线索编号："+ Arrays.toString(errors.toArray()));
            }
        }
    }
}
