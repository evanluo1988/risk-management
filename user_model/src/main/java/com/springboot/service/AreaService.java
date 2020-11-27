package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.Area;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-26
 */
public interface AreaService extends IService<Area> {

    /**
     * 根据当前区域ID获取它自己以及下面的区域ID集合
     * @param areaId
     * @return
     */
    public List<Long> findAreaIdsById(Long areaId);

}
