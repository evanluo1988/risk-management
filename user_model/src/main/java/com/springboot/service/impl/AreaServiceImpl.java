package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.springboot.domain.Area;
import com.springboot.enums.EnableEnum;
import com.springboot.enums.RoleEnum;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.AreaDao;
import com.springboot.model.RolePerm;
import com.springboot.service.AreaService;
import com.springboot.util.ConvertUtils;
import com.springboot.utils.RoleUtils;
import com.springboot.utils.UserAuthInfoContext;
import com.springboot.vo.AreaVo;
import org.glassfish.jersey.internal.guava.Sets;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-26
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaDao, Area> implements AreaService {

    @Override
    public Collection<AreaVo> listAreaByParentId(Long parentId) {
        RolePerm highestLevelRole = RoleUtils.getHighestLevelRole(UserAuthInfoContext.getRolePerms());
        if (Objects.isNull(highestLevelRole)){
            throw new ServiceException("用户角色错误");
        }

        Set<Long> areaIds = Sets.newHashSetWithExpectedSize(1);
        if (Objects.nonNull(parentId)){
            areaIds.add(parentId);
        }

        RoleEnum roleEnum = RoleEnum.nameOf(highestLevelRole.getRoleName());
        Collection<AreaVo> areaVos = Lists.newArrayList();
        //一级
        if (Objects.isNull(parentId)){
            if (RoleEnum.SYS_ADMIN == roleEnum){
                Collection<Area> areas = listAreaByParentIds(areaIds);
                areaVos = ConvertUtils.sourceToTarget(areas, AreaVo.class);
            }else if (RoleEnum.REGION_ADMIN == roleEnum){
                Area area = getAreaById(UserAuthInfoContext.getAreaId());
                AreaVo areaVo = ConvertUtils.sourceToTarget(area, AreaVo.class);
                areaVos.add(areaVo);
            }
        }else{
            //二级
            if (RoleEnum.STREET_ADMIN == roleEnum){
                Area area = getAreaById(UserAuthInfoContext.getAreaId());
                AreaVo areaVo = ConvertUtils.sourceToTarget(area, AreaVo.class);
                areaVos.add(areaVo);
            }else {
                Collection<Area> areas = listAreaByParentIds(areaIds);
                areaVos = ConvertUtils.sourceToTarget(areas, AreaVo.class);
            }
        }
        return areaVos;
    }

    private Area getAreaById(Long areaId) {
        if (Objects.isNull(areaId)){
            return null;
        }

        LambdaQueryWrapper<Area> queryWrapper = new LambdaQueryWrapper<Area>()
                .eq(Area::getId, areaId)
                .eq(Area::getEnable, EnableEnum.Y.getCode());
        return getOne(queryWrapper,false);
    }

    private Collection<Area> listAreaByParentIds(Set<Long> parentIds) {
        LambdaQueryWrapper<Area> queryWrapper = new LambdaQueryWrapper<Area>()
                .in(!CollectionUtils.isEmpty(parentIds), Area::getParentId, parentIds)
                .isNull(CollectionUtils.isEmpty(parentIds), Area::getParentId)
                .eq(Area::getEnable, EnableEnum.Y.getCode());
        return list(queryWrapper);
    }

    public List<Long> findAreaIdsById(Long areaId) {
        HashSet<Long> areaIds = Sets.newHashSetWithExpectedSize(1);
        if (Objects.nonNull(areaId)){
            areaIds.add(areaId);
        }

        Collection<Area> areas = listAreaByParentIds(areaIds);
        Set<Long> subAreaIds = areas.stream().map(Area::getId).collect(Collectors.toSet());
        Collection<Area> subAreas = listAreaByParentIds(subAreaIds);

        areas.addAll(subAreas);
        return areas.stream().map(Area::getId).collect(Collectors.toList());
    }
}
