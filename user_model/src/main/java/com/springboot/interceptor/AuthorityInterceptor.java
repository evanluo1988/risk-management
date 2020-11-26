package com.springboot.interceptor;

import com.springboot.authority.AuthorityManagement;
import com.springboot.authority.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author evan
 * 权限验证拦截器
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {
    private AuthorityManagement authorityManagement;

    public AuthorityInterceptor(AuthorityManagement authorityManagement){
        this.authorityManagement = authorityManagement;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        authorityManagement.validAuthority(new Subject(request));
        return super.preHandle(request, response, handler);
    }
}
