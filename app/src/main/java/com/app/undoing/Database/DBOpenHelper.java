package com.app.undoing.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context,"tally.db" , null, 1);
    }

    //    创建数据库的方法，只有项目第一次运行时，会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
      //创建正向账单表
        String sql = "create table positivetb(id integer primary key autoincrement,typename varchar(10),itemname varchar(100),itemmoney real,imagenum integer,year integer,month integer,day integer,week integer,water integer, land integer, air integer,mineral integer,animal integer)";
        db.execSQL(sql);

        //创建反向账单表
        sql =  "create table negativetb(id integer primary key autoincrement,typename varchar(10),itemname varchar(100),itemmoney real,imagenum integer,year integer,month integer,day integer,week integer,water integer, land integer, air integer,mineral integer,animal integer)";
        db.execSQL(sql);

        //创建种草清单表
        sql =  "create table greedtb(id integer primary key autoincrement,typename varchar(10),itemname varchar(100),itemmoney real,imagenum integer,year integer,month integer,day integer,week integer,water integer, land integer, air integer,mineral integer,animal integer)";
        db.execSQL(sql);
    }


    // 数据库版本在更新时发生改变，会调用此方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists positivetb");
        db.execSQL("drop table if exists negativetb");
        db.execSQL("drop table if exists greedtb");
        onCreate(db);
    }
}
