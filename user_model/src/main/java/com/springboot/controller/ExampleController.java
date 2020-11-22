package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zx on 2020/3/7.
 */
@RestController
public class ExampleController {

    /**
     *  Application Arguments
     */
    @Autowired
    private ApplicationArguments args;

    @RequestMapping("/example")
    public String example(HttpSession httpSession) {
        httpSession.setAttribute("hello", "world");
        System.out.println(args);
        return "Example!";
    }

}
