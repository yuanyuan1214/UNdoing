package com.app.undoing.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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
     * 获取本周节约支出
     *
     */
    public static List<Float> getWeekMoney(int year,int month,int week) {
        List<Float> list=new ArrayList<>();
        String positiveSql="select sum(itemmoney) from positivetb where year=? and month=? and week=? ";
        Cursor cursor = db.rawQuery(positiveSql, new String[]{year + "", month + "", week + ""});
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(itemmoney)"));
            list.add(money);
        }
        String negativeSql="select sum(itemmoney) from negativetb where year=? and month=? and week=? ";
        cursor = db.rawQuery(negativeSql, new String[]{year + "", month + "", week + ""});
        if (cursor.moveToFirst()) {
            float money = cursor.getFloat(cursor.getColumnIndex("sum(itemmoney)"));
            list.add(money);
        }
        return list;
    }
    /*
     * 获取本周积累消耗
     *
     */
    public static List<Integer> getWeekPoints(int year,int month,int week) {
        List<Integer> list=new ArrayList<>();

        String positiveSql="select sum(water)+sum(land)+sum(air)+sum(mineral)+sum(animal) as points from positivetb where year=? and month=? and week=? ";
        Cursor cursor = db.rawQuery(positiveSql, new String[]{year + "", month + "", week + ""});
        if (cursor.moveToFirst()) {
            Integer money = cursor.getInt(cursor.getColumnIndex("points"));
            list.add(money);
        }

        String negativeSql="select sum(water)+sum(land)+sum(air)+sum(mineral)+sum(animal) as points from negativetb where year=? and month=? and week=? ";
        cursor = db.rawQuery(negativeSql, new String[]{year + "", month + "", week + ""});
        if (cursor.moveToFirst()) {
            Integer money = cursor.getInt(cursor.getColumnIndex("points"));
            list.add(money);
        }
        return list;
    }


    /*
     * 读取正向账单元素
     */
    public static List<AccountBean> getPositivetbByDay(int year,int month,int day) {
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from positivetb where year=? and month=? and day=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + ""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String itemname = cursor.getString(cursor.getColumnIndex("itemname"));
            int imagenum = cursor.getInt(cursor.getColumnIndex("imagenum"));
            float itemmoney = cursor.getFloat(cursor.getColumnIndex("itemmoney"));
            int week = cursor.getInt(cursor.getColumnIndex("week"));
            int water = cursor.getInt(cursor.getColumnIndex("water"));
            int land = cursor.getInt(cursor.getColumnIndex("land"));
            int air = cursor.getInt(cursor.getColumnIndex("air"));
            int mineral = cursor.getInt(cursor.getColumnIndex("mineral"));
            int animal = cursor.getInt(cursor.getColumnIndex("animal"));
            AccountBean accountBean = new AccountBean(id, typename, itemname,itemmoney,imagenum,year,month,day,week,water,land,air,mineral,animal);
            list.add(accountBean);
        }
        return list;
    }

    /*
     * 读取反向账单元素
     */
    public static List<AccountBean> getNegativetbByDay(int year,int month,int day) {
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from negativetb where year=? and month=? and day=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + ""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String itemname = cursor.getString(cursor.getColumnIndex("itemname"));
            int imagenum = cursor.getInt(cursor.getColumnIndex("imagenum"));
            float itemmoney = cursor.getFloat(cursor.getColumnIndex("itemmoney"));
            int week = cursor.getInt(cursor.getColumnIndex("week"));
            int water = cursor.getInt(cursor.getColumnIndex("water"));
            int land = cursor.getInt(cursor.getColumnIndex("land"));
            int air = cursor.getInt(cursor.getColumnIndex("air"));
            int mineral = cursor.getInt(cursor.getColumnIndex("mineral"));
            int animal = cursor.getInt(cursor.getColumnIndex("animal"));
            AccountBean accountBean = new AccountBean(id, typename, itemname,itemmoney,imagenum,year,month,day,week,water,land,air,mineral,animal);
            list.add(accountBean);
        }
        return list;
    }

    /*
     * 读取种草账单元素
     */
    public static List<AccountBean> getGreedtbByDay(int year,int month,int day) {
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from greedtb where year=? and month=? and day=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + ""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String itemname = cursor.getString(cursor.getColumnIndex("itemname"));
            int imagenum = cursor.getInt(cursor.getColumnIndex("imagenum"));
            float itemmoney = cursor.getFloat(cursor.getColumnIndex("itemmoney"));
            int week = cursor.getInt(cursor.getColumnIndex("week"));
            int water = cursor.getInt(cursor.getColumnIndex("water"));
            int land = cursor.getInt(cursor.getColumnIndex("land"));
            int air = cursor.getInt(cursor.getColumnIndex("air"));
            int mineral = cursor.getInt(cursor.getColumnIndex("mineral"));
            int animal = cursor.getInt(cursor.getColumnIndex("animal"));
            AccountBean accountBean = new AccountBean(id, typename, itemname,itemmoney,imagenum,year,month,day,week,water,land,air,mineral,animal);
            list.add(accountBean);
        }
        return list;
    }

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
        db.insert("greedtb",null,values);
    }

    /*
     * 根据传入的id，删除positivetb表当中的一条数据
     * */
    public static int deleteItemFromPositivetbById(int id){
        int i = db.delete("positivetb", "id=?", new String[]{id + ""});
        return i;
    }

    /*
     * 根据传入的id，删除negativetb表当中的一条数据
     * */
    public static int deleteItemFromNegativetbById(int id){
        int i = db.delete("negativetb", "id=?", new String[]{id + ""});
        return i;
    }

    /*
     * 根据传入的id，删除greedtb表当中的一条数据
     * */
    public static int deleteItemFromGreedtbById(int id){
        int i = db.delete("greedtb", "id=?", new String[]{id + ""});
        return i;
    }

}
