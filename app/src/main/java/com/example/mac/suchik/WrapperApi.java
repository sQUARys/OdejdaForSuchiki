package com.example.mac.suchik;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

public class WrapperApi extends AsyncTask<Void, Void, Response>{
    private String lat, lon;
    private Callbacks callbacks;
    private Gson gson;

    public WrapperApi(String lat, String lon, Callbacks callbacks, Gson gson) {
        this.lat = lat;
        this.lon = lon;
        this.callbacks = callbacks;
        this.gson = gson;
    }

    @Override
    protected Response doInBackground(Void... voids) {
        Weather weather = null;
        boolean flag = true;
        while (flag){
            try {
                weather = new Weather(lat, lon, gson);
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
