package com.springboot.controller;

import com.springboot.domain.User;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;


/**
 * Created by zx on 2020/3/7.
 * @CrossOrigin 解决跨域问题，origin="*"代表所有域名都可访问
 * maxAge飞行前响应的缓存持续时间的最大年龄，简单来说就是Cookie的有效期 单位为秒
 * 若maxAge是负数,则代表为临时Cookie,不会被持久化,Cookie信息保存在浏览器内存中,浏览器关闭Cookie就消失
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("{id}")
    public User findUserById(@PathVariable Long id){
//        ValueOperations<String, User> operations = redisTemplate.opsForValue();
//        User user = operations.get(id.toString());
//        if(user == null){
//            user = userService.getById(id);
//            operations.set(id.toString(), user);
//        }
        return  userService.getById(id);
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        user.setPersonId(UUID.randomUUID());
        userService.create(user);

    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }

    @PutMapping
    public void updateUser(@RequestBody User user){
        user.setModifyDate(new Date());
        userService.update(user);
    }

    @PutMapping("{name}")
    public void updateUserEmail(@PathVariable String name){
        userService.updateEmailByName(name);
    }

}
