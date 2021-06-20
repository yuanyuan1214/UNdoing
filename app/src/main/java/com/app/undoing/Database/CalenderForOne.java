package com.app.undoing.Database;

import java.util.Calendar;
import java.util.Date;

public class CalenderForOne {
    private static Calendar cl;
    public static void initCalender(){
        cl=Calendar.getInstance();
    }
    public static int getYear()
    {
        return  cl.get(Calendar.YEAR);
    }
    public static int getMonth()
    {
        return  cl.get(Calendar.MONTH)+1;
    }
    public static int getDay()
    {
        return  cl.get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekOfMonth()
    {
        return  cl.get(Calendar.WEEK_OF_MONTH);
    }

    public static int getWeekOfYear()
    {
        cl.setFirstDayOfWeek(Calendar.MONDAY);
        return  cl.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getData(Date date)
    {
        cl.setTime(date);
        return cl.get(Calendar.DATE);
    }

}
