package com.example.mac.suchik.UI.main_window;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mac.suchik.Clothe;
import com.example.mac.suchik.R;
import com.example.mac.suchik.UI.settings_page.VH;
import com.example.mac.suchik.UI.settings_page.VH_weather_adapter;
import com.example.mac.suchik.WeatherData.Forecasts;

import java.util.List;

public class RecomendationListAdapter extends RecyclerView.Adapter<VH> {
    TextView second;
    private List<String> mData;

    public RecomendationListAdapter(List<String> data) {
        super();
        mData = data;
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recomendation_list_element, parent, false);
        return new VH(view);
    }

    public void onBindViewHolder(VH holder, int position) {
        holder.tv1.setText(mData.get(position));
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


