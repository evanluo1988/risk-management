package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.domain.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    public List<Menu> findAllMenus();

    public List<Menu> findMenuListByUserId(Long userId);

}
