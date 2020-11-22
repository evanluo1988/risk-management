package com.springboot.mapper;

import org.apache.ibatis.session.SqlSession;

import javax.annotation.Resource;

/**
 * Created by zx on 2020/7/24.
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
    @Resource(name="mybatisSqlSessionTemplate")
    private SqlSession session;
    private final String path = "com.springboot.dao.";

    private String getMethodPath(String methodType){
        return path + this.getClass().getSimpleName() + "." + methodType;
    }

    @Override
    public void save(T obj) {
        session.insert(getMethodPath("save"), obj);
    }

    @Override
    public void delete(T obj) {
        session.delete(getMethodPath("delete"), obj);
    }

    @Override
    public void delete(Long id) {
        session.delete(getMethodPath("deleteById"), id);
    }

    @Override
    public void update(T obj) {
        session.update(getMethodPath("update"), obj);
    }

    @Override
    public T get(Long id) {
        return session.selectOne(getMethodPath("getById"), id);
    }
}
