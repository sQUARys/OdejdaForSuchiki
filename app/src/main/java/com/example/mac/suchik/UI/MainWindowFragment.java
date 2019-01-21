package com.example.mac.suchik.UI;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.le.AdvertiseSettings;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mac.suchik.Callbacks;
import com.example.mac.suchik.Geoposition;
import com.example.mac.suchik.R;
import com.example.mac.suchik.Response;
import com.example.mac.suchik.ResponseType;
import com.example.mac.suchik.Storage;
import com.example.mac.suchik.UI.main_window.RecomendationListAdapter;
import com.example.mac.suchik.UI.settings_page.TimesListAdapter;
import com.example.mac.suchik.WeatherData.Fact;
import com.example.mac.suchik.WeatherData.Forecasts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainWindowFragment extends Fragment implements Callbacks {
    public static final int SCHEDULE_WINDOW_FRAGMENT = 1;
    public static Storage mStorage;
    private TextView city_name;
    private TextView date;
    private ImageView weather_cloud;
    private TextView temperature;
    private TextView temp_avg;
    private TextView weather_cloud_description;
    private Geoposition geoposition;
    private RecyclerView rv;
    private RecyclerView rv_clothes;
    private String city;
    private String mylist;
    RecomendationListAdapter recomendationListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getPermissions();
        mStorage = Storage.getOrCreate(null); // null потому что я надеюсь, что Storage уже инициализирован

        mStorage.subscribe(ResponseType.GGEOPOSITION, this);
        mStorage.subscribe(ResponseType.WTODAY, this);
        mStorage.subscribe(ResponseType.COMMUNITY, this);
        mStorage.subscribe(ResponseType.CLOTHES, this);
        mStorage.subscribe(ResponseType.WFORECASTS, this);
        return inflater.inflate(R.layout.main_window, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        Geoposition geoposition = new Geoposition(getContext());
        String[] position = geoposition.start();
        mStorage.setPosition(position[0], position[1]);
        mStorage.updateWeather(false);
    }

    @Override
    public void onStop() {
        mStorage.unsubscribe(this);
        super.onStop();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        city_name = view.findViewById(R.id.city_name);
        temperature = view.findViewById(R.id.temperature);
        weather_cloud = view.findViewById(R.id.weather_cloud);
        temp_avg = view.findViewById(R.id.temp_avg);
        rv = view.findViewById(R.id.recommendation_list);
        rv_clothes = view.findViewById(R.id.for_recommendation_list);
        date = view.findViewById(R.id.date);
        rv_clothes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        List<String> data = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date currentDate = new Date();
        String dateText = dateFormat.format(currentDate);
        date.setText(dateText);
    }

    public void onWeatherDataUpdated(Fact weather) {
        if(weather.getTemp() > 0)
        temperature.setText(String.format("+" + "%f", weather.getTemp()));
        else if (weather.getTemp() < 0)
            temperature.setText(String.format("-" + "%f" , weather.getTemp()));
        else temperature.setText("0");
    }
    public void onChangedWeatherDraw(Fact weather){
        String condition = weather.getCondition();
        Log.d("weather", condition);
        switch (condition){
                case "clear":
                weather_cloud.setImageResource(R.drawable.sunny);
                break;
                case "partly-cloudy ":
                weather_cloud.setImageResource(R.drawable.cloud);
                break;
                case "cloudy":
                weather_cloud.setImageResource(R.drawable.cloud);
                break;
                case "overcast":
                weather_cloud.setImageResource(R.drawable.cloud);
                break;
                case "partly-cloudy-and-light-rain":
                weather_cloud.setImageResource(R.drawable.snowing);
                break;
                case "partly-cloudy-and-rain":
                weather_cloud.setImageResource(R.drawable.rain);
                break;
                case "overcast-and-rain":
                weather_cloud.setImageResource(R.drawable.snowing);
                break;
                case "overcast-thunderstorms-with-rain":
                weather_cloud.setImageResource(R.drawable.snowing);
                break;
                case "cloudy-and-light-rain":
                weather_cloud.setImageResource(R.drawable.snowing);
                break;
                case "overcast-and-light-rain":
                weather_cloud.setImageResource(R.drawable.snowing);
                break;
                case "cloudy-and-rain":
                weather_cloud.setImageResource(R.drawable.snowing);
                break;
                case "overcast-and-wet-snow":
                weather_cloud.setImageResource(R.drawable.snowing);
                break;
                case "partly-cloudy-and-light-snow":
                weather_cloud.setImageResource(R.drawable.snowing);
                break;
                case "partly-cloudy-and-snow":
                weather_cloud.setImageResource(R.drawable.snowing);
                break;
                case "overcast-and-snow":
                weather_cloud.setImageResource(R.drawable.snowing);
                break;
                case "cloudy-and-light-snow":
                weather_cloud.setImageResource(R.drawable.snowing);
                break;
                case "overcast-and-light-snow":
                weather_cloud.setImageResource(R.drawable.snowing);
                break;
                case "cloudy-and-snow":
                weather_cloud.setImageResource(R.drawable.snowing);
                break;
        }
    }
    @Override
    public void onLoad(Response response) {
        switch (response.type) {
            case ResponseType.GGEOPOSITION:
                String[] position = (String[]) response.response;
                Log.d("position", position[0] + " " + position[1]);
                //mStorage.getWeatherToday();
                //mStorage.getCurrentCommunity();
                break;
            case ResponseType.WTODAY:
                Fact f = (Fact) response.response;
                onWeatherDataUpdated(f);
                onChangedWeatherDraw(f);
                mStorage.getClothes(f);
                break;
            case ResponseType.COMMUNITY:
                String community = (String) response.response;
                city_name.setText(community);
                Log.d("community", "community = " + community);
                break;
            case ResponseType.CLOTHES:
                ArrayList<String> recommendations = (ArrayList<String>) response.response;
                rv_clothes.setAdapter(new RecomendationListAdapter(recommendations));
                break;
            case ResponseType.WFORECASTS:
                List<Forecasts> forecasts = (List<Forecasts>) response.response;
//               if (forecasts.get(0).getParts() == null){
//                   Log.d("forcast", "null!");
//               } else{
//                   for (Forecasts forecast : forecasts) {
//                       Log.d("forcast", String.valueOf(forecast.getParts().getDay().getTemp_avg()));
//                   }
//               }
                rv.setAdapter(new Weather_Adapter(forecasts));
                break;
            case ResponseType.GEOERROR:
                mStorage.setPosition("50", "50");
                break;
        }
    }

    private void getPermissions(){
        while (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission
                (getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }
}
