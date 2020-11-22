package com.springboot.service.impl;

import com.springboot.domain.Menu;
import com.springboot.domain.Permission;
import com.springboot.mapper.MenuMapper;
import com.springboot.mapper.PermMapper;
import com.springboot.model.PremWithMenuId;
import com.springboot.service.MenuService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.MenuVo;
import com.springboot.ret.ReturnT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author evan
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private PermMapper permMapper;

    @Override
    public ReturnT<List<MenuVo>> findMenuByUserId(Long userId) {
        List<Menu> menuList = menuMapper.findMenuListByUserId(userId);
        if(CollectionUtils.isEmpty(menuList)) {
            return ReturnTUtils.getReturnT(new ArrayList<>());
        }

        List<MenuVo> menuVoList = new ArrayList<>();
        for(Menu menu : menuList) {
            menuVoList.add(convertMenuToVo(menu));
        }

        List<MenuVo> rootList = menuVoList.stream().filter(menu -> menu.getParentId() == null).collect(Collectors.toList());
        List<MenuVo> aboveList = null;
        aboveList = rootList;
        menuVoList.removeAll(aboveList);
        while(!menuVoList.isEmpty()){
            List<MenuVo> remove = new ArrayList<>();
            List<MenuVo> abvTemp = new ArrayList<>();
            for(MenuVo menu : menuVoList) {
                for (MenuVo abvMenu : aboveList) {
                    if (menu.getParentId().equals(abvMenu.getId())) {
                        abvMenu.getSubMenuVoList().add(menu);
                        remove.add(menu);
                        abvTemp.add(menu);
                    }
                }
            }
            menuVoList.removeAll(remove);
            aboveList = abvTemp;
        }

        return ReturnTUtils.getReturnT(rootList);
    }

    @Override
    public List<Menu> findAllMenus() {
        List<Menu> menuList = menuMapper.findAllMenus();
        if(CollectionUtils.isEmpty(menuList)){
            return new ArrayList<>();
        }
        List<PremWithMenuId> premWithMenuIdList = permMapper.findWithMenuIdByMenuIds(menuList.stream().map(Menu::getId).collect(Collectors.toList()));

        if(CollectionUtils.isEmpty(premWithMenuIdList)){
            return menuList;
        }
        for(Menu menu : menuList){
            menu.setPermissionList(new ArrayList<>());
            List<Permission> permissionList = premWithMenuIdList.stream().filter(item -> item.getMenuId().equals(menu.getId())).collect(Collectors.toList());
            menu.setPermissionList(permissionList);
        }

        return menuList;
    }

    private MenuVo convertMenuToVo(Menu menu){
        MenuVo menuVo = new MenuVo();
        BeanUtils.copyProperties(menu, menuVo);
        menuVo.setSubMenuVoList(new ArrayList<>());
        return menuVo;
    }

}
