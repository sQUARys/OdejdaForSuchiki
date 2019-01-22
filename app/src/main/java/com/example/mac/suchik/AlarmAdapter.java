package com.example.mac.suchik;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mac.suchik.AlarmHolder;
import com.example.mac.suchik.R;
import com.example.mac.suchik.UI.settings_page.VH;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmHolder> {
    TextView second;
    private List<AlarmClock> mData;
    private View view;

    public AlarmAdapter(List<AlarmClock> data) {
        mData = data;
    }

    @Override
    public AlarmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recomendation_list_element, parent, false);
        this.view = view;
        return new AlarmHolder(view);
    }

    class ClickListener implements View.OnClickListener {
        private int i;
        ClickListener(int i){
            this.i = i;
        }

        @Override
        public void onClick(View v) {
            final Alarms alarms = new Alarms(view.getContext());
            alarms.removeNotification(Integer.parseInt(mData.get(i).getId()));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final AlarmHolder alarmHolder, int i) {
        alarmHolder.getNum().setText((i + 1)+ "");
        alarmHolder.getTime().setText(mData.get(i).getTime());
        alarmHolder.getRemove().setOnClickListener(new ClickListener(i));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void setList(List<AlarmClock> new_elements){
        mData.clear();
        mData.addAll(new_elements);
    }


}
