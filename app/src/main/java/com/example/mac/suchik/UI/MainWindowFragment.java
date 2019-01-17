package com.example.mac.suchik.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mac.suchik.Callbacks;
import com.example.mac.suchik.Clothe;
import com.example.mac.suchik.R;
import com.example.mac.suchik.Response;
import com.example.mac.suchik.ResponseType;
import com.example.mac.suchik.Storage;
import com.example.mac.suchik.UI.main_window.ClickRecomendationListAdapter;
import com.example.mac.suchik.UI.main_window.RecomendationListAdapter;
import com.example.mac.suchik.UI.main_window.VH;
import com.example.mac.suchik.UI.settings_page.TimesListAdapter;
import com.example.mac.suchik.WeatherData.Fact;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainWindowFragment extends Fragment implements Callbacks {
    public static final int SCHEDULE_WINDOW_FRAGMENT = 1;

    private Storage mStorage;

    private TextView city_name;
    private TextView time;
    private ImageView weather_cloud;
    private TextView temperature;
    private TextView weather_cloud__description;

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
        mStorage.subscribe(ResponseType.GGEOPOSITION, this);
        mStorage.subscribe(ResponseType.WTODAY, this);
        mStorage.updatePosition();
    }

    @Override
    public void onStop() {
        mStorage.unsubscribe(this);
        super.onStop();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        time = view.findViewById(R.id.time);
        city_name = view.findViewById(R.id.city_name);
        weather_cloud = view.findViewById(R.id.weather_cloud);
        temperature = view.findViewById(R.id.temperature);
        weather_cloud__description = view.findViewById(R.id.weather_cloud__description);

        RecyclerView rv = view.findViewById(R.id.recommendation_list);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        List<Clothe> data = new ArrayList<>();
        recomendationListAdapter = new ClickRecomendationListAdapter(data, null);
        rv.setAdapter(recomendationListAdapter);
    }

    public void onWeatherDataUpdated(Fact weather) {
        temperature.setText(String.format("%f", weather.getTemp()));
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
                break;
        }
    }
}
