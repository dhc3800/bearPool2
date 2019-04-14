package com.dhc3800.bearpool5;

import java.util.Calendar;

public class Utils {
    public static long parseCalendarValues(int day, int month, int year, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute);
        return c.getTimeInMillis();
    }
}
