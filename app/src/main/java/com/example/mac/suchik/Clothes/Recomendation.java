package com.example.mac.suchik.Clothes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mac.suchik.R;

import java.util.ArrayList;

public class Recomendation extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd;
    EditText etTemp, etRain, etWind, etCloud;

    final String LOG_TAG = "myLog";

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendation);


        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        etTemp = findViewById(R.id.etTemp);
        etRain = findViewById(R.id.etRain);
        etWind = findViewById(R.id.etWind);
        etCloud = findViewById(R.id.etCloud);


    }

    // обработка нажатия кнопки
    public void onClick(View view) {

        Model model = new Model(this);

        Model.Weather weather = model.new Weather();

        weather.temperature = Integer.valueOf(etTemp.getText().toString());
        weather.rain = etRain.getText().toString();
        weather.wind = Integer.valueOf(etWind.getText().toString());
        weather.cloud = etCloud.getText().toString();

        //ArrayList<String> rec = model.getClothes();
        /*
        for (String i : rec){
            Log.d(LOG_TAG, i);
        }
        */
    }

}
