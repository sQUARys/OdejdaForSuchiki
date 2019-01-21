package com.example.mac.suchik.UI.main_window;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

import com.example.mac.suchik.Clothe;
import com.example.mac.suchik.UI.settings_page.VH_weather_adapter;

import java.util.List;

public class ClickRecomendationListAdapter extends RecomendationListAdapter implements View.OnClickListener {

    private final OnDocumentClickListener onDocumentClickListener;

    public ClickRecomendationListAdapter(List<String> data,
                                         ClickRecomendationListAdapter.OnDocumentClickListener onDocumentClickListener) {
        super(data);
        this.onDocumentClickListener = onDocumentClickListener;
    }

    @Override
    public void onClick(View view) {
        Integer position = (Integer)view.getTag();
        onDocumentClickListener.onDocumentClick(view, position);
    }

    public interface OnDocumentClickListener {
        void onDocumentClick(View view, int position);
    }

    @Override
    public VH_weather_adapter onCreateViewHolder(ViewGroup parent, int viewType) {
        VH_weather_adapter holder = super.onCreateViewHolder(parent, viewType);
        holder.itemView.setOnClickListener(this);

        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(VH_weather_adapter holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setTag(position);
    }
}
