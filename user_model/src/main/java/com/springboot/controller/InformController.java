package com.springboot.controller;


import com.springboot.ret.ReturnT;
import com.springboot.service.InformService;
import com.springboot.utils.ReturnTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
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
}
