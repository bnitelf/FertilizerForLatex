package com.example.puicbr.fertilizerforlatex.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Folder on 16-Nov-16.
 */
public class DateHelper {
    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    public static long getMonthDiff(Date date1, Date date2) {
        long dayDiff1 = DateHelper.getDateDiff(date1, date2, TimeUnit.DAYS);
        double monthDiff = Math.round((double) dayDiff1 / 30);
        return (long)monthDiff;
    }

    /**
     * Format date ให้เป็น string ในรูป yyyy-mm-dd
     * @param d
     * @return
     */
    public String formatDateToDateString(Date d){
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        String dateStr = dateFormat.format(d);
        return dateStr;
    }
}
