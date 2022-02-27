package com.app.undoing.model;
//item类型
public class TypeItem {
    int id;
    String typename;   //类型名称
    int imageId;    //未被选中图片id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public TypeItem() {
    }

    public TypeItem(int id, String typename, int imageId) {
        this.id = id;
        this.typename = typename;
        this.imageId = imageId;
    }
}
