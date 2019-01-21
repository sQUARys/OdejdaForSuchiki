package com.example.mac.suchik.UI.main_window;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mac.suchik.Clothe;
import com.example.mac.suchik.R;

import java.util.List;

public class RecomendationListAdapter extends RecyclerView.Adapter<VH> {

    private List<Clothe> mData;

    public RecomendationListAdapter(List<Clothe> data) {
        super();
        mData = data;
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_rv, parent, false);
        return new VH(view);
    }

    public void onBindViewHolder(VH holder, int position) {
        holder.tv.setText(mData.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}


