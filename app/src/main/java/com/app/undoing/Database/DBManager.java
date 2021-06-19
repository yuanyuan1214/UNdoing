package com.app.undoing.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private static SQLiteDatabase db;
    /* 初始化数据库对象*/
    public static void initDB(Context context){
        DBOpenHelper helper = new DBOpenHelper(context);  //得到帮助类对象
        db = helper.getWritableDatabase();      //得到数据库对象
    }

    /**
     * 在这里写使用数据库的接口
     *
     * */

    /*
     * 向正向账单当中插入一条元素
     * */
    public static void insertItemToPositivetb(AccountBean bean){
        ContentValues values = new ContentValues();
        values.put("typename",bean.getTypename());
        values.put("itemname",bean.getItemname());
        values.put("itemmoney",bean.getItemmoney());
        values.put("imagenum",bean.getImagenum());
        values.put("year",bean.getYear());
        values.put("month",bean.getMonth());
        values.put("day",bean.getDay());
        values.put("week",bean.getWeek());
        values.put("water",bean.getWater());
        values.put("land",bean.getLand());
        values.put("air",bean.getAir());
        values.put("mineral",bean.getMineral());
        values.put("animal",bean.getAnimal());
        db.insert("positivetb",null,values);
    }

    /*
     * 向反向账单当中插入一条元素
     * */
    public static void insertItemToNegativetb(AccountBean bean){
        ContentValues values = new ContentValues();
        values.put("typename",bean.getTypename());
        values.put("itemname",bean.getItemname());
        values.put("itemmoney",bean.getItemmoney());
        values.put("year",bean.getYear());
        values.put("month",bean.getMonth());
        values.put("day",bean.getDay());
        values.put("week",bean.getWeek());
        values.put("water",bean.getWater());
        values.put("land",bean.getLand());
        values.put("air",bean.getAir());
        values.put("mineral",bean.getMineral());
        values.put("animal",bean.getAnimal());
        db.insert("negativetb",null,values);
    }

    /*
     * 向种草清单当中插入一条元素
     * */
    public static void insertItemToGreedtb(AccountBean bean){
        ContentValues values = new ContentValues();
        values.put("typename",bean.getTypename());
        values.put("itemname",bean.getItemname());
        values.put("itemmoney",bean.getItemmoney());
        values.put("year",bean.getYear());
        values.put("month",bean.getMonth());
        values.put("day",bean.getDay());
        values.put("week",bean.getWeek());
        values.put("water",bean.getWater());
        values.put("land",bean.getLand());
        values.put("air",bean.getAir());
        values.put("mineral",bean.getMineral());
        values.put("animal",bean.getAnimal());
        db.insert("greedtb",null,values);
    }

    /*
     * 根据传入的id，删除positivetb表当中的一条数据
     * */
    public static int deleteItemFromPositivetbById(int id){
        int i = db.delete("positivetb", "id=?", new String[]{id + ""});
        return i;
    }
}
