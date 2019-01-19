package com.example.mac.suchik;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.BoringLayout;
import android.util.Log;

import com.example.mac.suchik.WeatherData.WeatherData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Storage implements Callbacks{
    private WeatherData response;
    private Geoposition geoposition;
    private String[] position;
    private HashMap<Integer, List<Callbacks>> type_callback_rels = new LinkedHashMap<>();
    private Gson gson;
    private Context mCtx;
    private SharedPreferences sp;
    private HashMap executed;


    private GetClothes getClothes;

    private static Storage _instance;


    public static synchronized Storage getOrCreate(Context context){
        if (_instance == null){
            _instance = new Storage(context);
        }
        return _instance;
    }

    Storage(Context context){
        this.mCtx = context;
        this.gson = new Gson();
        geoposition = new Geoposition(context);
        sp = context.getSharedPreferences(context.getString(R.string.weather_preferences), Context.MODE_PRIVATE);
        executed = new HashMap<String, Boolean> (){{
                put("GG", false);
                put("GT", false);
                put("GF", false);
                put("GC", false);
        }};
        if (response == null && !Objects.equals(sp.getString("weather", null), "null")){
            onLoad(new Response<>(ResponseType.GETW,
                    gson.fromJson(sp.getString("weather", null),
                            WeatherData.class)));
        }
        if (response == null && !Objects.equals(sp.getString("pos_lat",
                null), "null") &&
                response == null && !Objects.equals(sp.getString("pos_lon",
                null), "null")){
            setPosition(sp.getString("pos_lat", null),
                    sp.getString("pos_lon", null));
        }
    }

    void updateWeather(){
        if (position[0] == null || position[1] == null){
            updatePosition();
            return;
        }
        if ((Boolean) executed.get("GT"))
            getWeatherForecasts();
        else if ((Boolean) executed.get("GF"))
            getWeatherToday();
        else {
            executed.putAll(new HashMap(){{put("GT", true); put("GF", true);}});
            new WrapperApi(position[0], position[1], Storage.this, gson).execute();
            executed.putAll(new HashMap(){{put("GT", false); put("GF", false);}});
        }
    }

    void updatePosition(){
        onLoad(geoposition.start());
        updateWeather();
    }

    void getClothes(){
        new GetClothes(mCtx, Storage.this, response.getFact()).execute();
    }

    void setPosition(String lat, String lon){
        if (!(Boolean) executed.get("GG")) {
            executed.put("GG", true);
            onLoad(new Response<>(ResponseType.GGEOPOSITION, new String[]{lat, lon}));
            executed.put("GG", false);
            updateWeather();
        }
    }

    void subscribe(int type, Callbacks callbacks){
        if (type_callback_rels.get(type) == null) type_callback_rels.put(type,
                new ArrayList<Callbacks>());
        type_callback_rels.get(type).add(callbacks);
    }
    void unsubscribe(Callbacks callbacks){
        for (List<Callbacks> callbacks1: type_callback_rels.values()){
            if (callbacks1.contains(callbacks))
                callbacks1.remove(callbacks1.indexOf(callbacks));
        }
    }

    void saveData(){
        if (response != null){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("weather", gson.toJson(response));
            editor.apply();
        }
    }

    void getWeatherToday(){
        if (!(Boolean) executed.get("GT")){
            if (response == null && !((Boolean) executed.get("GF"))){
                updateWeather();
            } else{
                if (position[0] == null || position[1] == null){
                    updatePosition();
                    return;
                }
                executed.put("GT", true);
                onLoad(new Response<>(ResponseType.WTODAY, response.getFact()));
                executed.put("GT", false);
            }
        }
    }

    void getWeatherForecasts() {
        if (!(Boolean) executed.get("GF")){
            if (response == null && !((Boolean) executed.get("GT"))){
                updateWeather();
            } else{
                if (position[0] == null || position[1] == null){
                    updatePosition();
                    return;
                }
                executed.put("GF", true);
                onLoad(new Response<>(ResponseType.WFORECASTS, response.getForecasts()));
                executed.put("GF", false);
            }
        }
    }

    @Override
    public void onLoad(Response response) {
        if (response.response != null && response.response.getClass() == WeatherData.class)
            this.response = (WeatherData) response.response;
        List<Callbacks> list = null;
        switch (response.type){
            case ResponseType.GETW:
                this.response = (WeatherData) response.response;
                for (int i = 1; i < 4; i++){
                    if (type_callback_rels.get(i) == null) type_callback_rels.put(i,
                            new ArrayList<Callbacks>());
                    list = type_callback_rels.get(i);
                    for (Callbacks callbacks: list) {
                        switch (i) {
                            case ResponseType.GETW:
                                callbacks.onLoad(new Response<>(ResponseType.WTODAY,
                                        ((WeatherData) response.response).getFact()));
                                callbacks.onLoad(new Response<>(ResponseType.WFORECASTS,
                                        ((WeatherData) response.response).getForecasts()));
                                break;
                            case ResponseType.WTODAY:
                                callbacks.onLoad(new Response<>(ResponseType.WTODAY,
                                        ((WeatherData) response.response).getFact()));
                                break;
                            case ResponseType.WFORECASTS:
                                callbacks.onLoad(new Response<>(ResponseType.WFORECASTS,
                                        ((WeatherData) response.response).getForecasts()));
                                break;
                        }
                    }
                }
                break;
            case ResponseType.WTODAY:
                if (type_callback_rels.get(ResponseType.WTODAY) == null)
                    type_callback_rels.put(ResponseType.WTODAY, new ArrayList<Callbacks>());
                list = type_callback_rels.get(ResponseType.WTODAY);
                for (Callbacks callbacks: list) {
                    callbacks.onLoad(new Response<>(ResponseType.WTODAY, response.response));
                }
                break;
            case ResponseType.WFORECASTS:
                if (type_callback_rels.get(ResponseType.WFORECASTS) == null)
                    type_callback_rels.put(ResponseType.WFORECASTS, new ArrayList<Callbacks>());
                list = type_callback_rels.get(ResponseType.WFORECASTS);
                for (Callbacks callbacks: list) {
                    callbacks.onLoad(new Response<>(ResponseType.WFORECASTS, response.response));
                }
                break;
            case ResponseType.GGEOPOSITION:
                this.position = (String[]) response.response;
                if (type_callback_rels.get(ResponseType.GGEOPOSITION) == null)
                    type_callback_rels.put(ResponseType.GGEOPOSITION, new ArrayList<Callbacks>());
                list = type_callback_rels.get(ResponseType.GGEOPOSITION);
                for (Callbacks callbacks: list) {
                    callbacks.onLoad(response);
                }
                break;
            case ResponseType.GEOERROR:
                if (type_callback_rels.get(ResponseType.GGEOPOSITION) == null)
                    type_callback_rels.put(ResponseType.GGEOPOSITION, new ArrayList<Callbacks>());
                list = type_callback_rels.get(ResponseType.GGEOPOSITION);
                for (Callbacks callbacks: list) {
                    if (position != null)
                        callbacks.onLoad(new Response<>(ResponseType.GGEOPOSITION, position));
                    else callbacks.onLoad(response);
                }
                break;
            case ResponseType.CLOTHES:
                if (type_callback_rels.get(ResponseType.CLOTHES) == null)
                    type_callback_rels.put(ResponseType.CLOTHES, new ArrayList<Callbacks>());
                list = type_callback_rels.get(ResponseType.CLOTHES);
                for (Callbacks callbacks: list) {
                    callbacks.onLoad(response);
                }
                break;
        }
    }
}