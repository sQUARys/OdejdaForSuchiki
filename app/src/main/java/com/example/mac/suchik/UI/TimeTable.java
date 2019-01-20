package com.example.mac.suchik.UI;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mac.suchik.R;
import com.example.mac.suchik.UI.settings_page.TimesListAdapter;

import java.util.Arrays;

public class TimeTable extends Fragment {
    private ImageView image;
    private Button btn;
    TextView tv;
    EditText et;
    RecyclerView rv;
    private String s;
    public String[] Strings = new String[]{
            "gfjgjnig"
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_window, container, false);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.list_rv);
        tv = view.findViewById(R.id.tv1);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new TimesListAdapter(Arrays.asList(Strings)));
    }
}
