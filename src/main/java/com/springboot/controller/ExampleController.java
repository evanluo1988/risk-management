package com.springboot.controller;

import com.springboot.Application;
import com.springboot.domain.MyXml;
import com.springboot.properties.ConfigServer;
import com.springboot.service.UserService;
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
    @Autowired
    private ConfigServer configServer;

    /**
     *  Application Arguments
     */
    @Autowired
    private ApplicationArguments args;

    @RequestMapping("/example")
    public String example(HttpSession httpSession) {
        httpSession.setAttribute("hello", "world");
        System.out.println(configServer.isEnabled());
        System.out.println(args);
        return "Example!";
    }

    @RequestMapping("/xml")
    public MyXml myXml() {
        MyXml myXml = new MyXml();
        myXml.setName("XML name!");
        return myXml;
    }

}
