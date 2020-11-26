package com.springboot.controller;

import com.springboot.ret.ReturnT;
import com.springboot.service.UserService;
import com.springboot.utils.ReturnTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zx on 2020/11/19.
 */
@RestController
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ReturnT login(){
        userService.logout();
        return ReturnTUtils.newCorrectReturnT();
    }
}
