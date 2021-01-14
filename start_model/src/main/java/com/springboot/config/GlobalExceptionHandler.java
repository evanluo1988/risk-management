package com.springboot.config;

import com.google.common.collect.Lists;
import com.springboot.domain.UserLock;
import com.springboot.exception.ServiceException;
import com.springboot.exception.UserLoginException;
import com.springboot.ret.ReturnT;
import com.springboot.service.UserLockService;
import com.springboot.utils.DateUtils;
import com.springboot.utils.ReturnTUtils;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    @Autowired
    private UserLockService userLockService;

    /**
     * 用户登陆异常
     * UserLoginException拦截器
     * @param e 异常
     * @return  ReturnT
     */
    @ExceptionHandler(UserLoginException.class)
    public ReturnT UserLoginExceptionHandler(UserLoginException e) {
        log.error("UserLoginException {}",e);
        synchronized(this) {
            UserLock userLock = userLockService.getByLoginName(e.getLoginName());
            if(Objects.isNull(userLock)) {
                UserLock ul = new UserLock();
                ul.setLoginCount(1);
                ul.setLoginName(e.getLoginName());
                ul.setCreateTime(DateUtils.currentDateTime());
                userLockService.create(ul);
            }else {
                if(Objects.isNull(userLock.getLockTime())){
                    userLock.setLoginCount(userLock.getLoginCount() + 1);
                    if(userLock.getLoginCount() == 3) {
                        userLock.setLockTime(DateUtils.addMinute(10L));
                    }
                    userLock.setUpdateTime(DateUtils.currentDateTime());
                    userLockService.update(userLock);
                } else {
                    if(LocalDateTime.now().isAfter(userLock.getLockTime())) {
                        userLock.setLoginCount(1);
                        userLock.setLoginName(e.getLoginName());
                        userLock.setCreateTime(DateUtils.currentDate());
                        userLockService.create(userLock);
                    }
                }
            }
        }
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
