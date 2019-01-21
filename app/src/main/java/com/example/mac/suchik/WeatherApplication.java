package com.example.mac.suchik;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Storage mSorage = Storage.getOrCreate(getApplicationContext());
        //mSorage.updateWeather(false);
    }
}
