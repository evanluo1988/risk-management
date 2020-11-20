package com.springboot.service;

import com.springboot.domain.Product;
import com.springboot.domain.User;

/**
 * Created by zx on 2020/10/8.
 */
public interface UserProductService {
    public void updateUserAndProduct(User user, Product product);
}
