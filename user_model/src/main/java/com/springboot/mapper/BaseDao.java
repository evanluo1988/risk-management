package com.springboot.mapper;

/**
 * Created by zx on 2020/7/24.
 */
public interface BaseDao<T> {
    void save(T obj);

    void delete(T obj);

    void delete(Long id);

    void update(T obj);

    T get(Long id);

}
