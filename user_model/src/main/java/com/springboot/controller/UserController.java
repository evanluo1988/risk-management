package com.springboot.controller;

import com.springboot.domain.User;
import com.springboot.service.UserService;
import com.springboot.ret.ReturnT;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.UserVo;
import com.springboot.vo.UserWithRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author evan
 */
@Validated
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ReturnT<List<UserVo>> findAllUsers(){
        return  userService.findAllUsers();
    }

    @GetMapping("/view/{id}/role")
    public ReturnT<UserWithRoleVo> findUserWithRoleById(@PathVariable Long id){
        return userService.findWithRoleById(id);
    }

    @GetMapping("/view/{id}")
    public ReturnT<UserVo> findUserById(@PathVariable Long id){
        return  userService.getById(id);
    }

    @Validated(UserVo.UserAddGroup.class)
    @PostMapping("/add")
    public ReturnT createUser(@RequestBody @Valid UserVo userVo){
        userService.create(userVo);
        return ReturnTUtils.newCorrectReturnT();
    }

    @DeleteMapping("/del/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody User user){
        userService.update(user);
    }

}
