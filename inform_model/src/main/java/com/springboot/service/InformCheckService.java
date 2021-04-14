package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.InformCheck;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
public interface InformCheckService extends IService<InformCheck> {

    InformCheck getByInformId(Long informId);

    InformCheck getByInformIdAndProcessId(Long informId, Long processId);
}
