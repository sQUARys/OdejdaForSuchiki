package com.example.mac.suchik.UI;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.le.AdvertiseSettings;
import android.content.pm.PackageManager;
import android.hardware.camera2.CaptureRequest;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mac.suchik.Callbacks;
import com.example.mac.suchik.Geoposition;
import com.example.mac.suchik.R;
import com.example.mac.suchik.Response;
import com.example.mac.suchik.ResponseType;
import com.example.mac.suchik.Storage;
import com.example.mac.suchik.UI.main_window.RecomendationListAdapter;
import com.example.mac.suchik.UI.settings_page.VH;
import com.example.mac.suchik.WeatherData.Fact;
import com.example.mac.suchik.WeatherData.Forecasts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainWindowFragment extends Fragment implements Callbacks, AdapterView.OnItemSelectedListener {
    public static Storage mStorage;
    private TextView date;
    private ImageView weather_cloud;
    private TextView temperature;
    private RecyclerView rv;
    private RecyclerView rv_clothes;
    private Spinner spinnerCity;

    private String[] position;
    private ArrayAdapter arrayAdapter;
    private boolean first;

    private HashMap<Integer, String[]> CityPos = new HashMap<Integer, String[]>(){{
        put(0, new String[]{"50", "36"});
        put(1, new String[]{"55.7522200", "37.6155600"});
        put(2, new String[]{"80", "80"});
        put(3, new String[]{"58.0446000", "38.8425900"});
    }};
    private List<String> cities = new LinkedList<String>(){{
        add("Москва");
        add("Рыбинск");
    }};
    private ProgressBar progressBar;

    private Fact f;

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
        //mStorage.setPosition("55.45", "37.36");
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
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        spinnerCity = view.findViewById(R.id.city);
        temperature = view.findViewById(R.id.temperature);
        weather_cloud = view.findViewById(R.id.weather_cloud);
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
            temperature.setText(String.format("%f" , weather.getTemp()));
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
                weather_cloud.setImageResource(R.drawable.rain);
                break;
            case "partly-cloudy-and-rain":
                weather_cloud.setImageResource(R.drawable.rain);
                break;
            case "overcast-and-rain":
                weather_cloud.setImageResource(R.drawable.rain);
                break;
            case "overcast-thunderstorms-with-rain":
                weather_cloud.setImageResource(R.drawable.rain);
                break;
            case "cloudy-and-light-rain":
                weather_cloud.setImageResource(R.drawable.rain);
                break;
            case "overcast-and-light-rain":
                weather_cloud.setImageResource(R.drawable.rain);
                break;
            case "cloudy-and-rain":
                weather_cloud.setImageResource(R.drawable.rain);
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
                position = (String[]) response.response;
                Log.d("position", position[0] + " " + position[1]);
                //mStorage.getWeatherToday();
                mStorage.setPosition(position[0], position[1]);
                break;
            case ResponseType.WTODAY:
                f = (Fact) response.response;
                mStorage.getClothes(f);

                break;
            case ResponseType.COMMUNITY:
                final String[] res = (String[]) response.response;
                String community = res[2];
                Log.d("community", "community = " + community);
                if (!first) {
                    arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, new ArrayList(){{
                        add(res[2]);
                        add("Москва");
                        add("Рыбинск");
                        add("Шляпа");
                    }});
                    CityPos.put(0, new String[]{res[0], res[1]});
                    cities.add(res[2]);
                    spinnerCity.setAdapter(arrayAdapter);
                    spinnerCity.setOnItemSelectedListener(this);
                    first = true;
                }
                else {
                    if (!res[2].equals("") && !cities.contains(res[2])) {
                        arrayAdapter.add(res[2]);
                        cities.add(res[2]);
                        CityPos.put(CityPos.size(), new String[]{res[0], res[1]});
                    }
                }
                break;
            case ResponseType.CLOTHES:
                ArrayList<String> recommendations = (ArrayList<String>) response.response;
                rv_clothes.setAdapter(new RecomendationListAdapter(recommendations));
                onChangedWeatherDraw(f);
                onWeatherDataUpdated(f);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
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
                rv.setAdapter(new Weather_Adapter(forecasts.subList(1 , 8)));
                progressBar.setVisibility(ProgressBar.INVISIBLE);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] pos = CityPos.get(position);
        mStorage.setPosition(pos[0], pos[1]);
        mStorage.getCurrentCommunity();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
