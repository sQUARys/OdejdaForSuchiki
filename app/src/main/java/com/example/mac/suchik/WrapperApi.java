package com.example.mac.suchik;

import android.os.AsyncTask;

import java.io.IOException;

public class WrapperApi extends AsyncTask<Void, Void, Response> implements ResponseType{
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
    protected Response doInBackground(Void... voids) {
        boolean flag = true;
        Weather weather = null;
        while (flag) {
            try {
                weather = new Weather(lat, lon, callbacks);
                flag = false;
            } catch (IOException e) {
            }
        }
        switch (type) {
            case ResponseType.WTODAY:
                return weather.parseWheatherToday();
            case ResponseType.WFORECASTS:
                return weather.parseWheatherForecasts();
            default:
                return null;
        }
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        callbacks.onLoad(response);
    }
}
