package com.springboot.exception;

/**
 * @author lhf
 * @date 2021/2/23 10:09 上午
 **/
public class InvalidAuthException extends RuntimeException{

    public InvalidAuthException(String message) {
        super(message);
    }
}
