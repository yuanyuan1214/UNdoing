package com.app.undoing.Content;

import com.app.undoing.R;

public class DoingListItem {
    private String doing_content;
    private double doing_cost;
    private int doing_image;
    private int img_background;

    public DoingListItem(String content,double cost,int image){
        this.doing_content=content;
        this.doing_cost=cost;
        this.doing_image=image;
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

    public String getDoing_content(){
        return this.doing_content;
    }

    public void setDoing_content(String content){
        this.doing_content=content;
    }

    public double getDoing_cost(){
        return this.doing_cost;
    }

    public void setDoing_cost(double cost){
        this.doing_cost=cost;
    }

    public int getDoing_image(){
        return this.doing_image;
    }

    public void setDoing_image(int image){
        this.doing_image=image;
    }

    public int getImg_background() {
        return this.img_background;
    }

    public void setImg_background(int img_background) {
        this.img_background=img_background;
    }
}