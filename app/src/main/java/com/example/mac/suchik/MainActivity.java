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

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        new ApiTask().execute();

    }
    class ApiTask extends AsyncTask<Void, Void, Void> {
        String response;
        @Override
        protected Void doInBackground(Void... voids) {
            GetExample example = new GetExample();
            response = null;
            try {
                response = example.run("https://api.weather.yandex.ru/v1/forecast?");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Map<String, String> wheather = parseJsonConfig(response);
            textView.setText(wheather.get("temp"));
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            textView.setText("gcbbv");
        }

        public class GetExample {
            OkHttpClient client = new OkHttpClient();

            String run(String url) throws IOException {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("X-Yandex-API-Key", "5db67765-0ae1-41b9-af75-f4739f45ac5c")
                        .addHeader("lat", "50")
                        .addHeader("lon", "50")
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    return response.body().string();
                }
            }
        }
    }
    private Map<String, String> parseJsonConfig(String json) {
        HashMap<String, String> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject apps = jsonObject.getJSONObject("fact");
            map.put("temp", apps.getString("icon"));
        } catch (JSONException e) { //
            map.put("temp", "asd");
        }
        return map; }
}
