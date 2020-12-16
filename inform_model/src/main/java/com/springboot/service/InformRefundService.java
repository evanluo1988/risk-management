package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.InformRefund;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-12-03
 */
public interface InformRefundService extends IService<InformRefund> {

    List<InformRefund> listRefundByInformId(Long informId);
}
