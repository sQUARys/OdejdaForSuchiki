package com.example.mac.suchik;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.Response;

public class WrapperApi extends AsyncTask<Void, Void, Void> {
    private String lat, lon;

    public WrapperApi(String lat, String lon, int type) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        boolean flag = true;
        Wheather wheather = null;
        while (flag) {
            try {
                wheather = new Wheather(lat, lon);
                flag = false;
            } catch (IOException e) {
            }
        }
        try {
            wheather.parseWheatherForecastsy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
