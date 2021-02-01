package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.springboot.domain.Area;
import com.springboot.enums.EnableEnum;
import com.springboot.enums.RoleEnum;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.AreaDao;
import com.springboot.model.RolePerm;
import com.springboot.service.AreaService;
import com.springboot.service.RiskDetectionService;
import com.springboot.service.remote.GeoRemoteService;
import com.springboot.utils.*;
import com.springboot.vo.AreaVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private RiskDetectionService riskDetectionService;
    @Autowired
    private GeoRemoteService geoRemoteService;
    @Value("${map.key}")
    private String key;

    /**
     *
     * @param parentId
     * @return
     */
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

        List<Area> areaList = findAreasById(Objects.isNull(parentId)?UserAuthInfoContext.getAreaId():parentId, true);
        return ConvertUtils.sourceToTarget(areaList, AreaVo.class);
    }

    @Override
    public Collection<AreaVo> subsAreaByParentId(Long parentId) {
        HashSet<Long> ids = new HashSet<>();
        if (Objects.nonNull(parentId)){
            ids.add(parentId);
        }
        return ConvertUtils.sourceToTarget(listAreaByParentIds(ids),AreaVo.class);
    }

    @Override
    public Area getAreaById(Long areaId) {
        if (Objects.isNull(areaId)){
            return null;
        }

        LambdaQueryWrapper<Area> queryWrapper = new LambdaQueryWrapper<Area>()
                .eq(Area::getId, areaId)
                .eq(Area::getEnable, EnableEnum.Y.getCode());
        return getOne(queryWrapper,false);
    }

    @Override
    public Area getArea(String entName) {
        Area area = null;
        String entAddress = riskDetectionService.getEntAddress(entName);
        GeoRemoteService.GeoResponse geoResponse = geoRemoteService.geo(key, entAddress);
        if (geoResponse.succ()){
            String location = geoResponse.getGeocodes().get(0).getLocation();

            if (StringUtils.isNotBlank(location)){
                GeoRemoteService.ReGeoResponse regeo = geoRemoteService.regeo(key, location, "base", "false", "1");
                if (regeo.succ()){
                    String township = regeo.getRegeocode().getAddressComponent().getTownship();
                    area = ServerCacheUtils.getAreaLikeName(township);
                }
            }
        }
        return area;
    }

    private Collection<Area> listAreaByParentIds(Set<Long> parentIds) {
        LambdaQueryWrapper<Area> queryWrapper = new LambdaQueryWrapper<Area>()
                .in(!CollectionUtils.isEmpty(parentIds), Area::getParentId, parentIds)
                .isNull(CollectionUtils.isEmpty(parentIds), Area::getParentId)
                .eq(Area::getEnable, EnableEnum.Y.getCode());
        return list(queryWrapper);
    }

    @Override
    public List<Long> findAreaIdsById(Long areaId) {
        return findAreaIdsById(areaId, true);
    }

    @Override
    public List<Long> findAreaIdsById(Long areaId, boolean self) {
        List<Area> areaList = findAreasById(areaId, self);
        return Utils.getList(areaList).stream().map(Area::getId).collect(Collectors.toList());
    }

    private List<Area> findAreasById(Long areaId, boolean self) {
        List<Area> areaList = Lists.newArrayList();
        HashSet<Long> areaIds = Sets.newHashSetWithExpectedSize(1);
        if (Objects.nonNull(areaId)){
            areaIds.add(areaId);
            areaList.add(ServerCacheUtils.getAreaById(areaId));
        }

        Collection<Area> areas = listAreaByParentIds(areaIds);
        areaList.addAll(areas);
        while(!CollectionUtils.isEmpty(areas)) {
            Set<Long> subAreaIds = areas.stream().map(Area::getId).collect(Collectors.toSet());
            areas = listAreaByParentIds(subAreaIds);
            areaIds.addAll(subAreaIds);
            areaList.addAll(areas);
        }

        if(Objects.nonNull(areaId) && !self){
            areaIds.remove(areaId);
        }
        return areaList;
    }

}
