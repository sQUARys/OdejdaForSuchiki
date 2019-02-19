package com.example.mac.suchik;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import com.example.mac.suchik.WeatherData.WeatherData;
import com.google.gson.Gson;

class Weather {
    private Request request;
    private String strResponse;
    private int type;
    private Gson gson;

    Weather(String lat, String lon, Gson gson) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.weather.yandex.ru/v1/forecast?" + "lat=" + lat + "&lon=" + lon +
                        "&hours=false&extra=true")
                .addHeader("X-Yandex-API-Key", "07630454-fe07-4c10-acec-32cb620dc877")
                .build();
        this.request = request;
        this.strResponse = getResponse();
        this.gson = gson;
    }

    private String getResponse() throws IOException {
        OkHttpClient client = new OkHttpClient();
        try (okhttp3.Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    Response parseWeather(){
        Response response = new Response<>(ResponseType.GETW, gson.fromJson(strResponse, WeatherData.class));
        return response;
    }
}