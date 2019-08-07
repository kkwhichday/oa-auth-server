package com.oa.auth.compute.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @description: 日期处理
 * @author: kk
 * @create: 2019-01-18
 **/
public class DataUtil {
    public static String getLastMonth(){
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;

    }
    public static String getCurrentYear(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy");
        String year = dft.format(cal.getTime());
        return year;

    }
}
