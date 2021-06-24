package com.app.undoing;

import android.app.Application;

import com.app.undoing.Database.CalenderForOne;
import com.app.undoing.Database.DBManager;

/* 表示全局应用的类*/
public class UniteApp extends Application {
    WakeUp s;
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数据库
        DBManager.initDB(getApplicationContext());
        CalenderForOne.initCalender();
        s=new WakeUp(getApplicationContext());
        s.start();
    }
}
