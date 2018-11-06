package com.example.csyvi.medpack;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * The type Fourth fragment.
 */
public class SettingFragment extends Fragment {

    SwitchCompat switch_1, switch_2;
    boolean stateSwitch1, stateSwitch2;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_settings, container, false);

        preferences = getActivity().getSharedPreferences("PREFS",0);
        stateSwitch1 = preferences.getBoolean("switch1", false);
        stateSwitch2 = preferences.getBoolean("switch2", false);

        switch_1 = view.findViewById(R.id.switchLocation);
        switch_2 = view.findViewById(R.id.switchMessages);

        switch_1.setChecked(stateSwitch1);
        switch_2.setChecked(stateSwitch2);
        switch_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateSwitch1 = !stateSwitch1;
                switch_1.setChecked(stateSwitch1);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("switch1", stateSwitch1);
                editor.apply();
            }
        });

        switch_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateSwitch2 = !stateSwitch2;
                switch_2.setChecked(stateSwitch2);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("switch2", stateSwitch2);
                editor.apply();
            }
        });

        return view;
    }
}
