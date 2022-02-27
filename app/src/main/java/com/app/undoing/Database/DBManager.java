package com.app.undoing.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.undoing.model.CarbonItem;

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
    public static List<Float> getWeekPoints(int year,int month,int week) {
        List<Float> list=new ArrayList<>();

        String positiveSql="select sum(selfcarbon)+sum(wrapcarbon) as points from positivetb where year=? and month=? and week=? ";
        Cursor cursor = db.rawQuery(positiveSql, new String[]{year + "", month + "", week + ""});
        if (cursor.moveToFirst()) {
            float carbon = cursor.getFloat(cursor.getColumnIndex("points"));
            list.add(carbon);
        }

        String negativeSql="select sum(selfcarbon)+sum(wrapcarbon) as points from negativetb where year=? and month=? and week=? ";
        cursor = db.rawQuery(negativeSql, new String[]{year + "", month + "", week + ""});
        if (cursor.moveToFirst()) {
            float carbon = cursor.getFloat(cursor.getColumnIndex("points"));
            list.add(carbon);
        }
        return list;
    }

    /*
     * 获取总共节约数目
     *
     */
    public static int getTotalSaveCount() {
        int number=0;
        List<AccountBean> list = new ArrayList<>();
        String sql = "select * from negativetb order by id desc";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            number++;
        }
        return number;
    }

    /*
     * 读取正向账单元素
     */
    public static List<AccountBean> getPositivetb() {
        List<AccountBean> list = new ArrayList<>();
        String sql = "select * from positivetb order by id desc";
        Cursor cursor = db.rawQuery(sql, null);
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String itemname = cursor.getString(cursor.getColumnIndex("itemname"));
            float itemmoney = cursor.getFloat(cursor.getColumnIndex("itemmoney"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            int week = cursor.getInt(cursor.getColumnIndex("week"));
            int isnew = cursor.getInt(cursor.getColumnIndex("isnew"));
            float selfcarbon = cursor.getFloat(cursor.getColumnIndex("selfcarbon"));
            float wrapcarbon = cursor.getFloat(cursor.getColumnIndex("wrapcarbon"));
            AccountBean accountBean = new AccountBean(id, typename, itemname, itemmoney, year, month, day, week, isnew, selfcarbon, wrapcarbon);
            list.add(accountBean);
        }
        return list;
    }

    /*
     * 读取反向账单元素
     */
    public static List<AccountBean> getNegativetb() {
        List<AccountBean> list = new ArrayList<>();
        String sql = "select * from negativetb order by id desc";
        Cursor cursor = db.rawQuery(sql, null);
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String itemname = cursor.getString(cursor.getColumnIndex("itemname"));
            float itemmoney = cursor.getFloat(cursor.getColumnIndex("itemmoney"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            int week = cursor.getInt(cursor.getColumnIndex("week"));
            int isnew = cursor.getInt(cursor.getColumnIndex("isnew"));
            float selfcarbon = cursor.getFloat(cursor.getColumnIndex("selfcarbon"));
            float wrapcarbon = cursor.getFloat(cursor.getColumnIndex("wrapcarbon"));
            AccountBean accountBean = new AccountBean(id, typename, itemname, itemmoney, year, month, day, week, isnew, selfcarbon, wrapcarbon);
            list.add(accountBean);
        }
        return list;
    }

    /*
     * 读取种草账单元素
     */
    public static List<AccountBean> getGreedtb() {
        List<AccountBean> list = new ArrayList<>();
        String sql = "select * from greedtb order by id desc";
        Cursor cursor = db.rawQuery(sql, null);
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String itemname = cursor.getString(cursor.getColumnIndex("itemname"));
            float itemmoney = cursor.getFloat(cursor.getColumnIndex("itemmoney"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            int week = cursor.getInt(cursor.getColumnIndex("week"));
            int isnew = cursor.getInt(cursor.getColumnIndex("isnew"));
            float selfcarbon = cursor.getFloat(cursor.getColumnIndex("selfcarbon"));
            float wrapcarbon = cursor.getFloat(cursor.getColumnIndex("wrapcarbon"));
            AccountBean accountBean = new AccountBean(id, typename, itemname, itemmoney, year, month, day, week, isnew, selfcarbon, wrapcarbon);
            list.add(accountBean);
        }
        return list;
    }

    /*
     * 读取碳种类和碳数量
     */
    public static CarbonItem getCarbonNumber(String inputTxt) {
        //TODO: 智能匹配
        String carbonSql="select carbonnum from carbontb where subtypename=? ";
        Cursor cursor = db.rawQuery(carbonSql, new String[]{inputTxt + ""});
        if (cursor.moveToFirst()) {
            float carbonNumber = cursor.getFloat(cursor.getColumnIndex("carbonnum"));
            CarbonItem carbonItem=new CarbonItem(inputTxt,carbonNumber);
            return carbonItem;
        }
       return new CarbonItem(inputTxt,0.0f);
    }

    /*
     * 向正向账单当中插入一条元素
     * */
    public static void insertItemToPositivetb(AccountBean bean) {
        ContentValues values = new ContentValues();
        values.put("typename", bean.getTypename());
        values.put("itemname", bean.getItemname());
        values.put("itemmoney", bean.getItemmoney());
        values.put("year", bean.getYear());
        values.put("month", bean.getMonth());
        values.put("day", bean.getDay());
        values.put("week", bean.getWeek());
        values.put("isnew", bean.getIsNew());
        values.put("selfcarbon", bean.getSelfCarbon());
        values.put("wrapcarbon", bean.getWrapCarbon());
        db.insert("positivetb", null, values);
    }

    /*
     * 向反向账单当中插入一条元素
     * */
    public static void insertItemToNegativetb(AccountBean bean) {
        ContentValues values = new ContentValues();
        values.put("typename", bean.getTypename());
        values.put("itemname", bean.getItemname());
        values.put("itemmoney", bean.getItemmoney());
        values.put("year", bean.getYear());
        values.put("month", bean.getMonth());
        values.put("day", bean.getDay());
        values.put("week", bean.getWeek());
        values.put("isnew", bean.getIsNew());
        values.put("selfcarbon", bean.getSelfCarbon());
        values.put("wrapcarbon", bean.getWrapCarbon());
        db.insert("negativetb", null, values);
    }

    /*
     * 向种草清单当中插入一条元素
     * */
    public static void insertItemToGreedtb(AccountBean bean) {
        ContentValues values = new ContentValues();
        values.put("typename", bean.getTypename());
        values.put("itemname", bean.getItemname());
        values.put("itemmoney", bean.getItemmoney());
        values.put("year", bean.getYear());
        values.put("month", bean.getMonth());
        values.put("day", bean.getDay());
        values.put("week", bean.getWeek());
        values.put("isnew", bean.getIsNew());
        values.put("selfcarbon", bean.getSelfCarbon());
        values.put("wrapcarbon", bean.getWrapCarbon());
        db.insert("greedtb", null, values);
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
