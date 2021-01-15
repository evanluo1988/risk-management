package com.springboot.config;

import com.google.common.collect.Lists;
import com.springboot.exception.ServiceException;
import com.springboot.exception.UserLoginException;
import com.springboot.ret.ReturnT;
import com.springboot.utils.ReturnTUtils;
import com.springboot.utils.UserLoginCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常拦截器
 *
 * @Author 刘宏飞
 * @Date 2020/11/24 14:56
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 用户登陆异常
     * UserLoginException拦截器
     * @param e 异常
     * @return  ReturnT
     */
    @ExceptionHandler(UserLoginException.class)
    public ReturnT UserLoginExceptionHandler(UserLoginException e) {
        log.error("UserLoginException {}",e);
        UserLoginCache.lock(e.getLoginName());
        return ReturnTUtils.getReturnT(e);
    }

    /**
     * ServiceException拦截器
     * @param e 异常
     * @return  ReturnT
     */
    @ExceptionHandler(ServiceException.class)
    public ReturnT ServiceExceptionHandler(ServiceException e){
        log.error("ServiceException {}",e);
        return ReturnTUtils.getReturnT(e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ReturnT handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) throws IOException {
        log.error("HttpMessageNotReadableException：{}", ex);
        return ReturnTUtils.getReturnT(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ReturnT handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException:{}", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ReturnTUtils.getReturnT(ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ReturnT handleConstraintViolationExceptions(
            ConstraintViolationException e) {
        List<String> errors = Lists.newArrayList();
        e.getConstraintViolations().forEach(error -> errors.add(error.getMessage()));
        log.error("ConstraintViolationException:{}", e);
        return ReturnTUtils.getReturnT(e);
    }

    /**
     * 异常处理
     * @param e 异常
     * @return  标准响应
     */
    @ExceptionHandler(Exception.class)
    public ReturnT ExceptionHandler(Exception e){
        log.error("Exception : {}",e);
        return ReturnTUtils.getReturnT(e);
    }

}
