package com.springboot.service;

import com.springboot.domain.UserLock;

public interface UserLockService {
    public UserLock getByLoginName(String loginName);
    public void create(UserLock userLock);
    public void update(UserLock userLock);
 }
