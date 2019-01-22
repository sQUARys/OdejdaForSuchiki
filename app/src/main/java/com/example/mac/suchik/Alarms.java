package com.example.mac.suchik;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Calendar;
import java.util.Date;

public class Alarms {

  private static final int NOTIFICATION_ID = 0;
  private NotificationManager mNotificationManager;
  private AlarmManager alarmManager;
  private PendingIntent notifyPendingIntent;
  private Intent notifyIntent;

  public Alarms(Context context){
    mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
    alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
    notifyIntent = new Intent(context, AlarmReceiver.class);
    notifyPendingIntent = PendingIntent.getBroadcast
            (context, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
  }

  public void createNotification(int hours, int mins) {
    Date date = Calendar.getInstance().getTime();
    long triggerTime = SystemClock.elapsedRealtime() +
            hours * AlarmManager.INTERVAL_HOUR + mins * 60 * 1000 - date.getSeconds() * 1000;
    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
            triggerTime, AlarmManager.INTERVAL_DAY, notifyPendingIntent);
  }

  public void removeNotification(){
    alarmManager.cancel(notifyPendingIntent);
    mNotificationManager.cancelAll();
  }
}
