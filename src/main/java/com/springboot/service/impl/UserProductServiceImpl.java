package com.springboot.service.impl;

import com.springboot.dao.ProductRepository;
import com.springboot.domain.Product;
import com.springboot.domain.User;
import com.springboot.service.ProductService;
import com.springboot.service.UserProductService;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zx on 2020/10/8.
 */
@Service
public class UserProductServiceImpl implements UserProductService{
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserAndProduct(User user, Product product) {
        userService.create(user);
        productService.jpaSave(product);

    }
}
