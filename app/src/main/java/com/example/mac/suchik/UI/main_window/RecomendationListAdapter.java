package com.example.mac.suchik.UI.main_window;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mac.suchik.Clothe;
import com.example.mac.suchik.R;
import com.example.mac.suchik.UI.settings_page.VH_weather_adapter;
import com.example.mac.suchik.WeatherData.Forecasts;

import java.util.List;

public class RecomendationListAdapter extends RecyclerView.Adapter<VH_weather_adapter> {

    private List<String> mData;

    public RecomendationListAdapter(List<String> data) {
        super();
        mData = data;
    }
    @Override
    public VH_weather_adapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recomendation_list_element, parent, false);
        return new VH_weather_adapter(view);
    }

    public void onBindViewHolder(VH_weather_adapter holder, int position) {
        holder.tv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void setList(List<String> new_elements){
        mData.clear();
        mData.addAll(new_elements);
        notifyDataSetChanged();
    }


}


