package com.example.mac.suchik;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import com.example.mac.suchik.Response;

class Wheather implements ResponseType{
    private Request request;
    private String strResponse;

    Wheather(String lat, String lon) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.weather.yandex.ru/v1/forecast?")
                .addHeader("X-Yandex-API-Key", "5db67765-0ae1-41b9-af75-f4739f45ac5c")
                .addHeader("lat", lat)
                .addHeader("lon", lon)
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
    public void parseWheatherToday(){
        HashMap<String, String> map = new HashMap<>();
        try{
            JSONObject jsonObject = new JSONObject(strResponse);
            JSONObject apps = jsonObject.getJSONObject("fact");
            map.put("temp", apps.getString("temp"));
            map.put("icon", apps.getString("icon"));
            map.put("prec_type", apps.getString("prec_type"));
            map.put("condition", apps.getString("condition"));
        }
        catch (JSONException e){
            map.put("temp", null);
            map.put("icon", null);
            map.put("prec_type", null);
        }
    }

    public void parseWheatherForecasts() {
        HashMap<String, HashMap<String, HashMap<String, String>>> week = new HashMap<>();
        String[] times = {"night", "morning", "day", "evening"};
        HashMap<String, String> pieceDay;
        HashMap<String, HashMap<String, String>> day;
        try{
            JSONArray jsonArray = (new JSONObject(strResponse)).getJSONArray("forectasts");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject today= (JSONObject) jsonArray.get(i);
                JSONObject timesOfDay = today.getJSONObject("parts");
                day = new HashMap<>();
                for (String time: times){
                    JSONObject timeOfDay = timesOfDay.getJSONObject(time);
                    pieceDay = new HashMap<>();
                    pieceDay.put("temp", timeOfDay.getString("temp"));
                    pieceDay.put("icon", timeOfDay.getString("icon"));
                    pieceDay.put("prec_type", timeOfDay.getString("prec_type"));
                    pieceDay.put("condition", timeOfDay.getString("condition"));

                }
                week.put(today.getString("date"), day);
            }
        }
        catch (JSONException e){
        }
    }

}
