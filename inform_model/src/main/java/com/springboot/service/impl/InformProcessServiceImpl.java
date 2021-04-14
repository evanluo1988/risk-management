package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.springboot.domain.InformProcess;
import com.springboot.enums.ProcessTypeEnum;
import com.springboot.mapper.InformProcessDao;
import com.springboot.service.InformProcessService;
import com.springboot.utils.ConvertUtils;
import com.springboot.utils.UserAuthInfoContext;
import com.springboot.vo.InformProcessVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author lhf
 * @date 2021/4/1 4:51 下午
 **/
@Service
public class InformProcessServiceImpl extends ServiceImpl<InformProcessDao, InformProcess> implements InformProcessService {
    @Override
    public InformProcess addProcess(Long informId, ProcessTypeEnum processTypeEnum, String opMessage, Long opUserId) {
        final InformProcess informProcess = new InformProcess()
                .setInformId(informId)
                .setProcessType(processTypeEnum)
                .setProcessUserId(opUserId)
                .setProcessMessage(opMessage);

        saveInformProcess(informProcess);
        return informProcess;
    }

    @Override
    public List<InformProcessVo> listProcess(Long id) {
        if (Objects.isNull(id)){
            return Lists.newArrayListWithCapacity(0);
        }

        final LambdaQueryWrapper<InformProcess> queryWrapper = new LambdaQueryWrapper<InformProcess>()
                .eq(InformProcess::getInformId, id);
        final List<InformProcess> list = list(queryWrapper);
        return ConvertUtils.sourceToTarget(list,InformProcessVo.class);
    }

    private void saveInformProcess(InformProcess informProcess) {
        informProcess.setCreateTime(new Date());
        informProcess.setCreateBy(UserAuthInfoContext.getUserName());
        this.save(informProcess);
    }
}
