package com.springboot.service;

import com.springboot.domain.Product;

/**
 * Created by zx on 2020/8/5.
 */
public interface ProductService {
    public void saveOrUpdate(Product product);
    public Product get(Long id);
    public Product jpaGet(Long id);
    public Product jpaSave(Product product);
}
