package com.springboot.controller;


import com.springboot.ret.ReturnT;
import com.springboot.service.InformService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.InformVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
@Validated
@RestController
@RequestMapping("/informs")
public class InformController {

    @Autowired
    private InformService informService;

    @PostMapping("/import")
    public ReturnT importInforms(MultipartFile file){
        informService.importInforms(file);
        return ReturnTUtils.newCorrectReturnT();
    }

    @Validated(InformVo.DispatcherGroup.class)
    @PutMapping("/dispatcher/{id}")
    public ReturnT dispatcher(@PathVariable("id") Long id,
                              @RequestBody @Valid InformVo informVo){
        informService.dispatcher(id,informVo.getAreaId());
        return ReturnTUtils.newCorrectReturnT();
    }

    @PutMapping("/return/{id}")
    public ReturnT goBack(@PathVariable("id") Long id){
        informService.goBack(id);
        return ReturnTUtils.newCorrectReturnT();
    }

    @PutMapping("/check/{id}")
    public ReturnT check(@PathVariable("id") Long id){
        return ReturnTUtils.newCorrectReturnT();
    }
}
