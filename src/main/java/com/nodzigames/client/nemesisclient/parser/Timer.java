package com.nodzigames.client.nemesisclient.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer {

    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

    public static Date toDate(String millis) {
        return new Date(Long.parseLong(millis));
    }

    public static String toString(Date date) {
        return dateFormat.format(date);
    }
}
