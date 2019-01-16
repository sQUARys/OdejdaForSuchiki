package com.example.mac.suchik;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import com.example.mac.suchik.WeatherData.Fact;
import com.example.mac.suchik.WeatherData.WeatherData;
import com.google.gson.Gson;

class Weather implements ResponseType {
    private Request request;
    private String strResponse;
    private int type;

    Weather(String lat, String lon) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.weather.yandex.ru/v1/forecast?" + "lat=" + lat + "&lon=" + lon +
                        "&hours=false&extra=true")
                .addHeader("X-Yandex-API-Key", "5db67765-0ae1-41b9-af75-f4739f45ac5c")
                .build();
        this.request = request;
        this.strResponse = getResponse();
    }

    String getResponse() throws IOException {
        OkHttpClient client = new OkHttpClient();
        try (okhttp3.Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    Response parseWeather(){
        Response response = new Response<>(ResponseType.GETW, (new Gson()).fromJson(strResponse, WeatherData.class));
        return response;
    }
}