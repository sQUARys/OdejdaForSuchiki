package com.example.mac.suchik.UI.main_window;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mac.suchik.R;

public class VH extends RecyclerView.ViewHolder {

    TextView tv;
    public VH(View itemView) {
        super(itemView);
        tv = itemView.findViewById(R.id.tie);
    }
}