package com.example.mac.suchik;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Callbacks, ResponseType {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        WrapperApi wrapperApi = new WrapperApi("50", "50", ResponseType.WTODAY ,MainActivity.this);
        wrapperApi.execute();

    }

    @Override
    public void onLoad(com.example.mac.suchik.Response response) {
        switch (response.type){
            case ResponseType.WTODAY:
                textView.setText(response.response.toString());
                break;

            case ResponseType.WFORECASTS:
                textView.setText(response.response.toString());
                break;
        }
    }
}
