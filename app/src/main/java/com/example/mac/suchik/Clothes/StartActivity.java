package com.example.mac.suchik.Clothes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.suchik.Callbacks;
import com.example.mac.suchik.Geoposition;
import com.example.mac.suchik.R;
import com.example.mac.suchik.Response;
import com.example.mac.suchik.ResponseType;
import com.example.mac.suchik.Storage;
import com.example.mac.suchik.WeatherData.Fact;
import com.example.mac.suchik.WeatherData.WeatherData;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity implements View.OnClickListener, Callbacks, ResponseType {

    private static final int REQUEST_LOCATION = 1;
    Button button, buttonR, buttonA;
    TextView textView;
    static Storage storage = new Storage();


    final String LOG_TAG = "myLog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        buttonR = findViewById(R.id.btn_recomendation);
        buttonR.setOnClickListener(this);

        button = findViewById(R.id.bd);
        button.setOnClickListener(this);

        buttonA = findViewById(R.id.set_weather);
        buttonA.setOnClickListener(this);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);

        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.set_weather:
                //Log.d(LOG_TAG, "button 'get_recomendadion' was clicked");
                Intent intent = new Intent(StartActivity.this, Recomendation.class);
                startActivity(intent);
                break;
            case R.id.bd:
                //textView.setText("gsiu");
                Intent intent2 = new Intent(StartActivity.this, AddItemActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_recomendation:
                //Log.d(LOG_TAG, "button 'get_recomendadion' was clicked");
                storage.subscribe(ResponseType.WTODAY, StartActivity.this);
                storage.getWeatherToday("43", "40");
                break;

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onLoad(Response response) {
        switch (response.type){
            case ResponseType.WTODAY:
                Fact weather = (Fact) response.response;
                Model model = new Model(this);
                ArrayList<String> clothes = model.getClothes(weather);
                break;

            case ResponseType.WFORECASTS:
                WeatherData we = (WeatherData) response.response;
                break;
        }
    }
}