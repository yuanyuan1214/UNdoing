package com.app.undoing.model;

import com.app.undoing.Database.DBManager;

import java.util.ArrayList;
import java.util.List;

public class ChooseButtonItem {
    int isnew;
    List<Boolean> wrap=new ArrayList<>();
    int[] wrap_num=new int[2];
    public ChooseButtonItem(){
        this.isnew=-1;
        wrap.add(false);
        wrap.add(false);
        wrap.add(false);
        wrap_num[0]=0;
        wrap_num[1]=0;
    }
    public void setIsnew(int isnew) {this.isnew=isnew;}
    public void setWrapAtPosition(int position,boolean isSelected) {wrap.set(position,isSelected);}
    public void setWrapNumberAtPosition(int position,int number) {this.wrap_num[position]=number;}

    public int getIsnew() {return isnew;}
    public boolean getWrapAtPosition(int position) {return wrap.get(position);}
    public int getPlasticNumber(){return wrap_num[0];}
    public int getPaperNumber(){return wrap_num[1];}
    public boolean getWrapChooseState() {
        for(int i=0;i<wrap.size();++i){
            if(wrap.get(i)) return true;
        }
        return false;
    }

    public float calculate() {
        if(wrap.get(2)){
            return 0.0f;
        }
        float wrap_carbon1= DBManager.getCarbonNumber("塑料袋").getNumber();
        float wrap_carbon2= DBManager.getCarbonNumber("纸袋").getNumber();
        return wrap_num[0]*wrap_carbon1+wrap_num[1]*wrap_carbon2;
    }
}
