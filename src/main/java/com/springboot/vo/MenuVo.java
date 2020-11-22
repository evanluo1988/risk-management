package com.springboot.vo;

import java.util.List;

public class MenuVo {
    private Long id;
    private Long parentId;
    private String menuName;
    private String menuDescribe;
    private String menuUrl;
    private String menuType;
    private int menuLevel;

    private List<MenuVo> subMenuVoList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDescribe() {
        return menuDescribe;
    }

    public void setMenuDescribe(String menuDescribe) {
        this.menuDescribe = menuDescribe;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public int getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(int menuLevel) {
        this.menuLevel = menuLevel;
    }

    public List<MenuVo> getSubMenuVoList() {
        return subMenuVoList;
    }

    public void setSubMenuVoList(List<MenuVo> subMenuVoList) {
        this.subMenuVoList = subMenuVoList;
    }
}
