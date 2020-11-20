package com.springboot.utils;

import com.springboot.vo.ReturnT;

public class ReturnTUtils {
    public static<T> ReturnT<T> getReturnT(T obj) {
        ReturnT returnT = new ReturnT();
        returnT.setContent(obj);
        returnT.setCode("SUCESS");
        return returnT;
    }

    public static<T> ReturnT<T> getReturnT(String errMsg){
        ReturnT returnT = new ReturnT();
        returnT.setErrMsg(errMsg);
        returnT.setCode("FAILD");
        return returnT;
    }

    public static<T> ReturnT<T> getReturnT(Exception exception){
        return getReturnT(exception.getMessage());
    }
}
