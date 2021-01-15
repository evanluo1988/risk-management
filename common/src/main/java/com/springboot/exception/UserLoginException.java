package com.springboot.exception;

public class UserLoginException extends RuntimeException{
    private String loginName;

    public UserLoginException(String message){
        super(message);
    }

    public UserLoginException(String message, String loginName){
        super(message);
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }
}
