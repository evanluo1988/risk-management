package com.springboot.controller;

import com.springboot.domain.User;
import com.springboot.service.UserService;
import com.springboot.ret.ReturnT;
import com.springboot.vo.UserVo;
import com.springboot.vo.UserWithRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author evan
 */
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

    @PostMapping("/add")
    public void createUser(@RequestBody User user){
        userService.create(user);
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
