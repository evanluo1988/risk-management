package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.InformCheck;
import com.springboot.domain.InformReward;
import com.springboot.mapper.InformRewardDao;
import com.springboot.service.InformRewardService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
@Service
public class InformRewardServiceImpl extends ServiceImpl<InformRewardDao, InformReward> implements InformRewardService {

    @Override
    public InformReward getByInformId(Long informId) {
        if (Objects.isNull(informId)){
            return null;
        }
        LambdaQueryWrapper<InformReward> queryWrapper = new LambdaQueryWrapper<InformReward>()
                .eq(InformReward::getInformId, informId);
        return getOne(queryWrapper);
    }
}
