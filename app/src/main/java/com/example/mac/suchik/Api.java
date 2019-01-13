package com.example.mac.suchik;

import android.app.DownloadManager;
import android.app.VoiceInteractor;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

class WheatherNow implements WheatherTime{
    final String key = "5db67765-0ae1-41b9-af75-f4739f45ac5c";
    Request request;
    WheatherNow(int lat, int lon){
        Request request = new Request.Builder()
                .url("https://api.weather.yandex.ru/v1/forecast?")
                .addHeader("X-Yandex-API-Key", key)
                .addHeader("lat", lat)
                .addHeader("lon", lon)
                .build();
        this.request = request;
    }

    public Map<String, String> parseWheather(String json) {
        HashMap<String, String> map = new HashMap<>();
        try{

        }
        catch (JSONException e){

        }
        return map;
    }
}
