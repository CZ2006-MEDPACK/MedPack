package com.example.csyvi.medpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * The type Home fragment.
 */
public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookappointment_homepage, container, false);

        Button button = view.findViewById(R.id.bookAppointment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScanVitalSigns.class);
                Log.d("debugMEDPACK", "tesT");
                getActivity().startActivity(intent);
                // error starts after starting this activity
                Log.d("debugMEDPACK", "tesT2");
            }
        });

        return view;
    }
}