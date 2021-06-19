package com.app.undoing.Content;

import com.app.undoing.R;

import java.util.Date;
import java.util.List;

public class RecommendListItem {
    private String recommend_content;
    private String recommend_detail;
    private float recommend_save;
    private int recommend_image;
    private List<Integer> recommend_save_count;
    private int img_background;
    private Date recommend_date;
    private int id;
    private int week;


    public RecommendListItem(String content,String detail, float save,int image,List<Integer> save_count,Date recommend_date,int week,int id){
        this.recommend_content=content;
        this.recommend_detail=detail;
        this.recommend_save=save;
        this.recommend_image=image;
        this.recommend_save_count=save_count;
        this.recommend_date=recommend_date;
        this.week=week;
        this.id=id;
        int color_select=(int)(1+Math.random()*8);
        switch (color_select){
            case 1:
                this.img_background= R.color.list_img_pink;
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


    public String getRecommend_content() {
        return recommend_content;
    }

    public void setRecommend_content(String recommend_content) {
        this.recommend_content = recommend_content;
    }

    public String getRecommend_detail() {
        return recommend_detail;
    }

    public void setRecommend_detail(String recommend_detail) {
        this.recommend_detail = recommend_detail;
    }

    public float getRecommend_save() {
        return recommend_save;
    }

    public void setRecommend_save(float recommend_save) {
        this.recommend_save = recommend_save;
    }

    public int getRecommend_image() {
        return recommend_image;
    }

    public void setRecommend_image(int recommend_image) {
        this.recommend_image = recommend_image;
    }
    public List<Integer> getRecommend_save_count() {
        return recommend_save_count;
    }

    public void setRecommend_save_count(List<Integer> recommend_save_count) {
        this.recommend_save_count = recommend_save_count;
    }

    public int getImg_background() {
        return img_background;
    }

    public void setImg_background(int img_background) {
        this.img_background = img_background;
    }

    public Date getRecommend_date() {
        return recommend_date;
    }

    public void setRecommend_date(Date recommend_date) {
        this.recommend_date = recommend_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
