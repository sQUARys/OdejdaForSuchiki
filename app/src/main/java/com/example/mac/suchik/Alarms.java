package com.example.mac.suchik;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Set;

public class Alarms {

    private NotificationManager mNotificationManager;
    private AlarmManager alarmManager;
    private PendingIntent notifyPendingIntent;
    private Intent notifyIntent;
    private Context context;
    private SharedPreferences sp;
    private AlarmsClock alarmsClock;
    private Gson gson = new Gson();

    public Alarms(Context context) {
        this.context = context;
        notifyIntent = new Intent(context, AlarmReceiver.class);
        mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        sp = context.getSharedPreferences("alarms", Context.MODE_PRIVATE);
        if (sp.getString("alarms", "").equals("")) {alarmsClock = new AlarmsClock();
        alarmsClock.setAlarmClock(new ArrayList<AlarmClock>());}
        else alarmsClock = gson.fromJson(sp.getString("alarms", ""), AlarmsClock.class);
    }

    public AlarmsClock getAlarmsClock() {
        return alarmsClock;
    }

    public void createNotification(int hours, int mins) {
        int nowID = (int) SystemClock.elapsedRealtime();
        notifyPendingIntent = PendingIntent.getBroadcast
                (context, nowID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, mins);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, notifyPendingIntent);
        ArrayList<AlarmClock> arcl = alarmsClock.getAlarmClock();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        arcl.add(new AlarmClock(dateFormat.format(calendar.getTime()), nowID + ""));
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("alarms", gson.toJson(arcl));
        edit.commit();

    }

    public void removeAllNotification() {
        for (AlarmClock s: alarmsClock.getAlarmClock()) {
            PendingIntent notS = PendingIntent.getBroadcast
                    (context, Integer.parseInt(s.getId()), notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(notS);
        }
        mNotificationManager.cancelAll();
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("alarms", "");
        edit.commit();
    }

    public void removeNotification(int id){
        PendingIntent notS = PendingIntent.getBroadcast
                (context, id, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(notS);
    }

    public String getInfo(){
        return alarmsClock.getAlarmClock().toString();
    }
}
