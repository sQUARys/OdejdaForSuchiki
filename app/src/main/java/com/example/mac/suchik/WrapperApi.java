package com.example.mac.suchik;

import android.os.AsyncTask;

import java.io.IOException;

public class WrapperApi extends AsyncTask<Void, Void, Response> implements ResponseType{
    private String lat, lon;
    private Callbacks callbacks;

    public WrapperApi(String lat, String lon, Callbacks callbacks) {
        this.lat = lat;
        this.lon = lon;
        this.callbacks = callbacks;

    }

    @Override
    protected Response doInBackground(Void... voids) {
        Weather weather = null;
        boolean flag = true;
        while (flag){
            try {
                weather = new Weather(lat, lon);
                flag = false;
            } catch (IOException e) {
                flag = true;
            }
        }
        return weather.parseWeather();
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        callbacks.onLoad(response);
    }
}
