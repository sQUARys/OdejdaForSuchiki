package com.example.mac.suchik.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.mac.suchik.Clothe;
import com.example.mac.suchik.Geoposition;
import com.example.mac.suchik.R;
import com.example.mac.suchik.Response;
import com.example.mac.suchik.ResponseType;
import com.example.mac.suchik.Storage;
import com.example.mac.suchik.UI.main_window.ClickRecomendationListAdapter;
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
    private TextView weather_cloud_description;
    private Geoposition geoposition;
    RecyclerView rv;
    private String city;
    RecomendationListAdapter recomendationListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mStorage = Storage.getOrCreate(null); // null потому что я надеюсь, что Storage уже инициализирован
        return inflater.inflate(R.layout.main_window, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        geoposition = new Geoposition(getContext());
        String[] location = geoposition.start();
        Log.d("geo", location[0] + " " + location[1]);
        mStorage.setPosition(location[0], location[1]);
        mStorage.subscribe(ResponseType.GGEOPOSITION, this);
        mStorage.subscribe(ResponseType.WTODAY, this);
        mStorage.subscribe(ResponseType.COMMUNITY, this);
        mStorage.subscribe(ResponseType.WFORECASTS ,this);
        mStorage.updateWeather();
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
        rv = view.findViewById(R.id.recommendation_list);
        rv.setAdapter(new Weather_Adapter(new ArrayList<Forecasts>()));
        date = view.findViewById(R.id.date);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        List<Clothe> data = new ArrayList<>();
        recomendationListAdapter = new ClickRecomendationListAdapter(data, null);
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
                mStorage.getWeatherToday(position);
                break;
            case ResponseType.WTODAY:
                Fact f = (Fact) response.response;
                onWeatherDataUpdated(f);
                onChangedWeatherDraw(f);
                break;
            case ResponseType.COMMUNITY:
                city = (String) response.response;
                city_name.setText(city);
                break;
            case ResponseType.WFORECASTS:
                break;
            case ResponseType.GEOERROR:
                mStorage.setPosition("50", "50");
                break;
        }
    }

}
