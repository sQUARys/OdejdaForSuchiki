package com.example.mac.suchik.UI;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.mac.suchik.Clothe;
import com.example.mac.suchik.R;
import com.example.mac.suchik.UI.settings_page.VH_weather_adapter;
import com.example.mac.suchik.WeatherData.Forecasts;

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
        return new VH_weather_adapter(view);
    }

    public void onBindViewHolder(VH_weather_adapter holder, int position) {
        holder.tv.setText(String.valueOf(mData.get(position).getParts().getDay().getTemp_avg()));
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
