package com.example.mac.suchik;

import android.os.AsyncTask;

import java.io.IOException;

public class WrapperApi extends AsyncTask<Void, Void, Void> implements ResponseType{
    private String lat, lon;
    private int type;
    private Callbacks callbacks;

    public WrapperApi(String lat, String lon, int type, Callbacks callbacks) {
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.callbacks = callbacks;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        boolean flag = true;
        Weather weather = null;
        while (flag) {
            try {
                weather = new Weather(lat, lon, callbacks);
                flag = false;
            } catch (IOException e) {
            }
        }
        switch (type){
            case ResponseType.WTODAY:
                weather.parseWheatherToday();
                break;
            case ResponseType.WFORECASTS:
                weather.parseWheatherForecasts();
                break;
        }
        return null;
    }

}
