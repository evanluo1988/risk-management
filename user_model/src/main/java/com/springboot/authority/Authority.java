package com.springboot.authority;

/**
 * @author evan
 */
public interface Authority<T> {
    public T getPremission();
}
