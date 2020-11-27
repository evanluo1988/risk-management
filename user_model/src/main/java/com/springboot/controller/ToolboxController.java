package com.springboot.controller;

import com.springboot.ret.ReturnT;
import com.springboot.utils.ReturnTUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 刘宏飞
 * @Date 2020/11/27 16:21
 * @Version 1.0
 */
@RestController
@RequestMapping("/toolbox")
public class ToolboxController {

    @GetMapping
    public ReturnT importExcel(){

        return ReturnTUtils.newCorrectReturnT();
    }

    public void export(){

    }
}
