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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.suchik.ClothesData;
import com.example.mac.suchik.DBOperations;
import com.example.mac.suchik.R;
import com.example.mac.suchik.UI.main_window.RecomendationListAdapter;
import com.example.mac.suchik.UI.settings_page.TimesListAdapter;

import java.util.Arrays;

public class TimeTable extends Fragment implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener,
        View.OnClickListener {
    private String name, color;
    private int category = 0;
    private int minT = 100;
    private int maxT = 100;
    private int rain = 100;
    private int wind = 100;
    private int cloud = 100;

    private RadioGroup radioGroupRain;
    private RadioGroup radioGroupWind;
    private RadioGroup radioGroupCloud;

    private EditText editTextName;
    private EditText editTextColor;

    private Button add;

    private ClothesData item;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_item_fragment, container, false);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        editTextColor = view.findViewById(R.id.color);
        editTextName = view.findViewById(R.id.name);

        add = view.findViewById(R.id.add_item);
        add.setOnClickListener(this);


        radioGroupRain = (RadioGroup) view.findViewById(R.id.rain);
        radioGroupRain.setOnCheckedChangeListener(this);

        radioGroupWind = (RadioGroup) view.findViewById(R.id.wind);
        radioGroupWind.setOnCheckedChangeListener(this);

        radioGroupCloud = (RadioGroup) view.findViewById(R.id.cloud);
        radioGroupCloud.setOnCheckedChangeListener(this);

        // Настраиваем адаптер
        ArrayAdapter<?> adapterCategories =
                ArrayAdapter.createFromResource(getContext(), R.array.categories, android.R.layout.simple_spinner_item);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerCategories = (Spinner) view.findViewById(R.id.spinner_category);
        spinnerCategories.setAdapter(adapterCategories);
        spinnerCategories.setOnItemSelectedListener(this);


        // Настраиваем адаптер
        ArrayAdapter<?> adapterTemp =
                ArrayAdapter.createFromResource(getContext(), R.array.temperature, android.R.layout.simple_spinner_item);
        adapterTemp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerTemp = (Spinner) view.findViewById(R.id.spinner_temp);
        spinnerTemp.setAdapter(adapterTemp);
        spinnerTemp.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinner_temp:
                switch (position){
                    case 1:
                        minT = -30; maxT = -15;
                        break;
                    case 2:
                        minT = -15; maxT = 0;
                        break;
                    case 3:
                        minT = 0; maxT = 15;
                        break;
                    case 4:
                        minT = 15; maxT = 30;
                        break;
                }
                String item = (String) parent.getItemAtPosition(position);
                //Toast.makeText(getBaseContext(), "item = " + item, Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinner_category:
                category = position;
                //Toast.makeText(getBaseContext(), "item = " + position, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case -1:
                rain = 100; wind = 100; cloud = 100;
                break;
            case R.id.yes_rain:
                rain = 2;
                break;
            case R.id.no_rain:
                rain = 0;
                break;
            case R.id.do_not_matter_rain:
                rain = 1;
                break;
            case R.id.yes_wind:
                wind = 2;
                break;
            case R.id.no_wind:
                wind = 0;
                break;
            case R.id.do_not_matter_wind:
                wind = 1;
                break;
            case R.id.yes_cloud:
                cloud = 2;
                break;
            case R.id.no_cloud:
                cloud = 0;
                break;
            case R.id.do_not_matter_cloud:
                cloud = 1;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (category == 0){
            Toast.makeText(getContext(), "Выберете категорию", Toast.LENGTH_SHORT).show();
            return;
        }
        name = editTextName.getText().toString();
        if (name.isEmpty()){
            Toast.makeText(getContext(), "Введите название", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rain == 100){
            Toast.makeText(getContext(), "Выберете водонепроницаемость", Toast.LENGTH_SHORT).show();
            return;
        }
        if (wind == 100){
            Toast.makeText(getContext(), "Выберете продуваемость", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cloud == 100){
            Toast.makeText(getContext(), "Выберете защиту от солнца", Toast.LENGTH_SHORT).show();
            return;
        }
        color = editTextColor.getText().toString();
//        if (color.isEmpty()){
//            Toast.makeText(getBaseContext(), "Введите цвет", Toast.LENGTH_SHORT).show();
//            return;
//        }
        Log.d("Clothes", "Category = " + category + " name = " + name + " rain = " + rain + " wind = " + wind + " cloud = " +
                " color = " + color);

        item = new ClothesData();

        item.category = category;
        item.name = name;
        item.minTemp = minT;
        item.maxTemp = maxT;
        item.rain = rain;
        item.wind = wind;
        item.cloud = cloud;
        item.color = color;


        DBOperations dbOperations = new DBOperations(getContext());
        dbOperations.addItem(item);

        Toast.makeText(getContext(), "Добавлен элемент: " + item.name, Toast.LENGTH_SHORT).show();
    }
}
