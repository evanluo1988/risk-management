package com.springboot.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zx on 2020/11/18.
 */
public class HttpServletLocalThread {
    static ThreadLocal<HttpServletLocalThread.HttpContext> threadLocal = new ThreadLocal();

    public HttpServletLocalThread() {
    }

    public static void set(HttpServletRequest req, HttpServletResponse res) {
        threadLocal.set(new HttpServletLocalThread.HttpContext(req, res));
    }

    public static HttpServletRequest getRequest() {
        return ((HttpServletLocalThread.HttpContext)threadLocal.get()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((HttpServletLocalThread.HttpContext)threadLocal.get()).getResponse();
    }

    public static void clean() {
        threadLocal.remove();
    }

    static class HttpContext {
        private HttpServletRequest request;
        private HttpServletResponse response;

        public HttpContext(HttpServletRequest request, HttpServletResponse response) {
            this.request = request;
            this.response = response;
        }

        public HttpServletRequest getRequest() {
            return this.request;
        }

        public HttpServletResponse getResponse() {
            return this.response;
        }
    }
}
