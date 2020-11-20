package com.springboot.interceptor;

import com.springboot.utils.ServletUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zx on 2020/11/19.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String sessionId = ServletUtils.getSessionId(request, response);
        if(sessionId != null && request.getSession().getId().equals(sessionId)){
            return super.preHandle(request, response, handler);
        }
        return false;
    }
}
