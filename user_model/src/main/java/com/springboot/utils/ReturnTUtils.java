package com.springboot.utils;

import com.springboot.ret.ReturnT;

public class ReturnTUtils {
    /**
     * 创建一个正确的响应
     * @return  正确的响应
     */
    public static ReturnT newCorrectReturnT(){
        ReturnT returnT = new ReturnT();
        returnT.setCode("SUCCESS");
        returnT.setContent(null);
        returnT.setErrMsg(null);
        return returnT;
    }

    public static<T> ReturnT<T> getReturnT(T obj) {
        ReturnT returnT = new ReturnT();
        returnT.setContent(obj);
        returnT.setCode("SUCCESS");
        return returnT;
    }

    public static<T> ReturnT<T> getReturnT(String errMsg){
        ReturnT returnT = new ReturnT();
        returnT.setErrMsg(errMsg);
        returnT.setCode("FAIL");
        return returnT;
    }

    public static<T> ReturnT<T> getReturnT(Exception exception){
        return getReturnT(exception.getMessage());
    }
}
