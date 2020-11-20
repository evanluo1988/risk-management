package com.springboot.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by zx on 2020/11/19.
 */
public class ServletUtils {
    public static String getSessionId(HttpServletRequest request, HttpServletResponse response) {
        return request.getSession().getAttribute("sessionId") != null?request.getSession().getAttribute("sessionId").toString():getCookieValue(request, "sessionId");
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        return cookie == null?null:cookie.getValue();
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(int i = 0; i < cookies.length; ++i) {
                if(cookies[i].getName().equals(name)) {
                    return cookies[i];
                }
            }
        }
        return null;
    }

    public static void addCookie(HttpServletResponse res, String key, String value, int validDays) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(validDays * 3600 * 24);
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    public static void addCookie(HttpServletResponse res, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    public static void putCookie(HttpServletResponse response, String name, String value){
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(100);
        response.addCookie(cookie);
    }

    public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        try {
            if(cookies != null) {
                for(int ex = 0; ex < cookies.length; ++ex) {
                    if(cookies[ex].getName().equals(name)) {
                        Cookie cookie = new Cookie(cookies[ex].getName(), cookies[ex].getValue());
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
            }
        } catch (Exception var6) {
        }
    }


}
