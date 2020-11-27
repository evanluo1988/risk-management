package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.Area;
import com.springboot.vo.AreaVo;

import java.util.Collection;

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
     * 根据parentId获取
     * @param parentId
     * @return
     */
    Collection<AreaVo> listAreaByParentId(Long parentId);
}
