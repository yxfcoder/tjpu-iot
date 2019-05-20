package com.tjpu.iot.util;

import com.tjpu.iot.pojo.Data;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static long getDatePoorMin(Date start, Date end) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long diff = end.getTime() - start.getTime();
        long min = diff & nd & nh / nm;
        return min;
    }

    public static void main(String[] args) {
        System.out.println(getYear());
        System.out.println(getMonth());
        System.out.println(getDaysOfMonth(new Date()));
    }
}
