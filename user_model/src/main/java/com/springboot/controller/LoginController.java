package com.springboot.controller;

import com.springboot.ret.ReturnT;
import com.springboot.service.UserService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by zx on 2020/11/19.
 */
@Validated
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Validated(UserVo.LoginGroup.class)
    @PostMapping
    public ReturnT login(@RequestBody @Valid UserVo user){
        userService.login(user);
        return ReturnTUtils.newCorrectReturnT();
    }
}
