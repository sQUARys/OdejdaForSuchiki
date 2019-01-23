package com.example.mac.suchik.UI;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.example.mac.suchik.R;
import com.example.mac.suchik.UI.main_window.RecomendationListAdapter;
import com.example.mac.suchik.UI.settings_page.TimesListAdapter;
import java.util.Arrays;

public class Settings extends Fragment {

    private CheckBox head;
    private CheckBox glove;
    private CheckBox scarf;
    private CheckBox coat;
    private CheckBox jeans;
    private CheckBox shirt;
    private CheckBox boot;
    private CheckBox eyeglasses;
    private CheckBox jogger_pants;
    private CheckBox sweater;

    private Switch degrees;

    private SharedPreferences settings;

    final String DEGREES = "degrees";
    final String HEAD = "head";
    final String GLOVE = "glove";
    final String SCARF = "scarf";
    final String COAT = "coat";
    final String JEANS = "jeans";
    final String SHIRT = "shirt";
    final String BOOT = "boot";
    final String EYEGLASSES = "eyeglasses";
    final String JOGGER_PANTS = "jogger_pants";
    final String SWEATER = "sweater";

    SharedPreferences.Editor ed;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_main, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        head = view.findViewById(R.id.head);
        head.setOnCheckedChangeListener(new SettingsChangeListener(HEAD));

        glove = view.findViewById(R.id.head);
        glove.setOnCheckedChangeListener(new SettingsChangeListener(GLOVE));

        scarf = view.findViewById(R.id.scarf);
        scarf.setOnCheckedChangeListener(new SettingsChangeListener(SCARF));

        coat = view.findViewById(R.id.coat);
        coat.setOnCheckedChangeListener(new SettingsChangeListener(COAT));

        jeans = view.findViewById(R.id.jeans);
        jeans.setOnCheckedChangeListener(new SettingsChangeListener(JEANS));

        shirt = view.findViewById(R.id.shirt);
        shirt.setOnCheckedChangeListener(new SettingsChangeListener(SHIRT));

        boot = view.findViewById(R.id.boot);
        boot.setOnCheckedChangeListener(new SettingsChangeListener(BOOT));

        eyeglasses = view.findViewById(R.id.eyeglasses);
        eyeglasses.setOnCheckedChangeListener(new SettingsChangeListener(EYEGLASSES));

        jogger_pants = view.findViewById(R.id.jogger_pants);
        jogger_pants.setOnCheckedChangeListener(new SettingsChangeListener(JOGGER_PANTS));

        sweater = view.findViewById(R.id.sweater);
        sweater.setOnCheckedChangeListener(new SettingsChangeListener(SWEATER));

        settings = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        ed.apply();

    }

    class SettingsChangeListener implements CompoundButton.OnCheckedChangeListener {
        private String settingName;

        public SettingsChangeListener(String settingName) {
            this.settingName = settingName;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ed = settings.edit();
            ed.putBoolean(settingName, isChecked);
        }
    }
}
