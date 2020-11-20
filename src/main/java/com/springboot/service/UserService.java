package com.springboot.service;

import com.springboot.domain.User;

/**
 * Created by zx on 2020/3/7.
 */
public interface UserService {
    public void create(User user);

    public void deleteById(Long id);

    public User getById(Long id);

    public void update(User user);

    public void updateEmailByName(String name);
}
