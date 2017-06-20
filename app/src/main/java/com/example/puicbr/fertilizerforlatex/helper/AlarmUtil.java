package com.example.puicbr.fertilizerforlatex.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.puicbr.fertilizerforlatex.BroadcastReceiver.AlarmReceiver;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by Folder on 20-Jun-17.
 */
public class AlarmUtil {

    public static int defaultHourIn24 = 7;
    public static int defaultMinute = 0;
    public static int defaultSecond = 0;

    public static final int REQUEST_CODE_ALARM = 1;

    public static void setReminder(Context context, Calendar calendar){
        setReminder(context, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
    }

    public static void setReminder(Context context, int year, int month, int day){
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, day, defaultHourIn24, defaultMinute, defaultSecond);
        long alertTime = calendar.getTimeInMillis();

        Log.d("AlarmReceiver", String.format("set alarm at Y=%d, M=%d, D=%d, H=%d, Min=%d, S=%d"
        , year, month, day, defaultHourIn24, defaultMinute, defaultSecond));

        Random rand = new Random();
        int min = 1;
        int max = 9999;
        int request_code_alarm = rand.nextInt(max) + min;

        Intent alertIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                request_code_alarm,
                alertIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, pendingIntent);
    }
}
