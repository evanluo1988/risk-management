package com.springboot.service.impl;

import com.springboot.dao.ProductDao;
import com.springboot.dao.ProductRepository;
import com.springboot.domain.Product;
import com.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by zx on 2020/8/6.
 */
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public void saveOrUpdate(Product product) {
        productDao.saveOrUpdate(product);
    }

    @Override
    public Product get(Long id) {
        return productDao.get(id);
    }

    @Override
    public Product jpaGet(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product jpaSave(Product product) {
        return productRepository.save(product);
    }

}
