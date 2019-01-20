package com.example.mac.suchik;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Alarms {

    private static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotificationManager;
    private AlarmManager alarmManager;
    private PendingIntent notifyPendingIntent;
    private Intent notifyIntent;
    private boolean alarmUp;
    private Context context;

    Alarms(Context context){
        this.context = context;
        mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        notifyIntent = new Intent(context, AlarmReceiver.class);
        notifyPendingIntent = PendingIntent.getBroadcast
                (context, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void createNotification(long triggerTime) {
            long repeatInterval = AlarmManager.INTERVAL_DAY;
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerTime, repeatInterval, notifyPendingIntent);
        }

        public void removeNotification(){
        alarmManager.cancel(notifyPendingIntent);
        mNotificationManager.cancelAll();
    }

}
