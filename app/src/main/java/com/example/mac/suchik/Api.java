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

class Weather implements ResponseType{
    private Request request;
    private String strResponse;
    private  int type;

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
    public Response parseWheatherToday(){
        Fact now = null;
        type = ResponseType.WTODAY;
        try{
            JSONObject jsonObject = new JSONObject(strResponse);
            JSONObject apps = jsonObject.getJSONObject("fact");
            Gson gson = new Gson();
            now = gson.fromJson(strResponse, Fact.class);
        }
        catch (JSONException e){
//            now.put("temp", null);
//            now.put("icon", null);
//            now.put("prec_type", null);
            type = ResponseType.ERROR;
        }
        return new Response<>(type, now);
    }

    public Response parseWheatherForecasts() {
        WeatherData week = null;
//        HashMap<String, HashMap<String, HashMap<String, String>>> week = new HashMap<>();
//        String[] times = {"night", "morning", "day", "evening"};
//        HashMap<String, String> pieceDay;
//        HashMap<String, HashMap<String, String>> day;
//        type = ResponseType.WFORECASTS;
        try{
            JSONArray jsonArray = (new JSONObject(strResponse)).getJSONArray("forecasts");
            Gson gson = new Gson();
            week = gson.fromJson(jsonArray.toString(), WeatherData.class);
//            for (int i = 0; i < jsonArray.length(); i++){
//                JSONObject today= (JSONObject) jsonArray.get(i);
//                JSONObject timesOfDay = today.getJSONObject("parts");
//                day = new HashMap<>();
//                for (String time: times){
//                    JSONObject timeOfDay = timesOfDay.getJSONObject(time);
//                    pieceDay = new HashMap<>();
//                    pieceDay.put("temp", timeOfDay.getString("temp_avg"));
//                    pieceDay.put("icon", timeOfDay.getString("icon"));
//                    pieceDay.put("prec_type", timeOfDay.getString("prec_type"));
//                    pieceDay.put("condition", timeOfDay.getString("condition"));
//                    day.put(time, pieceDay);
//                }
//                week.put(today.getString("date"), day);
//            }
        }
        catch (JSONException e){
            type = ResponseType.ERROR;
        }
        return new Response<>(type, week);
    }

}
