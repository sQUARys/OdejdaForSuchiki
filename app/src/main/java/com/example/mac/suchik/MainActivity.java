package com.example.mac.suchik;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.suchik.WeatherData.Fact;
import com.example.mac.suchik.WeatherData.Forecasts;
import com.example.mac.suchik.WeatherData.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Callbacks, ResponseType {

    TextView textView;
    static Storage storage;
    String[] position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);

        storage = Storage.getOrCreate(MainActivity.this);

        

        storage.subscribe(ResponseType.GETW, MainActivity.this);
        storage.subscribe(ResponseType.GGEOPOSITION, MainActivity.this);

        storage.updatePosition();

    }

    @Override
    protected void onStop() {
        super.onStop();
        storage.unsubscribe(MainActivity.this);
    }

    @Override
    public void onLoad(com.example.mac.suchik.Response response) {
        switch (response.type){
            case ResponseType.WTODAY:
                Fact f = (Fact) response.response;

                textView.setText(f.getTemp() + "");
                break;

            case ResponseType.WFORECASTS:
                List<Forecasts> we = (List<Forecasts>) response.response;
                textView.setText(we.toString());
                break;
            case ResponseType.GEOERROR:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                            (this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        storage.updatePosition();
                    else{
                        Toast.makeText(this,"Ну и иди в баню, сыч", Toast.LENGTH_SHORT).show();
                        storage.setPosition("50", "50");
                    }
                }
                else storage.updatePosition();
                break;
            case ResponseType.GGEOPOSITION:
                position = (String[]) response.response;
                storage.getWeatherToday(position);
                break;
        }
    }

    public Context getContext(){
        return MainActivity.this;
    }
}
