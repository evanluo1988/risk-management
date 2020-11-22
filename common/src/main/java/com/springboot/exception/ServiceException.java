package com.springboot.exception;

public class ServiceException extends Exception{
    private String message;

    public ServiceException(String message){
        super(message);
    }
}
