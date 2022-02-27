package com.app.undoing.Database;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class DBOpenHelper extends SQLiteOpenHelper {
    Context context;
    public DBOpenHelper(@Nullable Context context) {
        super(context,"tally.db" , null, 1);
        this.context=context;
    }

    //    创建数据库的方法，只有项目第一次运行时，会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
      //创建正向账单表
        String sql = "create table positivetb(id integer primary key autoincrement,typename varchar(10),itemname varchar(100),itemmoney real,year integer,month integer,day integer,week integer,isnew integer,selfcarbon real,wrapcarbon real)";
        db.execSQL(sql);

        //创建反向账单表
        sql = "create table negativetb(id integer primary key autoincrement,typename varchar(10),itemname varchar(100),itemmoney real,year integer,month integer,day integer,week integer,isnew integer,selfcarbon real,wrapcarbon real)";
        db.execSQL(sql);

        //创建种草清单表
        sql = "create table greedtb(id integer primary key autoincrement,typename varchar(10),itemname varchar(100),itemmoney real,year integer,month integer,day integer,week integer,isnew integer,selfcarbon real,wrapcarbon real)";
        db.execSQL(sql);

        //创建碳数据库
        sql = "create table carbontb(id integer primary key autoincrement,subtypename varchar(10),carbonnum real)";
        db.execSQL(sql);

        //初始化碳数据库
        try {
            initialCarbonDb(db);
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
    }

    // 数据库版本在更新时发生改变，会调用此方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists positivetb");
        db.execSQL("drop table if exists negativetb");
        db.execSQL("drop table if exists greedtb");
        onCreate(db);
    }

    private void initialCarbonDb(SQLiteDatabase db) throws IOException, BiffException {
        InputStream inputStream = null;//输入流
        FileOutputStream outputStream = null;//输出流
        Workbook book = null;//Excel工作簿对象
        inputStream = context.getAssets().open("carbon.xls");
        File tempFile = new File(context.getCacheDir(), "carbon.xls");//临时文件，第二个参数为文件名字，可随便取
        outputStream = new FileOutputStream(tempFile);
        byte[] buf = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) > 0) {//while循环进行读取
            outputStream.write(buf, 0, len);
        }
        outputStream.close();
        inputStream.close();

        book = Workbook.getWorkbook(tempFile);//用读取到的表格文件来实例化工作簿对象
        Sheet[] sheets = book.getSheets(); //得到所有的工作表
        Sheet sheet=sheets[0];
        int Rows = sheet.getRows();//得到当前工作表的行数
        for(int i=0;i<Rows;++i){
            ContentValues values = new ContentValues();
            float carbon=Float.parseFloat(sheet.getCell(1,i).getContents());
            values.put("subtypename", sheet.getCell(0,i).getContents());
            values.put("carbonnum",carbon );
            db.insert("carbontb", null, values);
        }
    }
}
