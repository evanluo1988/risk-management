package com.springboot.util;

import com.google.common.collect.Lists;

import java.util.List;

public class Utils {
    public static <T> List<T> getList(List<T> list) {
        if(list == null) {
            return Lists.newArrayList();
        }
        return list;
    }
}
