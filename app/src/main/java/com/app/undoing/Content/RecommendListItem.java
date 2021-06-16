package com.app.undoing.Content;

import java.util.List;

public class RecommendListItem {
    private String recommend_content;
    private String recommend_detail;
    private double recommend_save;
    private int recommend_image;
    private List<Integer> recommend_save_count;

    public RecommendListItem(String content,String detail, double save,int image,List<Integer> save_count){
        this.recommend_content=content;
        this.recommend_detail=detail;
        this.recommend_save=save;
        this.recommend_image=image;
        this.recommend_save_count=save_count;
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

    public double getRecommend_save() {
        return recommend_save;
    }

    public void setRecommend_save(double recommend_save) {
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
}
