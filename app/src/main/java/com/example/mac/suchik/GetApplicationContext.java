package com.example.mac.suchik;

import android.app.Application;

public class GetApplicationContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Storage.getOrCreate(getApplicationContext());
    }
}
