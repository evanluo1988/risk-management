package com.springboot.service;

import com.springboot.domain.Menu;
import com.springboot.vo.MenuVo;
import com.springboot.vo.ReturnT;

import java.util.List;

/**
 * @author evan
 */
public interface MenuService {

    public ReturnT<List<MenuVo>> findMenuByUserId(Long userId);

    public List<Menu> findAllMenus();
}
