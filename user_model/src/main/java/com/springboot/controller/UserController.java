package com.springboot.controller;

import com.springboot.page.PageIn;
import com.springboot.page.Pagination;
import com.springboot.service.UserService;
import com.springboot.ret.ReturnT;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author evan
 */
@Validated
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public ReturnT<UserInfoVo> info(){
        UserInfoVo userInfoVo = userService.info();
        return ReturnTUtils.getReturnT(userInfoVo);
    }

    @GetMapping("/list")
    public ReturnT<Pagination<UserPageVo>> findUsers(UserVo userVo){
        return ReturnTUtils.getReturnT(userService.findUsers(userVo));
    }

    @GetMapping("/view/{id}/role")
    public ReturnT<UserWithRoleVo> findUserWithRoleById(@PathVariable Long id){
        return userService.findWithRoleById(id);
    }

    @GetMapping("/view/{id}")
    public ReturnT<UserVo> findUserById(@PathVariable Long id){
        return  ReturnTUtils.getReturnT(userService.getById(id));
    }

    @Validated(UserVo.UserAddGroup.class)
    @PostMapping("/add")
    public ReturnT createUser(@RequestBody @Valid RegUserVo userVo){
        userService.create(userVo);
        return ReturnTUtils.newCorrectReturnT();
    }

    @DeleteMapping("/del/{id}")
    public ReturnT deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return ReturnTUtils.newCorrectReturnT();
    }

    @Validated(UserVo.UserUpdateGroup.class)
    @PutMapping("/update")
    public ReturnT updateUser(@RequestBody @Valid RegUserVo regUserVo){
        userService.update(regUserVo);
        return ReturnTUtils.newCorrectReturnT();
    }


    @Validated(UserVo.UserUpdatePasswordGroup.class)
    @PutMapping("/password")
    public ReturnT updateUserPassword(@RequestBody @Valid RegUserVo regUserVo){
        userService.updateUserPassword(regUserVo);
        return ReturnTUtils.newCorrectReturnT();
    }

}
