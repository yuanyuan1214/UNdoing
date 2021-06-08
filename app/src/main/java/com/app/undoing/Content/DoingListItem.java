package com.app.undoing.Content;

import com.app.undoing.R;

//{"买水果", "五角场看电影", "请同学吃饭", "网购洗发水", "预订回家机票", "买狗粮"}
//{-21.00, -48.00, -121.00, -78.00, -830.00, -120.00}
//R.drawable.dashicons_fruit, R.drawable.dashicons_editor_video,
//            R.drawable.dashicons_food, R.drawable.dashicons_cart, R.drawable.dashicons_plane, R.drawable.dashicons_pets
public class DoingListItem {
    private String doing_content;
    private double doing_cost;
    private int doing_image;

    public DoingListItem(String content,double cost,int image){
        this.doing_content=content;
        this.doing_cost=cost;
        this.doing_image=image;
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
}