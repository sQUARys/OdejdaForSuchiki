package com.example.mac.suchik.UI.settings_page;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ClickDocumentAdapter extends TimesListAdapter implements View.OnClickListener {

    private final OnDocumentClickListener onDocumentClickListener;

    public ClickDocumentAdapter(List<String> data,
                                ClickDocumentAdapter.OnDocumentClickListener onDocumentClickListener) {
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
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH holder = super.onCreateViewHolder(parent, viewType);
        holder.itemView.setOnClickListener(this);

        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(VH holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setTag(position);
    }
}
