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

import com.example.mac.suchik.AlarmAdapter;
import com.example.mac.suchik.AlarmClock;
import com.example.mac.suchik.Alarms;
import com.example.mac.suchik.R;
import com.example.mac.suchik.UI.main_window.RecomendationListAdapter;
import com.example.mac.suchik.UI.settings_page.TimesListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        alarms.createNotification(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
      }
    });
    alarm_off.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        alarms.removeAllNotification();
      }
    });
    view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(view.getContext(), alarms.getInfo(), Toast.LENGTH_SHORT).show();
      }
    });
    rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    if (alarms.getAlarmsClock().getAlarmClock() == null)
      rv.setAdapter(new AlarmAdapter(new ArrayList<AlarmClock>()));
    else rv.setAdapter(new AlarmAdapter(alarms.getAlarmsClock().getAlarmClock()));
  }
}
