package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.InformCheck;
import com.springboot.domain.InformReward;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
public interface InformRewardService extends IService<InformReward> {

    InformReward getByInformId(Long informId);
}
