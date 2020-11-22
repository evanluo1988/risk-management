package com.springboot.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zx on 2020/10/5.
 */
@WebFilter(filterName = "myFilter", urlPatterns = "/users/*", initParams = { @WebInitParam(name = "URL", value = "http://localhost:8888")})
public class MyFilter extends HttpFilter {
    private static Log logger = LogFactory.getLog(MyFilter.class);
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("do my filter");
        request.getSession();
        chain.doFilter(request, response);
    }
}
