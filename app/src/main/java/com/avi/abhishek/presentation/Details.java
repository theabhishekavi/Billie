package com.avi.abhishek.presentation;

public class Details {

    public Details(){

    }

    public Details(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public Details(String name) {
        this.name = name;
    }

    public String name;
    public int money;

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }
}
