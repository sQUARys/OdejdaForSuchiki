package com.example.mac.suchik.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mac.suchik.Alarms;
import com.example.mac.suchik.R;
import com.example.mac.suchik.UI.settings_page.TimesListAdapter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Schedule extends Fragment {
  TextView tv;
  Button alarm_on, alarm_off;
  TimePicker timePicker;
  RecyclerView rv;
  public String[] aStrings = new String[]{
          "dasdsgsa",
          "sg",
          "gsg",
          "dfdfsgdsg"
  };

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.schedule, container, false);

  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.main_menu, menu);
  }

  private Integer[] calculateData(int hours, int mins){
    if (timePicker.getCurrentHour() <= hours){
      if (timePicker.getCurrentMinute() < mins){
        return new Integer[]{23, 60 - timePicker.getCurrentMinute()};
      }
      else {
        return new Integer[]{0, timePicker.getCurrentMinute() - mins};
      }
    }
    else{
      if (timePicker.getCurrentMinute() < mins){
        return new Integer[]{timePicker.getCurrentHour() - hours - 1, 60 + timePicker.getCurrentMinute() - mins};
      }
      else {
        return new Integer[]{timePicker.getCurrentHour() - hours, timePicker.getCurrentMinute() - mins};
      }
    }
  }

  @Override
  public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    rv = view.findViewById(R.id.listOfAlarms);
    tv = view.findViewById(R.id.updateTimeText);
    alarm_on = view.findViewById(R.id.alarm_on);
    alarm_off = view.findViewById(R.id.alarm_off);
    final Alarms alarms = new Alarms(view.getContext());
    timePicker = view.findViewById(R.id.timePicker);
    alarm_on.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Date date = Calendar.getInstance().getTime();
        final Integer[] fAlarm = calculateData(date.getHours(), date.getMinutes());
        alarms.createNotification(fAlarm[0], fAlarm[1]);
      }
    });
    alarm_off.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        alarms.removeNotification();
      }
    });
    rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    rv.setAdapter(new TimesListAdapter(Arrays.asList(aStrings)));
  }
}
