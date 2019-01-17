package com.example.mac.suchik;

import com.example.mac.suchik.WeatherData.WeatherData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Storage implements ResponseType, Callbacks{
    WeatherData response;

    HashMap<Integer, List<Callbacks>> type_callback_rels = new LinkedHashMap<>();

    void updateWeather(String lat, String lon){
        new WrapperApi(lat, lon, Storage.this).execute();
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

    void getWeatherToday(String lat, String lon){
        if (response == null) {
            updateWeather(lat, lon);
        }else {
            onLoad(new Response<>(ResponseType.WTODAY, response.getFact()));
        }
    }

    void getWeatherForecasts(String lat, String lon) {
        if (response == null) {
            updateWeather(lat, lon);
        } else {
            onLoad(new Response<>(ResponseType.WFORECASTS, response.getForecasts()));
        }
    }

    @Override
    public void onLoad(Response response) {
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
                            case ResponseType.WTODAY:
                                callbacks.onLoad(new Response<>(i,
                                        ((WeatherData) response.response).getFact()));
                                break;
                            case ResponseType.WFORECASTS:
                                callbacks.onLoad(new Response<>(i,
                                        ((WeatherData) response.response).getForecasts()));
                                break;
                        }
                    }
                }
                return;
            case ResponseType.WTODAY:
                if (type_callback_rels.get(ResponseType.WTODAY) == null)
                    type_callback_rels.put(ResponseType.WTODAY, new ArrayList<Callbacks>());
                list = type_callback_rels.get(ResponseType.WTODAY);
                for (Callbacks callbacks: list) {
                    callbacks.onLoad(new Response<>(ResponseType.WTODAY, response.response));
                }
                return;
            case ResponseType.WFORECASTS:
                if (type_callback_rels.get(ResponseType.WFORECASTS) == null)
                    type_callback_rels.put(ResponseType.WFORECASTS, new ArrayList<Callbacks>());
                list = type_callback_rels.get(ResponseType.WFORECASTS);
                for (Callbacks callbacks: list) {
                    callbacks.onLoad(new Response<>(ResponseType.WFORECASTS, response.response));
                }
                return;
        }
    }
}
