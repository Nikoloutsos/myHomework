package com.tutorial.androiddreamer.myhomework.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static String convertUnixTimeStampInReadableForm(long timestamp){
        Date date = new java.util.Date(timestamp);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd\nh:mm a");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(TimeZone.getDefault().toString()));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}
