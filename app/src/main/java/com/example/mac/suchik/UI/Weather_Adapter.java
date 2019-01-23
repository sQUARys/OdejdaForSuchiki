package com.example.mac.suchik.UI;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.mac.suchik.Clothe;
import com.example.mac.suchik.R;
import com.example.mac.suchik.UI.settings_page.VH;
import com.example.mac.suchik.UI.settings_page.VH_weather_adapter;
import com.example.mac.suchik.WeatherData.Forecasts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Weather_Adapter extends RecyclerView.Adapter<VH_weather_adapter> {
    private List<Forecasts> mData;

    public Weather_Adapter(List<Forecasts> data) {
        super();
        mData = data;
    }

    @Override
    public VH_weather_adapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.for_list_weather, parent, false);
        VH_weather_adapter weather_adapter = new VH_weather_adapter(view);
        return weather_adapter;
    }

    public void onBindViewHolder(VH_weather_adapter holder, int position) {
        //String date = "Fri, 22 Apr 2016 15:29:51 +0600";
        //String date = "2019-01-22";

        String strCurrentDate = mData.get(position).getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd-MM");
        String date = format.format(newDate);
        holder.date.setText(date);
        Float s = mData.get(position).getParts().getDay().getTemp_avg();
        holder.temp_avg.setText(s.toString() + "°С");
        String condition = mData.get(position).getParts().getDay().getCondition();
        switch (condition){
            case "clear":
                holder.im.setImageResource(R.drawable.sunny);
                break;
            case "partly-cloudy":
                holder.im.setImageResource(R.drawable.cloud);
                break;
            case "cloudy":
                holder.im.setImageResource(R.drawable.cloud);
                break;
            case "overcast":
                holder.im.setImageResource(R.drawable.cloud);
                break;
            case "partly-cloudy-and-light-rain":
                holder.im.setImageResource(R.drawable.rain);
                break;
            case "partly-cloudy-and-rain":
                holder.im.setImageResource(R.drawable.rain);
                break;
            case "overcast-and-rain":
                holder.im.setImageResource(R.drawable.rain);
                break;
            case "overcast-thunderstorms-with-rain":
                holder.im.setImageResource(R.drawable.rain);
                break;
            case "cloudy-and-light-rain":
                holder.im.setImageResource(R.drawable.rain);
                break;
            case "overcast-and-light-rain":
                holder.im.setImageResource(R.drawable.rain);
                break;
            case "cloudy-and-rain":
                holder.im.setImageResource(R.drawable.rain);
                break;
            case "overcast-and-wet-snow":
                holder.im.setImageResource(R.drawable.snowing);
                break;
            case "partly-cloudy-and-light-snow":
                holder.im.setImageResource(R.drawable.snowing);
                break;
            case "partly-cloudy-and-snow":
                holder.im.setImageResource(R.drawable.snowing);
                break;
            case "overcast-and-snow":
                holder.im.setImageResource(R.drawable.snowing);
                break;
            case "cloudy-and-light-snow":
                holder.im.setImageResource(R.drawable.snowing);
                break;
            case "overcast-and-light-snow":
                holder.im.setImageResource(R.drawable.snowing);
                break;
            case "cloudy-and-snow":
                holder.im.setImageResource(R.drawable.snowing);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void setList(List<Forecasts> new_elements){
        mData.clear();
        mData.addAll(new_elements);
        notifyDataSetChanged();
    }
}
