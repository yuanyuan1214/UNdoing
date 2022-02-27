package com.app.undoing.model;

import com.app.undoing.Database.DBManager;

public class EditItem {
    String name;
    String bigTypeName;
    float selfCarbon;

    public EditItem() {
        this.name="";
        this.selfCarbon=0.0f;
    }

    public void setName(String name) {this.name=name;}

    public void setBigTypeName(String name) {this.bigTypeName=name;}

    public String getName() {return name;}

    public void setSelfCarbon(float selfCarbon) {this.selfCarbon=selfCarbon;}

    public float getSelfCarbon() {return  selfCarbon;}

    public String calculate(){
        CarbonItem carbonItem=DBManager.getCarbonNumber(name);
        selfCarbon= carbonItem.getNumber();
        if(selfCarbon==0) return bigTypeName;
        return carbonItem.getName();
    }
}
