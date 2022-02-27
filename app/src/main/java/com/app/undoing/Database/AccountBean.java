package com.app.undoing.Database;

import java.io.Serializable;

public class AccountBean implements Serializable {
    int id;
    String typename;
    String itemname;
    float itemmoney;

    int year;
    int month;
    int day;
    int week;

    int isnew;

    float selfcarbon;
    float wrapcarbon;


    public float getItemmoney() {
        return itemmoney;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getWeek() {
        return week;
    }

    public String getTypename() {
        return typename;
    }

    public String getItemname() {
        return itemname;
    }

    public int getId() {
        return id;
    }

    public AccountBean() {
    }

    public AccountBean(int id, String typename, String itemname, float itemmoney, int year, int month, int day, int week, int isNew, float selfCarbon, float wrapCarbon) {
        this.id = id;
        this.typename = typename;
        this.itemname = itemname;
        this.itemmoney = itemmoney;
        this.year = year;
        this.month = month;
        this.day = day;
        this.week = week;
        this.isnew = isNew;
        this.selfcarbon = selfCarbon;
        this.wrapcarbon = wrapCarbon;
    }

    public float getSelfCarbon() {
        return selfcarbon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public void setItemmoney(float itemmoney) {
        this.itemmoney = itemmoney;
    }

    public float getWrapCarbon() {
        return wrapcarbon;
    }

    public double getTotalCarbon() {return  selfcarbon+wrapcarbon;}

    public int getIsNew() {
        return isnew;
    }

    public void setIsNew(int isNew) {
        this.isnew = isNew;
    }

    public void setDate(int year, int month, int day, int week) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.week = week;
    }

    public void setCarbon(float selfCarbon, float wrapCarbon) {
        this.selfcarbon = selfCarbon;
        this.wrapcarbon = wrapCarbon;
    }
}
