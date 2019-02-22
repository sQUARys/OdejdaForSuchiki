package com.example.mac.suchik.UI;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mac.suchik.R;
import com.example.mac.suchik.Storage;

public class MainActivityUI extends AppCompatActivity {
    public android.support.v7.app.ActionBar actionbar;
    public static final int MAIN_WINDOW_FRAGMENT = 1;
    public static final int SCHEDULE_WINDOW_FRAGMENT = 2;
    public static final int MY_LIST = 3;
    public static final int SETTINGS = 4;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        actionbar = getSupportActionBar();
//        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#84B3D5")));//change color of action bar

        //actionbar.setBackgroundDrawable(getDrawable(R.drawable.backgtoundmusttop));
//        actionbar.setDisplayShowTitleEnabled (false);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        if(savedInstanceState == null){
            Fragment fragment = new Fragment();
            fragment = new MainWindowFragment();
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){

                        case R.id.schedule_btn:
                            selectedFragment = new Schedule();
                            break;
                        case R.id.list_btn:
                            selectedFragment = new TimeTable();
                            break;
                        case R.id.settings_btn:
                         selectedFragment = new Settings();
                         break;
                        default:
                            selectedFragment = new MainWindowFragment();
                            break;
                    }
                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.container , selectedFragment).
                            commit();
                    return true;
                }
            };
}