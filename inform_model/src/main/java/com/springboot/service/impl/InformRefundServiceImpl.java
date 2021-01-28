package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.springboot.domain.InformRefund;
import com.springboot.mapper.InformRefundDao;
import com.springboot.service.InformRefundService;
import com.springboot.utils.ConvertUtils;
import com.springboot.vo.InformRefundOutputVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-12-03
 */
@Service
public class InformRefundServiceImpl extends ServiceImpl<InformRefundDao, InformRefund> implements InformRefundService {

    @Override
    public List<InformRefundOutputVo> listRefundByInformId(Long informId) {
        if (Objects.isNull(informId)){
            return Lists.newArrayListWithCapacity(0);
        }
        LambdaQueryWrapper<InformRefund> queryWrapper = new LambdaQueryWrapper<InformRefund>()
                .eq(InformRefund::getInformId, informId);
        List<InformRefund> list = list(queryWrapper);
        return ConvertUtils.sourceToTarget(list, InformRefundOutputVo.class);
    }
}
