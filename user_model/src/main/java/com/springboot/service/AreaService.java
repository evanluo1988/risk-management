package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.Area;
import com.springboot.vo.AreaVo;

import java.util.Collection;

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
     * 根据areaId获取自己和下面所有的区域ID（递归）
     * @param areaId
     * @return
     */
    Collection<AreaVo> listAreaByParentId(Long areaId);


    /**
     * 根据区域ID获取下一级的区域（不递归）
     * @param areaId
     * @return
     */
    Collection<AreaVo> subsAreaByParentId(Long areaId);

    /**
     * 根据当前区域ID获取它下面的区域ID集合
     * @param areaId
     * @return
     */
    List<Long> findAreaIdsById(Long areaId);

    /**
     * 根据当前区域ID获取它下面的区域ID集合
     * @param areaId
     * @param self 包含自己
     * @return
     */
    List<Long> findAreaIdsById(Long areaId, boolean self);

    /**
     * 根据AreaId查询Area
     * @param areaId
     * @return
     */
    Area getAreaById(Long areaId);


    Area getArea(String entName);
}
