package com.example.wangzhixiang.librarytest.entity;

public class MenuItem {
    private String itemName;
    private int resource;
    private boolean isShow;
    public MenuItem(String name, int id){
        this.itemName = name;
        this.resource = id;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
