package com.springboot.controller;

import com.springboot.service.MenuService;
import com.springboot.vo.MenuVo;
import com.springboot.vo.ReturnT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuCotroller {
    @Autowired
    private MenuService menuService;

    @GetMapping("/user/{userId}")
    public ReturnT<List<MenuVo>> findMenuListByUserId(@PathVariable Long userId){
       return menuService.findMenuByUserId(userId);
    }
}
