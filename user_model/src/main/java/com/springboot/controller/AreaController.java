package com.springboot.controller;


import com.springboot.ret.ReturnT;
import com.springboot.service.AreaService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.AreaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-26
 */
@RestController
@RequestMapping("/areas")
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
     * 传areaId查自己及自己下的所有区域（带递归）
     * 如果不传areaId，areaId = user.areaId
     * @param parentId
     * @return
     */
    @GetMapping(value = {"/list/{parentId}","/list"})
    public ReturnT listAreaByParentId(@PathVariable(value = "parentId",required = false) Long parentId){
        Collection<AreaVo> areaVos = areaService.listAreaByParentId(parentId);
        return ReturnTUtils.getReturnT(areaVos);
    }

    /**
     * 传areaId，查下一级区域（不递归）
     * 如果不传areaId,areaId=user.areaId
     * @param parentId
     * @return
     */
    @GetMapping(value = {"/subs/{parentId}","/subs"})
    public ReturnT subsAreaByParentId(@PathVariable(value = "parentId",required = false) Long parentId){
        Collection<AreaVo> areaVos = areaService.subsAreaByParentId(parentId);
        return ReturnTUtils.getReturnT(areaVos);
    }

    /**
     * 下发列表区域
     * @return
     */
    @GetMapping("/getDispatcherAreaList")
    public ReturnT getDispatcherAreaList(){
        Collection<AreaVo> areaVos = areaService.getDispatcherAreaList();
        return ReturnTUtils.getReturnT(areaVos);
    }
}
