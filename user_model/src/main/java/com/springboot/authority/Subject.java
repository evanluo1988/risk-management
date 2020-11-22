package com.springboot.authority;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author evan
 * 主体
 */
public class Subject {

    private HttpServletRequest request;
    private List<Authority> authorities;

    public Subject(HttpServletRequest request){
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
