package com.example.puicbr.fertilizerforlatex.helper;

import com.example.puicbr.fertilizerforlatex.model.Formula;
import com.example.puicbr.fertilizerforlatex.model.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    public static String formatDateToDateString(Date d){
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        String dateStr = dateFormat.format(d);
        return dateStr;
    }

    public static int GetCurrentTreeAge(Task task){
        int currentTreeAge = 0;

        Date today = new Date();

        currentTreeAge = (int) (task.tree_age + (getDateDiff(today, task.create_date, TimeUnit.DAYS) / 30));

        return currentTreeAge;
    }

    /**
     * Get วันที่ในการให้ปุ๋ยรอบต่อไปของ task
     * @param task task ที่ต้องการหาวันที่การใส่ปุ๋ยรอบต่อไป
     * @param formulaList list ของสูตรการให้ปุ๋ย (ข้อมูลนี้ต้องดึงมาจาก database sqlite)
     * @return ถ้ามีการให้ปุ๋ยรอบถัดไปจะ return date วันที่, ถ้าไม่มีการให้ปุ๋ยรอบต่อไปจะ return null
     */
    public static Date GetNextFertilizingDate(Task task, List<Formula> formulaList){
        Date next = null;

//        Calendar calendar = Calendar.getInstance();
        Calendar calendarNext = Calendar.getInstance();

        int currentTreeAge = GetCurrentTreeAge(task);

        int nextFertilizingTreeAge = -1;

        for(Formula f : formulaList){
            if(f.tree_age > currentTreeAge){
                nextFertilizingTreeAge = f.tree_age;
            }
        }

        if(nextFertilizingTreeAge != -1){
            int treeAgeDiff = nextFertilizingTreeAge - currentTreeAge;

            calendarNext.add(Calendar.MONTH, treeAgeDiff);

            next = calendarNext.getTime();
        }

        return next;
    }

    public static Date GetNextFertilizingDate(Task task, List<Formula> formulaList, int currentTreeAge){
        Date next = null;

        Calendar calendarNext = Calendar.getInstance();

        int nextFertilizingTreeAge = -1;

        for(Formula f : formulaList){
            if(f.tree_age > currentTreeAge){
                nextFertilizingTreeAge = f.tree_age;
            }
        }

        if(nextFertilizingTreeAge != -1){
            int treeAgeDiff = nextFertilizingTreeAge - currentTreeAge;

            calendarNext.add(Calendar.MONTH, treeAgeDiff);

            next = calendarNext.getTime();
        }

        return next;
    }


    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * เช็คว่าเป็นปีอธิกสุรทินหรือไม่
     * @param year ปีที่ต้องการเช็ค
     * @return true เป็นปีอธิกสุรทิน, false ไม่เป็นอธิกสุรทิน
     */
    public static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }
}
