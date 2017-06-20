package com.example.puicbr.fertilizerforlatex.BroadcastReceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.puicbr.fertilizerforlatex.MainActivityOld;
import com.example.puicbr.fertilizerforlatex.helper.DbHelper;
import com.example.puicbr.fertilizerforlatex.model.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Folder on 20-Jun-17.
 */
public class AlarmReceiver extends BroadcastReceiver {

//    public static

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("AlarmReceiver", "start");

        // Select ทุก task ที่ต้องใส่ปุ๋ยในวันที่แจ้งเตือน
        String taskNameListStr = "";
        DbHelper dbHelper = new DbHelper(context);
        Calendar calendar_today = new GregorianCalendar();
        List<Task> taskList = dbHelper.selectTask_ByFertilizingDate(calendar_today.getTime());
        for (Task task: taskList) {
            if(taskNameListStr.equals("")){
                taskNameListStr += task.name;
            } else {
                taskNameListStr += ", " + task.name;
            }
        }
        Log.d("AlarmReceiver", "found task name: " + taskNameListStr);

        // สร้าง notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivityOld.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle("Reminder")
                .setContentText("ถึงวันใส่ปุ๋ยแล้ว กรุณาเข้าไปเช็คงาน " + taskNameListStr)
                .setTicker("แจ้งเตือนการใส่ปุ๋ย")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // allow swipe to dismiss

        Random rand = new Random();
        int min = 1;
        int max = 9999;
        int unique_id = rand.nextInt(max) + min;

        notificationManager.notify(unique_id, mNotifyBuilder.build());
    }
}
