package com.springboot.controller;

import com.springboot.domain.Product;
import com.springboot.domain.User;
import com.springboot.service.UserProductService;
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
@RequestMapping("/users/products")
public class UserProductController {
    @Autowired
    private UserProductService userProductService;


    @GetMapping("{id}/{name}")
    public void updateUserAndProduct(@PathVariable Long id, @PathVariable String name){
        User user = new User();
        user.setId(id);
        user.setName(name);

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        userProductService.updateUserAndProduct(user, product);
    }

}
