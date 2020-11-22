package com.springboot.interceptor;

import com.springboot.utils.HttpServletLocalThread;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zx on 2020/11/18.
 */
public class HttpServletInterceptor extends HandlerInterceptorAdapter {
    public HttpServletInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServletLocalThread.set(request, response);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HttpServletLocalThread.clean();
        super.afterCompletion(request, response, handler, ex);
    }
}
