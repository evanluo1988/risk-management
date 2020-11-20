package com.springboot.controller;

import com.springboot.domain.User;
import com.springboot.utils.ServletUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zx on 2020/11/19.
 */
@Controller
@RequestMapping("/logout")
public class LogoutController {
    @GetMapping
    public void login(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("sessionId");
        ServletUtils.clearCookie(request, response, "sessionId");
    }
}
