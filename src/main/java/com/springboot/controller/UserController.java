package com.springboot.controller;

import com.springboot.domain.User;
import com.springboot.service.UserService;
import com.springboot.vo.ReturnT;
import com.springboot.vo.UserVo;
import com.springboot.vo.UserWithRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author evan
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("{id}/role")
    public ReturnT<UserWithRoleVo> findUserWithRoleById(@PathVariable Long id){
        return userService.findUserWithRoleById(id);
    }

    @GetMapping("{id}")
    public ReturnT<UserVo> findUserById(@PathVariable Long id){
        return  userService.getById(id);
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.create(user);

    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }

    @PutMapping
    public void updateUser(@RequestBody User user){
        userService.update(user);
    }

    @PutMapping("{name}")
    public void updateUserEmail(@PathVariable String name){
        userService.updateEmailByName(name);
    }

}
