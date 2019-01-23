package com.example.mac.suchik.UI.settings_page;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mac.suchik.R;

public class VH extends RecyclerView.ViewHolder {

    public TextView tv1;
    public ImageView category;

    public VH(View itemView) {
        super(itemView);
        tv1 = itemView.findViewById(R.id.second);
        category = itemView.findViewById(R.id.category);
    }
}