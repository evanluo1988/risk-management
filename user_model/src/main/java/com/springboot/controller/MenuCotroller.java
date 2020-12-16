package com.springboot.controller;

import com.springboot.service.MenuService;
import com.springboot.utils.UserAuthInfoContext;
import com.springboot.vo.MenuVo;
import com.springboot.ret.ReturnT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuCotroller {
    @Autowired
    private MenuService menuService;

    @GetMapping
    public ReturnT<List<MenuVo>> findMenuList(){
       return menuService.findMenuByUserId(UserAuthInfoContext.getUserId());
    }
}
