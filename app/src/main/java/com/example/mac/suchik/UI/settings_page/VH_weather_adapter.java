package com.example.mac.suchik.UI.settings_page;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mac.suchik.R;

public class VH_weather_adapter extends RecyclerView.ViewHolder {
    public TextView tv;

    public VH_weather_adapter(View itemView) {
        super(itemView);
        tv = itemView.findViewById(R.id.for_main);
    }
}
