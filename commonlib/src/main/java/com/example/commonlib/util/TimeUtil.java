package com.example.commonlib.util;

import java.text.SimpleDateFormat;

public class TimeUtil {
    public static String getSystemCurrentTime() {

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }
}
