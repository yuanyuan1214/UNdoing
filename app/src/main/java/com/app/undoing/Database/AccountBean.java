package com.app.undoing.Database;

public class AccountBean {
    int id;
    String typename;
    String itemname;
    float itemmoney;
    int imagenum;

    int year;
    int month;
    int day;
    int week;
    int water;
    int land;
    int air;
    int mineral;
    int animal;

    public float getItemmoney() {
        return itemmoney;
    }

    public int getImagenum() {
        return imagenum;
    }

    public int getAir() {
        return air;
    }

    public int getAnimal() {
        return animal;
    }

    public int getMineral() {
        return mineral;
    }

    public int getLand() {
        return land;
    }

    public int getWater() {
        return water;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setImagenum(int imagenum) {
        this.imagenum = imagenum;
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

    public void setPoints(int water,int land,int air,int mineral,int animal) {
        this.water=water;
        this.land=land;
        this.air=air;
        this.mineral=mineral;
        this.animal=animal;
    }

    public void setDate( int year,int month,int day,int week) {
        this.year=year;
        this.month=month;
        this.day=day;
        this.week=week;
    }

    public AccountBean(){}

    public AccountBean(int id,String typename,String itemname,int imagenum,float itemmoney,int year,int month,int day,int week,int water,int land,int air,int mineral,int animal)
    {
        this.id=id;
        this.typename=typename;
        this.itemname=itemname;
        this.itemmoney=itemmoney;
        this.imagenum=imagenum;
        this.year=year;
        this.month=month;
        this.day=day;
        this.week=week;
        this.water=water;
        this.land=land;
        this.air=air;
        this.mineral=mineral;
        this.animal=animal;
    }
}
