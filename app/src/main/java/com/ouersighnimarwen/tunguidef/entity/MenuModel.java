package com.ouersighnimarwen.tunguidef.entity;

public class MenuModel
{
    public String menuImageUrl;
    public String menuName;

    public MenuModel() {
    }

    public MenuModel(String menuImageUrl, String menuName) {
        this.menuImageUrl = menuImageUrl;
        this.menuName = menuName;
    }

    public String getMenuImageUrl()
    {
        return menuImageUrl;
    }

    public void setMenuImageUrl(String menuImageUrl)
    {
        this.menuImageUrl = menuImageUrl;
    }

    public String getMenuName()
    {
        return menuName;
    }

    public void setMenuName(String menuName)
    {
        this.menuName = menuName;
    }
}
