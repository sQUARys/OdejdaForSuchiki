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

import com.example.mac.suchik.R;
import com.example.mac.suchik.UI.settings_page.TimesListAdapter;
import java.util.Arrays;

public class Schedule extends Fragment {
    private ImageView image;
    private Button btn;
    TextView tv;
    public static final int MY_LIST = 3;
    public static final int SETTINGS = 4;
    Button alarm_on, alarm_off;
    TextView updateText;
    TimePicker timePicker;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    EditText et;
    RecyclerView rv;
    private String s;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_btn:
                openFragment(MY_LIST);
                break;
            case R.id.settings_btn:
                openFragment(SETTINGS);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFragment(int fragmentId) {
        Fragment fragment = null;
        switch (fragmentId) {
            case MY_LIST:
                fragment = new TimeTable();
                break;
            case SETTINGS:
                fragment = new Settings();
        }
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.listOfAlarms);
        tv = view.findViewById(R.id.updateTimeText);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new TimesListAdapter(Arrays.asList(aStrings)));
    }
}
