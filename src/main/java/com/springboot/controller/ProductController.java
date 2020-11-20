package com.springboot.controller;

import com.springboot.domain.Product;
import com.springboot.domain.User;
import com.springboot.service.ProductService;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;


/**
 * Created by zx on 2020/3/7.
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public void createUser(@RequestBody Product product){
        productService.saveOrUpdate(product);
    }

    @GetMapping("{id}")
    public Product get(@PathVariable Long id){
        return productService.get(id);
    }

    @GetMapping("/jpa/{id}")
    public Product jpaGet(@PathVariable Long id){
        return productService.jpaGet(id);
    }

    @PostMapping("/jpa")
    public Product jpaGet(@RequestBody Product product){
        return productService.jpaSave(product);
    }
}
