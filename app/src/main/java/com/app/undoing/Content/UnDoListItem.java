package com.app.undoing.Content;

import com.app.undoing.R;

import java.util.Date;

public class UnDoListItem {
    private String undo_content;
    private double undo_cost;
    private int undo_image;
    private int img_background;
    private Date undo_date;

    public UnDoListItem(String content,double cost,int image,Date undo_date){
        this.undo_content=content;
        this.undo_cost=cost;
        this.undo_image=image;
        this.undo_date=undo_date;
        int color_select=(int)(1+Math.random()*8);
        System.out.println(color_select);
        //根据生成的随机数选择对应的颜色
        switch (color_select){
            case 1:
                this.img_background=R.color.list_img_pink;
                break;
            case 2:
                this.img_background=R.color.list_img_orange;
                break;
            case 3:
                this.img_background=R.color.list_img_yellow;
                break;
            case 4:
                this.img_background=R.color.list_img_brown;
                break;
            case 5:
                this.img_background=R.color.list_img_lightgreen;
                break;
            case 6:
                this.img_background=R.color.list_img_green;
                break;
            case 7:
                this.img_background=R.color.list_img_purple;
                break;
            case 8:
                this.img_background=R.color.list_img_blue;
                break;
        }
    }

    public String getUndo_content(){
        return this.undo_content;
    }

    public void setUndo_content(String content){
        this.undo_content=content;
    }

    public double getUndo_cost(){
        return this.undo_cost;
    }

    public void setUndo_cost(double cost){
        this.undo_cost=cost;
    }

    public int getUndo_image(){
        return this.undo_image;
    }

    public void setUndo_image(int image){
        this.undo_image=image;
    }

    public int getImg_background() {
        return this.img_background;
    }

    public void setImg_background(int img_background) {
        this.img_background=img_background;
    }

    public Date getUndo_date() {
        return this.undo_date;
    }

    public void setUndo_date(Date date) {
        this.undo_date=date;
    }
}
