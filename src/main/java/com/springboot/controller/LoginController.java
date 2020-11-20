package com.springboot.controller;


import com.springboot.domain.User;
import com.springboot.utils.ServletUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zx on 2020/11/19.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    private final static Logger logger = LogManager.getLogger(LoginController.class);

    @PostMapping
    public void login(HttpServletRequest request, HttpServletResponse response, @RequestParam String username, @RequestParam String password){
        logger.info("name {" + username + "},password {"+password+"}");
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        request.setAttribute("user", user);
        request.getSession().setAttribute("sessionId", request.getSession().getId());

        ServletUtils.putCookie(response, "sessionId", request.getSession().getId());
        System.out.println(request.getSession().getId());
    }
}
