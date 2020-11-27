package com.springboot.controller;


import com.springboot.ret.ReturnT;
import com.springboot.service.AreaService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.AreaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Path;
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

    @GetMapping(value = {"/list/{parentId}","/list"})
    public ReturnT listAreaByParentId(@PathVariable(value = "parentId",required = false) Long parentId){
        Collection<AreaVo> areaVos = areaService.listAreaByParentId(parentId);
        return ReturnTUtils.getReturnT(areaVos);
    }
}
